package com.labsync.lms.dto.request;

import jakarta.validation.constraints.*;

/**
 * GaRunRequest DTO — configuration payload for triggering the Genetic Algorithm.
 */
public class GaRunRequest {
    @Min(10)
    @Max(500)
    private int populationSize = 50;
    @Min(10)
    @Max(2000)
    private int maxGenerations = 200;
    @DecimalMin("0.1")
    @DecimalMax("1.0")
    private double crossoverRate = 0.8;
    @DecimalMin("0.01")
    @DecimalMax("0.5")
    private double mutationRate = 0.05;
    /**
     * If true, deactivates existing schedule and applies the best GA result
     */
    private boolean applyResult = false;

    public void setApplyResult(boolean applyResult) {
        this.applyResult = applyResult;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof GaRunRequest)) return false;
        final GaRunRequest other = (GaRunRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.getPopulationSize() != other.getPopulationSize()) return false;
        if (this.getMaxGenerations() != other.getMaxGenerations()) return false;
        if (java.lang.Double.compare(this.getCrossoverRate(), other.getCrossoverRate()) != 0) return false;
        if (java.lang.Double.compare(this.getMutationRate(), other.getMutationRate()) != 0) return false;
        if (this.isApplyResult() != other.isApplyResult()) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof GaRunRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPopulationSize();
        result = result * PRIME + this.getMaxGenerations();
        final long $crossoverRate = java.lang.Double.doubleToLongBits(this.getCrossoverRate());
        result = result * PRIME + (int) ($crossoverRate >>> 32 ^ $crossoverRate);
        final long $mutationRate = java.lang.Double.doubleToLongBits(this.getMutationRate());
        result = result * PRIME + (int) ($mutationRate >>> 32 ^ $mutationRate);
        result = result * PRIME + (this.isApplyResult() ? 79 : 97);
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "GaRunRequest(populationSize=" + this.getPopulationSize() + ", maxGenerations=" + this.getMaxGenerations() + ", crossoverRate=" + this.getCrossoverRate() + ", mutationRate=" + this.getMutationRate() + ", applyResult=" + this.isApplyResult() + ")";
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public int getMaxGenerations() {
        return this.maxGenerations;
    }

    public double getCrossoverRate() {
        return this.crossoverRate;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    /**
     * If true, deactivates existing schedule and applies the best GA result
     */
    public boolean isApplyResult() {
        return this.applyResult;
    }

    public void setPopulationSize(final int populationSize) {
        this.populationSize = populationSize;
    }

    public void setMaxGenerations(final int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public void setCrossoverRate(final double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public void setMutationRate(final double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public GaRunRequest() {
    }
}
