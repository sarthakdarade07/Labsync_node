const POPULATION_SIZE = 50;
const MAX_GENERATIONS = 50;
const MUTATION_RATE = 0.05;

class GeneticAlgorithm {
    constructor(batches, labs, staff, days, timeSlots) {
        this.batches = batches;
        this.labs = labs;
        this.staff = staff;
        this.days = days;
        this.timeSlots = timeSlots;
        
        // Precompute lab capacities for O(1) lookup
        this.labCapacityMap = {};
        for (let lab of labs) {
            this.labCapacityMap[lab._id] = lab.workingComputers || 0;
        }
    }

    async run() {
        if (this.batches.length === 0 || this.labs.length === 0 || this.staff.length === 0 || this.days.length === 0 || this.timeSlots.length === 0) {
            throw new Error("Insufficient data to run Genetic Algorithm.");
        }

        let population = this.initializePopulation();
        let bestChromosome = null;

        for (let generation = 0; generation < MAX_GENERATIONS; generation++) {
            this.evaluateFitness(population);
            
            // Sort by fitness (descending, so closest to 0 penalty is first)
            population.sort((a, b) => b.fitness - a.fitness);
            
            bestChromosome = population[0];
            if (bestChromosome.fitness === 0) {
                break; // Found perfect schedule
            }

            let newPopulation = [bestChromosome]; // Elitism
            while (newPopulation.length < POPULATION_SIZE) {
                let parent1 = this.tournamentSelection(population);
                let parent2 = this.tournamentSelection(population);
                let child = this.crossover(parent1, parent2);
                this.mutate(child);
                newPopulation.push(child);
            }
            population = newPopulation;

            // Yield event loop every 50 generations to prevent blocking
            if (generation % 50 === 0) {
                console.log(`Generation ${generation}, Best Fitness: ${bestChromosome.fitness}, Conflicts: ${bestChromosome.conflicts}`);
                await new Promise(resolve => setImmediate(resolve));
            }
        }

        this.evaluateFitness(population);
        population.sort((a, b) => b.fitness - a.fitness);
        return population[0];
    }

    initializePopulation() {
        let pop = [];
        for (let i = 0; i < POPULATION_SIZE; i++) {
            pop.push(this.generateRandomChromosome());
        }
        return pop;
    }

    generateRandomChromosome() {
        let chromosome = { genes: [], fitness: 0, conflicts: 0 };
        
        // Create a balanced pool of staff IDs to avoid overloading any staff member > 24 slots
        let staffPool = [];
        let numGenes = 0;
        for (let batch of this.batches) {
            for (let subject of batch.subjects) {
                numGenes += batch.labsPerWeek;
            }
        }
        
        let staffIndex = 0;
        for (let i = 0; i < numGenes; i++) {
            staffPool.push(this.staff[staffIndex]._id);
            staffIndex = (staffIndex + 1) % this.staff.length;
        }
        staffPool = staffPool.sort(() => Math.random() - 0.5); // Shuffle pool

        let currentGeneIndex = 0;
        for (let batch of this.batches) {
            for (let subject of batch.subjects) {
                for (let session = 0; session < batch.labsPerWeek; session++) {
                    let day = this.days[Math.floor(Math.random() * this.days.length)];
                    let timeSlot = this.timeSlots[Math.floor(Math.random() * this.timeSlots.length)];
                    
                    let staffId = staffPool[currentGeneIndex];
                    currentGeneIndex++;
                    
                    chromosome.genes.push({
                        batchId: batch._id,
                        subjectId: subject._id || subject,
                        staffId: staffId,
                        dayId: day._id,
                        startTime: timeSlot.startTime,
                        endTime: timeSlot.endTime,
                        labIds: [], // Assigned greedily in evaluateFitness
                        osRequirement: batch.osRequirement,
                        studentCount: batch.studentCount,
                        inConflict: false
                    });
                }
            }
        }
        return chromosome;
    }

