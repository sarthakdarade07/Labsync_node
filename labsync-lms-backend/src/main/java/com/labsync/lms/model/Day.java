package com.labsync.lms.model;

import java.time.LocalTime;

import jakarta.persistence.*;

/**
 * Day entity — days of the week for scheduling.
 * e.g., Monday=1, Tuesday=2, ... Saturday=6
 */
@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String dayName; // e.g., "Monday"
    @Column(nullable = false, unique = true)
    private Integer dayOrder; // 1=Monday, 6=Saturday
    @Column(nullable = false)
    private boolean active;
    @Column
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "HH:mm[:ss]")
    private LocalTime startTime;
    @Column
    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "HH:mm[:ss]")
    private LocalTime endTime;

    private static boolean $default$active() {
        return true;
    }


    public static class DayBuilder {
        private Long id;
        private String dayName;
        private Integer dayOrder;
        private boolean active$set;
        private boolean active$value;
        private LocalTime startTime;
        private LocalTime endTime;

        DayBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Day.DayBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Day.DayBuilder dayName(final String dayName) {
            this.dayName = dayName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Day.DayBuilder dayOrder(final Integer dayOrder) {
            this.dayOrder = dayOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Day.DayBuilder active(final boolean active) {
            this.active$value = active;
            active$set = true;
            return this;
        }

        public Day.DayBuilder startTime(final LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Day.DayBuilder endTime(final LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Day build() {
            boolean active$value = this.active$value;
            if (!this.active$set) active$value = Day.$default$active();
            return new Day(this.id, this.dayName, this.dayOrder, active$value, this.startTime, this.endTime);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Day.DayBuilder(id=" + this.id + ", dayName=" + this.dayName + ", dayOrder=" + this.dayOrder + ", active$value=" + this.active$value + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ")";
        }
    }

    public static Day.DayBuilder builder() {
        return new Day.DayBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getDayName() {
        return this.dayName;
    }

    public Integer getDayOrder() {
        return this.dayOrder;
    }

    public boolean isActive() {
        return this.active;
    }

    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public void setStartTime(final LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(final LocalTime endTime) { this.endTime = endTime; }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setDayName(final String dayName) {
        this.dayName = dayName;
    }

    public void setDayOrder(final Integer dayOrder) {
        this.dayOrder = dayOrder;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Day() {
        this.active = Day.$default$active();
    }

    public Day(final Long id, final String dayName, final Integer dayOrder, final boolean active, final LocalTime startTime, final LocalTime endTime) {
        this.id = id;
        this.dayName = dayName;
        this.dayOrder = dayOrder;
        this.active = active;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
