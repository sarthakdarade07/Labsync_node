package com.labsync.lms.dto.request;

import jakarta.validation.constraints.*;

/**
 * LabRequest DTO — payload for creating/updating a lab.
 */
public class LabRequest {
    @NotBlank
    @Size(max = 50)
    private String labName;
    @NotNull
    @Min(1)
    private Integer capacity;
    @NotNull
    @Min(0)
    private Integer totalComputers;
    @Min(0)
    private Integer workingComputers;
    @Min(0)
    private Integer faultyComputers;
    @Size(max = 50)
    private String osType;
    @Size(max = 100)
    private String location;
    private Boolean available;

    public LabRequest() {
    }

    public String getLabName() {
        return this.labName;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Integer getTotalComputers() {
        return this.totalComputers;
    }

    public Integer getWorkingComputers() {
        return this.workingComputers;
    }

    public Integer getFaultyComputers() {
        return this.faultyComputers;
    }

    public String getOsType() {
        return this.osType;
    }

    public String getLocation() {
        return this.location;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    public void setLabName(final String labName) {
        this.labName = labName;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public void setTotalComputers(final Integer totalComputers) {
        this.totalComputers = totalComputers;
    }

    public void setWorkingComputers(final Integer workingComputers) {
        this.workingComputers = workingComputers;
    }

    public void setFaultyComputers(final Integer faultyComputers) {
        this.faultyComputers = faultyComputers;
    }

    public void setOsType(final String osType) {
        this.osType = osType;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setAvailable(final Boolean available) {
        this.available = available;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LabRequest)) return false;
        final LabRequest other = (LabRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$capacity = this.getCapacity();
        final java.lang.Object other$capacity = other.getCapacity();
        if (this$capacity == null ? other$capacity != null : !this$capacity.equals(other$capacity)) return false;
        final java.lang.Object this$totalComputers = this.getTotalComputers();
        final java.lang.Object other$totalComputers = other.getTotalComputers();
        if (this$totalComputers == null ? other$totalComputers != null : !this$totalComputers.equals(other$totalComputers)) return false;
        final java.lang.Object this$workingComputers = this.getWorkingComputers();
        final java.lang.Object other$workingComputers = other.getWorkingComputers();
        if (this$workingComputers == null ? other$workingComputers != null : !this$workingComputers.equals(other$workingComputers)) return false;
        final java.lang.Object this$faultyComputers = this.getFaultyComputers();
        final java.lang.Object other$faultyComputers = other.getFaultyComputers();
        if (this$faultyComputers == null ? other$faultyComputers != null : !this$faultyComputers.equals(other$faultyComputers)) return false;
        final java.lang.Object this$available = this.getAvailable();
        final java.lang.Object other$available = other.getAvailable();
        if (this$available == null ? other$available != null : !this$available.equals(other$available)) return false;
        final java.lang.Object this$labName = this.getLabName();
        final java.lang.Object other$labName = other.getLabName();
        if (this$labName == null ? other$labName != null : !this$labName.equals(other$labName)) return false;
        final java.lang.Object this$osType = this.getOsType();
        final java.lang.Object other$osType = other.getOsType();
        if (this$osType == null ? other$osType != null : !this$osType.equals(other$osType)) return false;
        final java.lang.Object this$location = this.getLocation();
        final java.lang.Object other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LabRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $capacity = this.getCapacity();
        result = result * PRIME + ($capacity == null ? 43 : $capacity.hashCode());
        final java.lang.Object $totalComputers = this.getTotalComputers();
        result = result * PRIME + ($totalComputers == null ? 43 : $totalComputers.hashCode());
        final java.lang.Object $workingComputers = this.getWorkingComputers();
        result = result * PRIME + ($workingComputers == null ? 43 : $workingComputers.hashCode());
        final java.lang.Object $faultyComputers = this.getFaultyComputers();
        result = result * PRIME + ($faultyComputers == null ? 43 : $faultyComputers.hashCode());
        final java.lang.Object $available = this.getAvailable();
        result = result * PRIME + ($available == null ? 43 : $available.hashCode());
        final java.lang.Object $labName = this.getLabName();
        result = result * PRIME + ($labName == null ? 43 : $labName.hashCode());
        final java.lang.Object $osType = this.getOsType();
        result = result * PRIME + ($osType == null ? 43 : $osType.hashCode());
        final java.lang.Object $location = this.getLocation();
        result = result * PRIME + ($location == null ? 43 : $location.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "LabRequest(labName=" + this.getLabName() + ", capacity=" + this.getCapacity() + ", totalComputers=" + this.getTotalComputers() + ", workingComputers=" + this.getWorkingComputers() + ", faultyComputers=" + this.getFaultyComputers() + ", osType=" + this.getOsType() + ", location=" + this.getLocation() + ", available=" + this.getAvailable() + ")";
    }
}
