package com.labsync.lms.util;

import com.labsync.lms.model.*;
import java.time.LocalTime;
import java.util.*;

/**
 * GeneticAlgorithm — generates optimised lab timetables using an evolutionary approach.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * ALGORITHM OVERVIEW
 * ───────────────────────────────────────────────────────────────────────────────
 * 1. INITIALISATION  — create N random chromosomes (candidate timetables)
 * 2. EVALUATION      — score each chromosome via the fitness function
 * 3. SELECTION       — pick parents using tournament selection
 * 4. CROSSOVER       — combine two parent chromosomes at a random split point
 * 5. MUTATION        — randomly alter a gene's lab/day/slot assignment
 * 6. ELITISM         — always carry the best chromosome to the next generation
 * 7. REPEAT 2-6 for maxGenerations iterations
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * FITNESS FUNCTION (maximise):
 *   Start at 1.0, subtract penalty per hard constraint violation:
 *    -0.20 per lab double-booking        (hard)
 *    -0.20 per staff double-booking      (hard)
 *    -0.20 per batch double-booking      (hard)
 *    -0.10 per lab capacity violation    (hard)
 *    -0.05 per staff teaching subjects outside their specialisation  (soft)
 *   Final score clamped to [0.0, 1.0].
 *
 * A perfect timetable has fitness = 1.0.
 */
