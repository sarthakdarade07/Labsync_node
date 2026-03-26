package com.labsync.lms.model;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Batch entity — groups students by division/year.
 * Links to Program and AcademicYear.
 */
@Entity
@Table(name = "batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String batchName; // e.g., "SE-A", "TE-B"
    @Column(nullable = false, length = 10)
    private String division;
    @Column(nullable = false)
    private Integer studentCount;
    @Column(length = 10)
    private String semester; // e.g., "SEM-3", "SEM-4"
    @Column(length = 50)
    private String osRequirement; // e.g., "Any", "Windows 11", "Ubuntu 22.04"
    @Column(nullable = false)
    private Integer labsPerWeek; // e.g., 1
    @Column(nullable = false)
    private Integer requiredHours; // e.g., 2 hours per session
    @Column
    private Integer totalHours;
    @Column
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "HH:mm[:ss]")
    private LocalTime startTime;
    @Column
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "HH:mm[:ss]")
    private LocalTime endTime;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;
    @JsonIgnore
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;
    @JsonIgnore
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "batch_subjects",
        joinColumns = @JoinColumn(name = "batch_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;


    public static class BatchBuilder {
        private Long id;
        private String batchName;
        private String division;
        private Integer studentCount;
        private String semester;
        private String osRequirement;
        private Integer labsPerWeek;
        private Integer requiredHours;
        private Integer totalHours;
        private LocalTime startTime;
        private LocalTime endTime;
        private Program program;
        private AcademicYear academicYear;
        private List<Student> students;
        private List<Schedule> schedules;
        private List<Subject> subjects;

        BatchBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder batchName(final String batchName) {
            this.batchName = batchName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder division(final String division) {
            this.division = division;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder studentCount(final Integer studentCount) {
            this.studentCount = studentCount;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder semester(final String semester) {
            this.semester = semester;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder osRequirement(final String osRequirement) {
            this.osRequirement = osRequirement;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder labsPerWeek(final Integer labsPerWeek) {
            this.labsPerWeek = labsPerWeek;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder requiredHours(final Integer requiredHours) {
            this.requiredHours = requiredHours;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder totalHours(final Integer totalHours) {
            this.totalHours = totalHours;
            return this;
        }

        public Batch.BatchBuilder startTime(final LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Batch.BatchBuilder endTime(final LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder program(final Program program) {
            this.program = program;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder academicYear(final AcademicYear academicYear) {
            this.academicYear = academicYear;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder students(final List<Student> students) {
            this.students = students;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder schedules(final List<Schedule> schedules) {
            this.schedules = schedules;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Batch.BatchBuilder subjects(final List<Subject> subjects) {
            this.subjects = subjects;
            return this;
        }

        public Batch build() {
            return new Batch(this.id, this.batchName, this.division, this.studentCount, this.semester, this.osRequirement, this.labsPerWeek, this.requiredHours, this.totalHours, this.startTime, this.endTime, this.program, this.academicYear, this.students, this.schedules, this.subjects);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Batch.BatchBuilder(id=" + this.id + ", batchName=" + this.batchName + ", division=" + this.division + ", studentCount=" + this.studentCount + ", semester=" + this.semester + ", osRequirement=" + this.osRequirement + ", labsPerWeek=" + this.labsPerWeek + ", requiredHours=" + this.requiredHours + ", totalHours=" + this.totalHours + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", program=" + this.program + ", academicYear=" + this.academicYear + ", students=" + this.students + ", schedules=" + this.schedules + ", subjects=" + this.subjects + ")";
        }
    }

    public static Batch.BatchBuilder builder() {
        return new Batch.BatchBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getBatchName() {
        return this.batchName;
    }

    public String getDivision() {
        return this.division;
    }

    public Integer getStudentCount() {
        return this.studentCount;
    }

    public String getSemester() {
        return this.semester;
    }

    public String getOsRequirement() {
        return this.osRequirement;
    }

    public Integer getLabsPerWeek() {
        return this.labsPerWeek;
    }

    public Integer getRequiredHours() {
        return this.requiredHours;
    }

    public Integer getTotalHours() { return this.totalHours; }
    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public void setTotalHours(final Integer totalHours) { this.totalHours = totalHours; }
    public void setStartTime(final LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(final LocalTime endTime) { this.endTime = endTime; }

    public Program getProgram() {
        return this.program;
    }

    public AcademicYear getAcademicYear() {
        return this.academicYear;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public List<Subject> getSubjects() {
        return this.subjects;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setBatchName(final String batchName) {
        this.batchName = batchName;
    }

    public void setDivision(final String division) {
        this.division = division;
    }

    public void setStudentCount(final Integer studentCount) {
        this.studentCount = studentCount;
    }

    public void setSemester(final String semester) {
        this.semester = semester;
    }

    public void setOsRequirement(final String osRequirement) {
        this.osRequirement = osRequirement;
    }

    public void setLabsPerWeek(final Integer labsPerWeek) {
        this.labsPerWeek = labsPerWeek;
    }

    public void setRequiredHours(final Integer requiredHours) {
        this.requiredHours = requiredHours;
    }

    public void setProgram(final Program program) {
        this.program = program;
    }

    public void setAcademicYear(final AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public void setStudents(final List<Student> students) {
        this.students = students;
    }

    public void setSchedules(final List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void setSubjects(final List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Batch() {
        this.labsPerWeek = 1;
        this.requiredHours = 2;
    }

    public Batch(final Long id, final String batchName, final String division, final Integer studentCount, final String semester, final String osRequirement, final Integer labsPerWeek, final Integer requiredHours, final Integer totalHours, final LocalTime startTime, final LocalTime endTime, final Program program, final AcademicYear academicYear, final List<Student> students, final List<Schedule> schedules, final List<Subject> subjects) {
        this.id = id;
        this.batchName = batchName;
        this.division = division;
        this.studentCount = studentCount;
        this.semester = semester;
        this.osRequirement = osRequirement;
        this.labsPerWeek = labsPerWeek != null ? labsPerWeek : 1;
        this.requiredHours = requiredHours != null ? requiredHours : 2;
        this.totalHours = totalHours;
        this.startTime = startTime;
        this.endTime = endTime;
        this.program = program;
        this.academicYear = academicYear;
        this.students = students;
        this.schedules = schedules;
        this.subjects = subjects;
    }
}
