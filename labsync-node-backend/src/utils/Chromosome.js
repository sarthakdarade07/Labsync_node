class Chromosome {
    constructor(genes = []) {
        this.genes = genes;
        this.fitnessScore = 0.0;
    }

    deepCopy() {
        const copiedGenes = this.genes.map(gene => ({
            batch: gene.batch,
            subject: gene.subject,
            staffList: [...gene.staffList],
            labs: [...gene.labs],
            day: gene.day,
            startTime: gene.startTime,
            endTime: gene.endTime
        }));
        const copy = new Chromosome(copiedGenes);
        copy.fitnessScore = this.fitnessScore;
        return copy;
    }

    compareTo(other) {
        return other.fitnessScore - this.fitnessScore; // Descending
    }
}

module.exports = Chromosome;
