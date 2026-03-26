package com.labsync.lms.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Program entity — e.g., B.Tech Computer Science, MCA.
 * A program has many batches.
 */
@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String name;
    @Column(length = 10)
    private String code; // e.g., "BTECH-CS"
    @Column(length = 255)
    private String description;
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Batch> batches;


    public static class ProgramBuilder {
        private Long id;
        private String name;
        private String code;
        private String description;
        private List<Batch> batches;

        ProgramBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Program.ProgramBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Program.ProgramBuilder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Program.ProgramBuilder code(final String code) {
            this.code = code;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Program.ProgramBuilder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Program.ProgramBuilder batches(final List<Batch> batches) {
            this.batches = batches;
            return this;
        }

        public Program build() {
            return new Program(this.id, this.name, this.code, this.description, this.batches);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Program.ProgramBuilder(id=" + this.id + ", name=" + this.name + ", code=" + this.code + ", description=" + this.description + ", batches=" + this.batches + ")";
        }
    }

    public static Program.ProgramBuilder builder() {
        return new Program.ProgramBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Batch> getBatches() {
        return this.batches;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setBatches(final List<Batch> batches) {
        this.batches = batches;
    }

    public Program() {
    }

    public Program(final Long id, final String name, final String code, final String description, final List<Batch> batches) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.batches = batches;
    }
}