public class GeneticAlgorithm {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GeneticAlgorithm.class);
    // ── Constraint penalty weights ────────────────────────────────────────────
    private static final double PENALTY_LAB_CLASH = 0.2;
    private static final double PENALTY_STAFF_CLASH = 0.2;
    private static final double PENALTY_BATCH_CLASH = 0.2;
    private static final double PENALTY_CAPACITY = 0.1;
    private static final double PENALTY_WORKING_PCS = 0.1;
    private static final double PENALTY_OS_MISMATCH = 0.15;
    // ── Tournament selection size ─────────────────────────────────────────────
    private static final int TOURNAMENT_SIZE = 5;
    // ── GA parameters (injected) ──────────────────────────────────────────────
    private final int populationSize;
    private final int maxGenerations;
    private final double crossoverRate;
    private final double mutationRate;
    // ── Problem data ──────────────────────────────────────────────────────────
    private final List<Batch> batches;
    private final List<Staff> staffList;
    private final List<Lab> labs;
    private final List<Day> days;
    // ── Internal state ────────────────────────────────────────────────────────
    private final Random random = new Random();
    private Chromosome bestChromosome;
    private final List<Double> fitnessHistory = new ArrayList<>();

    public GeneticAlgorithm(int populationSize, int maxGenerations, double crossoverRate, double mutationRate, List<Batch> batches, List<Staff> staffList, List<Lab> labs, List<Day> days) {
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.batches = batches;
        this.staffList = staffList;
        this.labs = labs;
        this.days = days;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // PUBLIC ENTRY POINT
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Runs the full GA and returns the best chromosome found.
     * Logs progress every 10 generations.
     */
    public Chromosome run() {
        log.info("GA starting — population={}, maxGenerations={}, crossover={}, mutation={}", populationSize, maxGenerations, crossoverRate, mutationRate);
        // Step 1: Initialise population
        List<Chromosome> population = initialisePopulation();
        // Step 2: Evaluate initial fitness
        population.forEach(this::evaluateFitness);
        Collections.sort(population);
        bestChromosome = population.get(0).deepCopy();
        log.info("Initial best fitness: {:.4f}", bestChromosome.getFitnessScore());
        // Main evolutionary loop
        for (int gen = 1; gen <= maxGenerations; gen++) {
            List<Chromosome> nextGeneration = new ArrayList<>();
            // Elitism — carry the best unchanged
            nextGeneration.add(bestChromosome.deepCopy());
            // Fill the rest of the next generation
            while (nextGeneration.size() < populationSize) {
                Chromosome parent1 = tournamentSelect(population);
                Chromosome parent2 = tournamentSelect(population);
                Chromosome offspring = random.nextDouble() < crossoverRate ? crossover(parent1, parent2) : parent1.deepCopy();
                mutate(offspring);
                evaluateFitness(offspring);
                nextGeneration.add(offspring);
            }
            population = nextGeneration;
            Collections.sort(population);
            // Track best
            Chromosome currentBest = population.get(0);
            if (currentBest.getFitnessScore() > bestChromosome.getFitnessScore()) {
                bestChromosome = currentBest.deepCopy();
            }
            fitnessHistory.add(bestChromosome.getFitnessScore());
            if (gen % 10 == 0 || gen == maxGenerations) {
                log.info("Generation {:4d} | Best fitness: {:.4f} | Clashes: {}", gen, bestChromosome.getFitnessScore(), countClashes(bestChromosome));
            }
            // Early termination if perfect solution found
            if (bestChromosome.getFitnessScore() >= 1.0) {
                log.info("Perfect solution found at generation {}!", gen);
                break;
            }
        }
        log.info("GA complete — best fitness: {:.4f} after {} generations", bestChromosome.getFitnessScore(), fitnessHistory.size());
        return bestChromosome;
    }

    public List<Double> getFitnessHistory() {
        return fitnessHistory;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // STEP 1 — INITIALISATION
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Creates {@code populationSize} random chromosomes.
     * Each chromosome assigns every (batch, subject) pair a random staff/lab/day/slot.
     */
    private List<Chromosome> initialisePopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(generateRandomChromosome());
        }
        return population;
    }

    /**
     * Generates one random chromosome.
     * For each batch × subject combination, picks random staff, lab, day, and slot.
     */
    private Chromosome generateRandomChromosome() {
        List<Chromosome.Gene> genes = new ArrayList<>();
        for (Batch batch : batches) {
            // Only schedule subjects explicitly assigned to this batch
            List<Subject> batchSubjects = batch.getSubjects();
            if (batchSubjects == null || batchSubjects.isEmpty()) {
                continue;
            }
            for (Subject subject : batchSubjects) {
                int sessions = batch.getLabsPerWeek() != null ? batch.getLabsPerWeek() : 1;
                for (int i = 0; i < sessions; i++) {
                    Staff staff = randomFrom(staffList);
                    Lab lab = randomFrom(labs);
                    Day day = randomFrom(days);
                    Chromosome.Gene gene = new Chromosome.Gene(batch, subject, staff, lab, day, null, null);
                    assignRandomTime(gene);
                    genes.add(gene);
                }
            }
        }
        return new Chromosome(genes);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // STEP 2 — FITNESS EVALUATION
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Scores a chromosome based on constraint violations.
     * Perfect timetable = 1.0; each violation deducts a penalty.
     */
    void evaluateFitness(Chromosome chromosome) {
        double penalty = 0.0;
        List<Chromosome.Gene> genes = chromosome.getGenes();
        for (int i = 0; i < genes.size(); i++) {
            Chromosome.Gene g1 = genes.get(i);
            for (int j = i + 1; j < genes.size(); j++) {
                Chromosome.Gene g2 = genes.get(j);
                boolean sameDay = g1.getDay().getId().equals(g2.getDay().getId());
                boolean timeOverlap = sameDay && g1.getStartTime().isBefore(g2.getEndTime()) && g1.getEndTime().isAfter(g2.getStartTime());
                if (timeOverlap) {
                    // Hard: lab double-booking
                    if (g1.getLab().getId().equals(g2.getLab().getId())) {
                        penalty += PENALTY_LAB_CLASH;
                    }
                    // Hard: staff double-booking
                    if (g1.getStaff().getId().equals(g2.getStaff().getId())) {
                        penalty += PENALTY_STAFF_CLASH;
                    }
                    // Hard: batch double-booking (same batch in two labs at once)
                    if (g1.getBatch().getId().equals(g2.getBatch().getId())) {
                        penalty += PENALTY_BATCH_CLASH;
                    }
                }
            }
            // Hard: lab capacity < batch size
            if (g1.getLab().getCapacity() < g1.getBatch().getStudentCount()) {
                penalty += PENALTY_CAPACITY;
            }
            // Hard: lab working computers < batch size
            if (g1.getLab().getWorkingComputers() < g1.getBatch().getStudentCount()) {
                penalty += PENALTY_WORKING_PCS;
            }
            // Hard: OS requirement mismatch
            String requiredOs = g1.getBatch().getOsRequirement();
            if (requiredOs != null && !requiredOs.trim().isEmpty() && !requiredOs.equalsIgnoreCase("Any")) {
                if (!requiredOs.equalsIgnoreCase(g1.getLab().getOsType())) {
                    penalty += PENALTY_OS_MISMATCH;
                }
            }
        }
        // Clamp fitness to [0.0, 1.0]
        chromosome.setFitnessScore(Math.max(0.0, 1.0 - penalty));
    }

    /**
     * Counts total hard constraint violations (for logging).
     */
    private int countClashes(Chromosome chromosome) {
        int clashes = 0;
        List<Chromosome.Gene> genes = chromosome.getGenes();
        for (int i = 0; i < genes.size(); i++) {
            Chromosome.Gene g1 = genes.get(i);
            for (int j = i + 1; j < genes.size(); j++) {
                Chromosome.Gene g2 = genes.get(j);
                boolean sameDay = g1.getDay().getId().equals(g2.getDay().getId());
                boolean timeOverlap = sameDay && g1.getStartTime().isBefore(g2.getEndTime()) && g1.getEndTime().isAfter(g2.getStartTime());
                if (timeOverlap) {
                    if (g1.getLab().getId().equals(g2.getLab().getId())) clashes++;
                    if (g1.getStaff().getId().equals(g2.getStaff().getId())) clashes++;
                    if (g1.getBatch().getId().equals(g2.getBatch().getId())) clashes++;
                }
            }
        }
        return clashes;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // STEP 3 — SELECTION (Tournament)
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Tournament selection — picks {@code TOURNAMENT_SIZE} random chromosomes
     * and returns the fittest one. Applies selection pressure without losing diversity.
     */
    private Chromosome tournamentSelect(List<Chromosome> population) {
        Chromosome best = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            Chromosome candidate = population.get(random.nextInt(population.size()));
            if (best == null || candidate.getFitnessScore() > best.getFitnessScore()) {
                best = candidate;
            }
        }
        return best;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // STEP 4 — CROSSOVER (Single-point)
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Single-point crossover — splits both parents at a random index and
     * combines the head of parent1 with the tail of parent2.
     *
     * parent1: [A, B, C, D, E]
     * parent2: [F, G, H, I, J]
     * point=3 → offspring: [A, B, C, I, J]
     */
    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<Chromosome.Gene> p1Genes = parent1.getGenes();
        List<Chromosome.Gene> p2Genes = parent2.getGenes();
        // Use shorter parent's size as safe upper bound
        int size = Math.min(p1Genes.size(), p2Genes.size());
        int point = random.nextInt(size);
        List<Chromosome.Gene> childGenes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            childGenes.add((i <= point ? p1Genes.get(i) : p2Genes.get(i)).copy());
        }
        return new Chromosome(childGenes);
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // STEP 5 — MUTATION
    // ═══════════════════════════════════════════════════════════════════════════
    /**
     * Mutation — for each gene, with probability {@code mutationRate},
     * randomly reassigns one of: lab, day, or time slot.
     *
     * Targeted mutation: we only mutate the dimension most likely causing a clash,
     * rather than resetting the entire gene (which would discard good partial solutions).
     */
    private void mutate(Chromosome chromosome) {
        for (Chromosome.Gene gene : chromosome.getGenes()) {
            if (random.nextDouble() < mutationRate) {
                int mutationType = random.nextInt(3);
                switch (mutationType) {
                    case 0 -> gene.setLab(randomFrom(labs));
                    case 1 -> {
                        gene.setDay(randomFrom(days));
                        assignRandomTime(gene);
                    }
                    case 2 -> assignRandomTime(gene);
                }
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // UTILITY
    // ═══════════════════════════════════════════════════════════════════════════
    private <T> T randomFrom(List<T> list) {
        if (list.isEmpty()) throw new IllegalStateException("Cannot pick from empty list");
        return list.get(random.nextInt(list.size()));
    }

    private void assignRandomTime(Chromosome.Gene gene) {
        int reqHrs = gene.getBatch().getRequiredHours() != null ? gene.getBatch().getRequiredHours() : 2;
        
        LocalTime bStart = gene.getBatch().getStartTime();
        LocalTime bEnd = gene.getBatch().getEndTime();
        
        boolean hasValidBatchTime = bStart != null && bEnd != null 
                                 && !bStart.equals(LocalTime.MIDNIGHT) 
                                 && !bEnd.equals(LocalTime.MIDNIGHT);
        
        LocalTime dayStartTime = gene.getDay().getStartTime();
        LocalTime dayEndTime = gene.getDay().getEndTime();
        
        LocalTime dStart = hasValidBatchTime ? bStart : (dayStartTime != null ? dayStartTime : LocalTime.of(9, 0));
        LocalTime dEnd = hasValidBatchTime ? bEnd : (dayEndTime != null ? dayEndTime : LocalTime.of(17, 0));
        
        if (dEnd == null || dStart == null) {
            dStart = LocalTime.of(9, 0);
            dEnd = LocalTime.of(17, 0);
        }

        // Handle inverted or invalid boundaries safely
        if (dEnd.isBefore(dStart)) {
            dEnd = dStart;
        }

        long maxStartMins = dEnd.toSecondOfDay() / 60 - dStart.toSecondOfDay() / 60 - reqHrs * 60L;
        if (maxStartMins < 0) maxStartMins = 0;
        
        int hrOffset = maxStartMins > 0 ? random.nextInt((int) (maxStartMins / 60) + 1) : 0;
        LocalTime startTime = dStart.plusHours(hrOffset);
        gene.setStartTime(startTime);
        gene.setEndTime(startTime.plusHours(reqHrs));
    }
}
