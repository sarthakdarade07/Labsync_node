package com.labsync.lms.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Lab entity — represents a computer laboratory.
 * Tracks capacity, computer status, and OS type.
 */
@Entity
@Table(name = "labs")
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String labName; // e.g., "Lab-101", "Advanced Computing Lab"
    @Column(nullable = false)
    private Integer capacity; // max number of students
    @Column(nullable = false)
    private Integer totalComputers;
    @Column(nullable = false)
    private Integer workingComputers;
    @Column(nullable = false)
    private Integer faultyComputers;
    @Column(length = 50)
    private String osType; // e.g., "Windows 11", "Ubuntu 22.04", "Dual Boot"
    @Column(length = 100)
    private String location; // e.g., "Building A, Floor 2"
    @Column(nullable = false)
    private boolean available;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    @JsonIgnore
    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    /**
     * Derived: computers under maintenance
     */
    @Transient
    public int getUnderMaintenanceComputers() {
        return totalComputers - workingComputers - faultyComputers;
    }

    private static Integer $default$workingComputers() {
        return 0;
    }

    private static Integer $default$faultyComputers() {
        return 0;
    }

    private static boolean $default$available() {
        return true;
    }


    public static class LabBuilder {
        private Long id;
        private String labName;
        private Integer capacity;
        private Integer totalComputers;
        private boolean workingComputers$set;
        private Integer workingComputers$value;
        private boolean faultyComputers$set;
        private Integer faultyComputers$value;
        private String osType;
        private String location;
        private boolean available$set;
        private boolean available$value;
        private LocalDateTime lastUpdated;
        private List<Schedule> schedules;

        LabBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder labName(final String labName) {
            this.labName = labName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder capacity(final Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder totalComputers(final Integer totalComputers) {
            this.totalComputers = totalComputers;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder workingComputers(final Integer workingComputers) {
            this.workingComputers$value = workingComputers;
            workingComputers$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder faultyComputers(final Integer faultyComputers) {
            this.faultyComputers$value = faultyComputers;
            faultyComputers$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder osType(final String osType) {
            this.osType = osType;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder location(final String location) {
            this.location = location;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder available(final boolean available) {
            this.available$value = available;
            available$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder lastUpdated(final LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Lab.LabBuilder schedules(final List<Schedule> schedules) {
            this.schedules = schedules;
            return this;
        }

        public Lab build() {
            Integer workingComputers$value = this.workingComputers$value;
            if (!this.workingComputers$set) workingComputers$value = Lab.$default$workingComputers();
            Integer faultyComputers$value = this.faultyComputers$value;
            if (!this.faultyComputers$set) faultyComputers$value = Lab.$default$faultyComputers();
            boolean available$value = this.available$value;
            if (!this.available$set) available$value = Lab.$default$available();
            return new Lab(this.id, this.labName, this.capacity, this.totalComputers, workingComputers$value, faultyComputers$value, this.osType, this.location, available$value, this.lastUpdated, this.schedules);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Lab.LabBuilder(id=" + this.id + ", labName=" + this.labName + ", capacity=" + this.capacity + ", totalComputers=" + this.totalComputers + ", workingComputers$value=" + this.workingComputers$value + ", faultyComputers$value=" + this.faultyComputers$value + ", osType=" + this.osType + ", location=" + this.location + ", available$value=" + this.available$value + ", lastUpdated=" + this.lastUpdated + ", schedules=" + this.schedules + ")";
        }
    }

    public static Lab.LabBuilder builder() {
        return new Lab.LabBuilder();
    }

    public Long getId() {
        return this.id;
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

    public boolean isAvailable() {
        return this.available;
    }

    public LocalDateTime getLastUpdated() {
        return this.lastUpdated;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public void setAvailable(final boolean available) {
        this.available = available;
    }

    public void setLastUpdated(final LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setSchedules(final List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Lab() {
        this.workingComputers = Lab.$default$workingComputers();
        this.faultyComputers = Lab.$default$faultyComputers();
        this.available = Lab.$default$available();
    }

    public Lab(final Long id, final String labName, final Integer capacity, final Integer totalComputers, final Integer workingComputers, final Integer faultyComputers, final String osType, final String location, final boolean available, final LocalDateTime lastUpdated, final List<Schedule> schedules) {
        this.id = id;
        this.labName = labName;
        this.capacity = capacity;
        this.totalComputers = totalComputers;
        this.workingComputers = workingComputers;
        this.faultyComputers = faultyComputers;
        this.osType = osType;
        this.location = location;
        this.available = available;
        this.lastUpdated = lastUpdated;
        this.schedules = schedules;
    }
}
