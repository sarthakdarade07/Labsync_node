package com.labsync.lms.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Subject entity — lab subjects/practicals assigned to a program.
 */
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name; // e.g., "Database Lab", "OS Practicals"
    @Column(unique = true, length = 20)
    private String subjectCode; // e.g., "CS301"
    @Column(length = 255)
    private String description;
    @Column(nullable = false)
    private Integer hoursPerWeek;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

    private static Integer $default$hoursPerWeek() {
        return 2;
    }


    public static class SubjectBuilder {
        private Long id;
        private String name;
        private String subjectCode;
        private String description;
        private boolean hoursPerWeek$set;
        private Integer hoursPerWeek$value;
        private Program program;

        SubjectBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder subjectCode(final String subjectCode) {
            this.subjectCode = subjectCode;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder hoursPerWeek(final Integer hoursPerWeek) {
            this.hoursPerWeek$value = hoursPerWeek;
            hoursPerWeek$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Subject.SubjectBuilder program(final Program program) {
            this.program = program;
            return this;
        }

        public Subject build() {
            Integer hoursPerWeek$value = this.hoursPerWeek$value;
            if (!this.hoursPerWeek$set) hoursPerWeek$value = Subject.$default$hoursPerWeek();
            return new Subject(this.id, this.name, this.subjectCode, this.description, hoursPerWeek$value, this.program);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Subject.SubjectBuilder(id=" + this.id + ", name=" + this.name + ", subjectCode=" + this.subjectCode + ", description=" + this.description + ", hoursPerWeek$value=" + this.hoursPerWeek$value + ", program=" + this.program + ")";
        }
    }

    public static Subject.SubjectBuilder builder() {
        return new Subject.SubjectBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getHoursPerWeek() {
        return this.hoursPerWeek;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSubjectCode(final String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setHoursPerWeek(final Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setProgram(final Program program) {
        this.program = program;
    }

    public Subject() {
        this.hoursPerWeek = Subject.$default$hoursPerWeek();
    }

    public Subject(final Long id, final String name, final String subjectCode, final String description, final Integer hoursPerWeek, final Program program) {
        this.id = id;
        this.name = name;
        this.subjectCode = subjectCode;
        this.description = description;
        this.hoursPerWeek = hoursPerWeek;
        this.program = program;
    }
}