    assignLabsForBatch(batch) {
        let availableLabs = [...this.labs].sort(() => 0.5 - Math.random());
        let assigned = [];
        let totalCapacity = 0;
        
        // Try with strict OS matching first
        for (let lab of availableLabs) {
            if (batch.osRequirement && batch.osRequirement !== 'Any' && lab.osType !== batch.osRequirement) continue;
            assigned.push(lab);
            totalCapacity += lab.workingComputers || 0;
            if (totalCapacity >= batch.studentCount) return assigned;
        }
        
        // If capacity isn't met due to OS constraints, fall back to adding ANY available labs to meet capacity
        for (let lab of availableLabs) {
            if (!assigned.includes(lab)) {
                assigned.push(lab);
                totalCapacity += lab.workingComputers || 0;
                if (totalCapacity >= batch.studentCount) return assigned;
            }
        }
        
        return assigned.length > 0 ? assigned : [availableLabs[0]];
    }

    evaluateFitness(population) {
        for (let chromosome of population) {
            let penalty = 0;
            let conflicts = 0;
            
            // Group by timeslot
            let timeSlotGroups = {};

            for (let i = 0; i < chromosome.genes.length; i++) {
                let gene = chromosome.genes[i];
                gene.inConflict = false; // Reset conflict flag
                
                let key = gene.dayId + '_' + gene.startTime;
                if (!timeSlotGroups[key]) timeSlotGroups[key] = [];
                timeSlotGroups[key].push(gene);
            }
            
            // Check collisions within each timeslot and greedily assign labs
            for (let key in timeSlotGroups) {
                let genesInSlot = timeSlotGroups[key];
                
                let busyLabs = new Set();
                
                for (let i = 0; i < genesInSlot.length; i++) {
                    let geneA = genesInSlot[i];
                    let conflictFound = false;

                    // Greedy Lab Assignment for geneA
                    let batch = this.batches.find(b => b._id === geneA.batchId);
                    let assigned = [];
                    let totalCapacity = 0;
                    
                    // First try strict OS matching
                    for (let lab of this.labs) {
                        if (busyLabs.has(lab._id)) continue;
                        if (batch.osRequirement && batch.osRequirement !== 'Any' && lab.osType !== batch.osRequirement) continue;
                        assigned.push(lab._id);
                        totalCapacity += lab.workingComputers || 0;
                        if (totalCapacity >= geneA.studentCount) break;
                    }
                    
                    // Fallback to ANY lab if capacity not met
                    if (totalCapacity < geneA.studentCount) {
                        for (let lab of this.labs) {
                            if (busyLabs.has(lab._id) || assigned.includes(lab._id)) continue;
                            assigned.push(lab._id);
                            totalCapacity += lab.workingComputers || 0;
                            if (totalCapacity >= geneA.studentCount) break;
                        }
                    }
                    
                    geneA.labIds = assigned;
                    for (let id of assigned) busyLabs.add(id);
                    
                    if (totalCapacity < geneA.studentCount) {
                        penalty += 50;
                        conflicts++;
                        conflictFound = true;
                    }

                    for (let j = i + 1; j < genesInSlot.length; j++) {
                        let geneB = genesInSlot[j];

                        // Staff collision
                        if (geneA.staffId === geneB.staffId) {
                            penalty += 100;
                            conflicts++;
                            conflictFound = true;
                            geneB.inConflict = true;
                        }
                        
                        // Batch collision
                        if (geneA.batchId === geneB.batchId) {
                            penalty += 100;
                            conflicts++;
                            conflictFound = true;
                            geneB.inConflict = true;
                        }
                    }
                    
                    if (conflictFound) {
                        geneA.inConflict = true;
                    }
                }
            }
            
            chromosome.fitness = -penalty; // 0 is best
            chromosome.conflicts = conflicts;
        }
    }

