package com.labsync.lms.dto.request;

import jakarta.validation.constraints.*;

/**
 * ComputerStatusRequest DTO — payload for updating computer availability in a lab.
 */
public class ComputerStatusRequest {
    @NotNull
    @Min(0)
    private Integer workingComputers;
    @NotNull
    @Min(0)
    private Integer faultyComputers;

    public ComputerStatusRequest() {
    }

    public Integer getWorkingComputers() {
        return this.workingComputers;
    }

    public Integer getFaultyComputers() {
        return this.faultyComputers;
    }

    public void setWorkingComputers(final Integer workingComputers) {
        this.workingComputers = workingComputers;
    }

    public void setFaultyComputers(final Integer faultyComputers) {
        this.faultyComputers = faultyComputers;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ComputerStatusRequest)) return false;
        final ComputerStatusRequest other = (ComputerStatusRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$workingComputers = this.getWorkingComputers();
        final java.lang.Object other$workingComputers = other.getWorkingComputers();
        if (this$workingComputers == null ? other$workingComputers != null : !this$workingComputers.equals(other$workingComputers)) return false;
        final java.lang.Object this$faultyComputers = this.getFaultyComputers();
        final java.lang.Object other$faultyComputers = other.getFaultyComputers();
        if (this$faultyComputers == null ? other$faultyComputers != null : !this$faultyComputers.equals(other$faultyComputers)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ComputerStatusRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $workingComputers = this.getWorkingComputers();
        result = result * PRIME + ($workingComputers == null ? 43 : $workingComputers.hashCode());
        final java.lang.Object $faultyComputers = this.getFaultyComputers();
        result = result * PRIME + ($faultyComputers == null ? 43 : $faultyComputers.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ComputerStatusRequest(workingComputers=" + this.getWorkingComputers() + ", faultyComputers=" + this.getFaultyComputers() + ")";
    }
}
