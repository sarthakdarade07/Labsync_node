package com.labsync.lms.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Student entity — core student record.
 * Linked to Batch, Program, and AcademicYear.
 */
@Entity
@Table(name = "students", uniqueConstraints = @UniqueConstraint(columnNames = "prn"))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String fullName;
    @Column(nullable = false, unique = true, length = 20)
    private String prn; // Permanent Registration Number
    @Column(length = 100)
    private String email;
    @Column(length = 15)
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    public static class StudentBuilder {
        private Long id;
        private String fullName;
        private String prn;
        private String email;
        private String phone;
        private Batch batch;
        private Program program;
        private AcademicYear academicYear;
        private LocalDateTime createdAt;

        StudentBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder prn(final String prn) {
            this.prn = prn;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder batch(final Batch batch) {
            this.batch = batch;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder program(final Program program) {
            this.program = program;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder academicYear(final AcademicYear academicYear) {
            this.academicYear = academicYear;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Student.StudentBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Student build() {
            return new Student(this.id, this.fullName, this.prn, this.email, this.phone, this.batch, this.program, this.academicYear, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Student.StudentBuilder(id=" + this.id + ", fullName=" + this.fullName + ", prn=" + this.prn + ", email=" + this.email + ", phone=" + this.phone + ", batch=" + this.batch + ", program=" + this.program + ", academicYear=" + this.academicYear + ", createdAt=" + this.createdAt + ")";
        }
    }

    public static Student.StudentBuilder builder() {
        return new Student.StudentBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getPrn() {
        return this.prn;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Batch getBatch() {
        return this.batch;
    }

    public Program getProgram() {
        return this.program;
    }

    public AcademicYear getAcademicYear() {
        return this.academicYear;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setPrn(final String prn) {
        this.prn = prn;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public void setBatch(final Batch batch) {
        this.batch = batch;
    }

    public void setProgram(final Program program) {
        this.program = program;
    }

    public void setAcademicYear(final AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Student() {
    }

    public Student(final Long id, final String fullName, final String prn, final String email, final String phone, final Batch batch, final Program program, final AcademicYear academicYear, final LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.prn = prn;
        this.email = email;
        this.phone = phone;
        this.batch = batch;
        this.program = program;
        this.academicYear = academicYear;
        this.createdAt = createdAt;
    }
}