    tournamentSelection(population) {
        let best = null;
        for (let i = 0; i < 3; i++) {
            let ind = population[Math.floor(Math.random() * population.length)];
            if (best === null || ind.fitness > best.fitness) {
                best = ind;
            }
        }
        return best;
    }

    crossover(parent1, parent2) {
        let child = { genes: [], fitness: 0, conflicts: 0 };
        for (let i = 0; i < parent1.genes.length; i++) {
            let gene;
            if (Math.random() > 0.5) {
                gene = { ...parent1.genes[i] };
            } else {
                gene = { ...parent2.genes[i] };
            }
            // Preserve staff load balancing from parent1
            gene.staffId = parent1.genes[i].staffId;
            child.genes.push(gene);
        }
        return child;
    }

    mutate(chromosome) {
        // Group genes by timeslot to quickly check availability
        let timeSlotGroups = {};
        for (let gene of chromosome.genes) {
            let key = gene.dayId + '_' + gene.startTime;
            if (!timeSlotGroups[key]) timeSlotGroups[key] = [];
            timeSlotGroups[key].push(gene);
        }

        for (let i = 0; i < chromosome.genes.length; i++) {
            let gene = chromosome.genes[i];
            
            let mRate = gene.inConflict ? 0.6 : MUTATION_RATE;

            if (Math.random() < mRate) {
                if (gene.inConflict) {
                    // Smart Mutation: Exhaustively try all spots and pick the one with fewest conflicts
                    let bestParams = null;
                    let minLocalConflicts = 999;
                    
                    let shuffledDays = [...this.days].sort(() => Math.random() - 0.5);
                    let shuffledSlots = [...this.timeSlots].sort(() => Math.random() - 0.5);

                    for (let tryDay of shuffledDays) {
                        for (let trySlot of shuffledSlots) {
                            let tryKey = tryDay._id + '_' + trySlot.startTime;
                            
                            let localConflicts = 0;
                            let existingGenes = timeSlotGroups[tryKey] || [];
                            for (let g of existingGenes) {
                                if (g === gene) continue;
                                if (g.staffId === gene.staffId) localConflicts++;
                                if (g.batchId === gene.batchId) localConflicts++;
                                let commonLabs = gene.labIds.filter(id => g.labIds.includes(id));
                                if (commonLabs.length > 0) localConflicts++;
                            }
                            
                            if (localConflicts < minLocalConflicts) {
                                minLocalConflicts = localConflicts;
                                bestParams = { day: tryDay, timeSlot: trySlot };
                            }
                            if (minLocalConflicts === 0) break;
                        }
                        if (minLocalConflicts === 0) break;
                    }
                    
                    if (bestParams) {
                        // Remove from old slot
                        let oldKey = gene.dayId + '_' + gene.startTime;
                        timeSlotGroups[oldKey] = timeSlotGroups[oldKey].filter(g => g !== gene);
                        
                        gene.dayId = bestParams.day._id;
                        gene.startTime = bestParams.timeSlot.startTime;
                        gene.endTime = bestParams.timeSlot.endTime;
                        
                        // Add to new slot
                        let newKey = gene.dayId + '_' + gene.startTime;
                        if (!timeSlotGroups[newKey]) timeSlotGroups[newKey] = [];
                        timeSlotGroups[newKey].push(gene);
                    }
                } else {
                    // Random mutation for time slot only
                    let day = this.days[Math.floor(Math.random() * this.days.length)];
                    let timeSlot = this.timeSlots[Math.floor(Math.random() * this.timeSlots.length)];
                    gene.dayId = day._id;
                    gene.startTime = timeSlot.startTime;
                    gene.endTime = timeSlot.endTime;
                }
            }
            
            if (Math.random() < mRate) {
                let batch = this.batches.find(b => b._id === gene.batchId);
                if (batch) {
                    gene.labIds = this.assignLabsForBatch(batch).map(l => l._id);
                }
            }
        }
    }
}

module.exports = GeneticAlgorithm;
