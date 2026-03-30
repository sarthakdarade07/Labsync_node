package com.labsync.lms.util;

import com.labsync.lms.model.*;
import java.util.*;

/**
 * Chromosome — represents a complete timetable as a candidate solution for the GA.
 *
 * Each gene = one schedule assignment:
 *   { batch, subject, staff, lab, day, startTime, endTime }
 *
 * The chromosome wraps a list of genes (Gene objects) and holds its computed
 * fitness score. Higher fitness = fewer constraint violations = better timetable.
 */
import java.time.LocalTime;

public class Chromosome implements Comparable<Chromosome> {

    /**
     * A single schedule assignment within the chromosome
     */
    public static class Gene {
        private Batch batch;
        private Subject subject;
        private List<Staff> staffList;
        private List<Lab> labs;
        private Day day;
        private LocalTime startTime;
        private LocalTime endTime;

        public Gene(Batch batch, Subject subject, List<Staff> staffList, List<Lab> labs, Day day, LocalTime startTime, LocalTime endTime) {
            this.batch = batch;
            this.subject = subject;
            this.staffList = new ArrayList<>(staffList);
            this.labs = new ArrayList<>(labs);
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        /**
         * Deep copy of this gene
         */
        public Gene copy() {
            return new Gene(batch, subject, new ArrayList<>(staffList), new ArrayList<>(labs), day, startTime, endTime);
        }

        @Override
        public String toString() {
            String labNames = labs.stream().map(Lab::getLabName).reduce((a, b) -> a + "+" + b).orElse("None");
            String staffNames = staffList.stream().map(Staff::getFullName).reduce((a, b) -> a + "+" + b).orElse("None");
            return String.format("[%s | %s | %s | %s | %s %s - %s]", batch.getBatchName(), subject.getName(), staffNames, labNames, day.getDayName(), startTime, endTime);
        }

        public Batch getBatch() {
            return this.batch;
        }

        public Subject getSubject() {
            return this.subject;
        }

        public List<Staff> getStaffList() {
            return this.staffList;
        }

        public List<Lab> getLabs() {
            return this.labs;
        }

        public Day getDay() {
            return this.day;
        }

        public LocalTime getStartTime() {
            return this.startTime;
        }

        public LocalTime getEndTime() {
            return this.endTime;
        }

        public void setBatch(final Batch batch) {
            this.batch = batch;
        }

        public void setSubject(final Subject subject) {
            this.subject = subject;
        }

        public void setStaffList(final List<Staff> staffList) {
            this.staffList = staffList;
        }

        public void setLabs(final List<Lab> labs) {
            this.labs = labs;
        }

        public void setDay(final Day day) {
            this.day = day;
        }

        public void setStartTime(final LocalTime startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(final LocalTime endTime) {
            this.endTime = endTime;
        }
    }

    // ── Chromosome fields ─────────────────────────────────────────────────────
    private List<Gene> genes;
    private double fitnessScore;

    public Chromosome() {
        this.genes = new ArrayList<>();
        this.fitnessScore = 0.0;
    }

    public Chromosome(List<Gene> genes) {
        this.genes = new ArrayList<>(genes);
        this.fitnessScore = 0.0;
    }

    /**
     * Deep copy constructor used during crossover/mutation
     */
    public Chromosome deepCopy() {
        List<Gene> copiedGenes = new ArrayList<>();
        for (Gene g : this.genes) copiedGenes.add(g.copy());
        Chromosome copy = new Chromosome(copiedGenes);
        copy.setFitnessScore(this.fitnessScore);
        return copy;
    }

    /**
     * Chromosomes with higher fitness rank first (descending)
     */
    @Override
    public int compareTo(Chromosome other) {
        return Double.compare(other.fitnessScore, this.fitnessScore);
    }

    @Override
    public String toString() {
        return String.format("Chromosome[genes=%d, fitness=%.4f]", genes.size(), fitnessScore);
    }

    public List<Gene> getGenes() {
        return this.genes;
    }

    public double getFitnessScore() {
        return this.fitnessScore;
    }

    public void setGenes(final List<Gene> genes) {
        this.genes = genes;
    }

    public void setFitnessScore(final double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }
}
