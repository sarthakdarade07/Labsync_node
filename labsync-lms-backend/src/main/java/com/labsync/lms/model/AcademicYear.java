package com.labsync.lms.model;

import jakarta.persistence.*;

/**
 * AcademicYear entity — e.g., "2023-24", "2024-25".
 * Associates batches to a specific academic year.
 */
@Entity
@Table(name = "academic_years")
public class AcademicYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String yearLabel; // e.g., "2024-25"
    @Column(nullable = false)
    private Integer startYear;
    @Column(nullable = false)
    private Integer endYear;
    @Column(nullable = false)
    private boolean active;

    private static boolean $default$active() {
        return true;
    }


    public static class AcademicYearBuilder {
        private Long id;
        private String yearLabel;
        private Integer startYear;
        private Integer endYear;
        private boolean active$set;
        private boolean active$value;

        AcademicYearBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public AcademicYear.AcademicYearBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AcademicYear.AcademicYearBuilder yearLabel(final String yearLabel) {
            this.yearLabel = yearLabel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AcademicYear.AcademicYearBuilder startYear(final Integer startYear) {
            this.startYear = startYear;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AcademicYear.AcademicYearBuilder endYear(final Integer endYear) {
            this.endYear = endYear;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public AcademicYear.AcademicYearBuilder active(final boolean active) {
            this.active$value = active;
            active$set = true;
            return this;
        }

        public AcademicYear build() {
            boolean active$value = this.active$value;
            if (!this.active$set) active$value = AcademicYear.$default$active();
            return new AcademicYear(this.id, this.yearLabel, this.startYear, this.endYear, active$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "AcademicYear.AcademicYearBuilder(id=" + this.id + ", yearLabel=" + this.yearLabel + ", startYear=" + this.startYear + ", endYear=" + this.endYear + ", active$value=" + this.active$value + ")";
        }
    }

    public static AcademicYear.AcademicYearBuilder builder() {
        return new AcademicYear.AcademicYearBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getYearLabel() {
        return this.yearLabel;
    }

    public Integer getStartYear() {
        return this.startYear;
    }

    public Integer getEndYear() {
        return this.endYear;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setYearLabel(final String yearLabel) {
        this.yearLabel = yearLabel;
    }

    public void setStartYear(final Integer startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(final Integer endYear) {
        this.endYear = endYear;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public AcademicYear() {
        this.active = AcademicYear.$default$active();
    }

    public AcademicYear(final Long id, final String yearLabel, final Integer startYear, final Integer endYear, final boolean active) {
        this.id = id;
        this.yearLabel = yearLabel;
        this.startYear = startYear;
        this.endYear = endYear;
        this.active = active;
    }
}
