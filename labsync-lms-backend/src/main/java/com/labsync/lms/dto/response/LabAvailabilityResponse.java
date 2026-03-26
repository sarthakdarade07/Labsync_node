package com.labsync.lms.dto.response;


/**
 * LabAvailabilityResponse DTO — summary of a lab's current computer status.
 */
public class LabAvailabilityResponse {
    private Long labId;
    private String labName;
    private String location;
    private String osType;
    private Integer capacity;
    private Integer totalComputers;
    private Integer workingComputers;
    private Integer faultyComputers;
    private Integer underMaintenanceComputers;
    private boolean available;
    private double availabilityPercentage;


    public static class LabAvailabilityResponseBuilder {
        private Long labId;
        private String labName;
        private String location;
        private String osType;
        private Integer capacity;
        private Integer totalComputers;
        private Integer workingComputers;
        private Integer faultyComputers;
        private Integer underMaintenanceComputers;
        private boolean available;
        private double availabilityPercentage;

        LabAvailabilityResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder labId(final Long labId) {
            this.labId = labId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder labName(final String labName) {
            this.labName = labName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder location(final String location) {
            this.location = location;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder osType(final String osType) {
            this.osType = osType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder capacity(final Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder totalComputers(final Integer totalComputers) {
            this.totalComputers = totalComputers;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder workingComputers(final Integer workingComputers) {
            this.workingComputers = workingComputers;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder faultyComputers(final Integer faultyComputers) {
            this.faultyComputers = faultyComputers;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder underMaintenanceComputers(final Integer underMaintenanceComputers) {
            this.underMaintenanceComputers = underMaintenanceComputers;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder available(final boolean available) {
            this.available = available;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public LabAvailabilityResponse.LabAvailabilityResponseBuilder availabilityPercentage(final double availabilityPercentage) {
            this.availabilityPercentage = availabilityPercentage;
            return this;
        }

        public LabAvailabilityResponse build() {
            return new LabAvailabilityResponse(this.labId, this.labName, this.location, this.osType, this.capacity, this.totalComputers, this.workingComputers, this.faultyComputers, this.underMaintenanceComputers, this.available, this.availabilityPercentage);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "LabAvailabilityResponse.LabAvailabilityResponseBuilder(labId=" + this.labId + ", labName=" + this.labName + ", location=" + this.location + ", osType=" + this.osType + ", capacity=" + this.capacity + ", totalComputers=" + this.totalComputers + ", workingComputers=" + this.workingComputers + ", faultyComputers=" + this.faultyComputers + ", underMaintenanceComputers=" + this.underMaintenanceComputers + ", available=" + this.available + ", availabilityPercentage=" + this.availabilityPercentage + ")";
        }
    }

    public static LabAvailabilityResponse.LabAvailabilityResponseBuilder builder() {
        return new LabAvailabilityResponse.LabAvailabilityResponseBuilder();
    }

    public Long getLabId() {
        return this.labId;
    }

    public String getLabName() {
        return this.labName;
    }

    public String getLocation() {
        return this.location;
    }

    public String getOsType() {
        return this.osType;
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

    public Integer getUnderMaintenanceComputers() {
        return this.underMaintenanceComputers;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public double getAvailabilityPercentage() {
        return this.availabilityPercentage;
    }

    public void setLabId(final Long labId) {
        this.labId = labId;
    }

    public void setLabName(final String labName) {
        this.labName = labName;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public void setOsType(final String osType) {
        this.osType = osType;
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

    public void setUnderMaintenanceComputers(final Integer underMaintenanceComputers) {
        this.underMaintenanceComputers = underMaintenanceComputers;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }

    public void setAvailabilityPercentage(final double availabilityPercentage) {
        this.availabilityPercentage = availabilityPercentage;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LabAvailabilityResponse)) return false;
        final LabAvailabilityResponse other = (LabAvailabilityResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isAvailable() != other.isAvailable()) return false;
        if (java.lang.Double.compare(this.getAvailabilityPercentage(), other.getAvailabilityPercentage()) != 0) return false;
        final java.lang.Object this$labId = this.getLabId();
        final java.lang.Object other$labId = other.getLabId();
        if (this$labId == null ? other$labId != null : !this$labId.equals(other$labId)) return false;
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
        final java.lang.Object this$underMaintenanceComputers = this.getUnderMaintenanceComputers();
        final java.lang.Object other$underMaintenanceComputers = other.getUnderMaintenanceComputers();
        if (this$underMaintenanceComputers == null ? other$underMaintenanceComputers != null : !this$underMaintenanceComputers.equals(other$underMaintenanceComputers)) return false;
        final java.lang.Object this$labName = this.getLabName();
        final java.lang.Object other$labName = other.getLabName();
        if (this$labName == null ? other$labName != null : !this$labName.equals(other$labName)) return false;
        final java.lang.Object this$location = this.getLocation();
        final java.lang.Object other$location = other.getLocation();
        if (this$location == null ? other$location != null : !this$location.equals(other$location)) return false;
        final java.lang.Object this$osType = this.getOsType();
        final java.lang.Object other$osType = other.getOsType();
        if (this$osType == null ? other$osType != null : !this$osType.equals(other$osType)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LabAvailabilityResponse;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isAvailable() ? 79 : 97);
        final long $availabilityPercentage = java.lang.Double.doubleToLongBits(this.getAvailabilityPercentage());
        result = result * PRIME + (int) ($availabilityPercentage >>> 32 ^ $availabilityPercentage);
        final java.lang.Object $labId = this.getLabId();
        result = result * PRIME + ($labId == null ? 43 : $labId.hashCode());
        final java.lang.Object $capacity = this.getCapacity();
        result = result * PRIME + ($capacity == null ? 43 : $capacity.hashCode());
        final java.lang.Object $totalComputers = this.getTotalComputers();
        result = result * PRIME + ($totalComputers == null ? 43 : $totalComputers.hashCode());
        final java.lang.Object $workingComputers = this.getWorkingComputers();
        result = result * PRIME + ($workingComputers == null ? 43 : $workingComputers.hashCode());
        final java.lang.Object $faultyComputers = this.getFaultyComputers();
        result = result * PRIME + ($faultyComputers == null ? 43 : $faultyComputers.hashCode());
        final java.lang.Object $underMaintenanceComputers = this.getUnderMaintenanceComputers();
        result = result * PRIME + ($underMaintenanceComputers == null ? 43 : $underMaintenanceComputers.hashCode());
        final java.lang.Object $labName = this.getLabName();
        result = result * PRIME + ($labName == null ? 43 : $labName.hashCode());
        final java.lang.Object $location = this.getLocation();
        result = result * PRIME + ($location == null ? 43 : $location.hashCode());
        final java.lang.Object $osType = this.getOsType();
        result = result * PRIME + ($osType == null ? 43 : $osType.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "LabAvailabilityResponse(labId=" + this.getLabId() + ", labName=" + this.getLabName() + ", location=" + this.getLocation() + ", osType=" + this.getOsType() + ", capacity=" + this.getCapacity() + ", totalComputers=" + this.getTotalComputers() + ", workingComputers=" + this.getWorkingComputers() + ", faultyComputers=" + this.getFaultyComputers() + ", underMaintenanceComputers=" + this.getUnderMaintenanceComputers() + ", available=" + this.isAvailable() + ", availabilityPercentage=" + this.getAvailabilityPercentage() + ")";
    }

    public LabAvailabilityResponse(final Long labId, final String labName, final String location, final String osType, final Integer capacity, final Integer totalComputers, final Integer workingComputers, final Integer faultyComputers, final Integer underMaintenanceComputers, final boolean available, final double availabilityPercentage) {
        this.labId = labId;
        this.labName = labName;
        this.location = location;
        this.osType = osType;
        this.capacity = capacity;
        this.totalComputers = totalComputers;
        this.workingComputers = workingComputers;
        this.faultyComputers = faultyComputers;
        this.underMaintenanceComputers = underMaintenanceComputers;
        this.available = available;
        this.availabilityPercentage = availabilityPercentage;
    }

    public LabAvailabilityResponse() {
    }
}
