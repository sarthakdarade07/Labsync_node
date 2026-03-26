package com.labsync.lms.model;

import jakarta.persistence.*;
import java.time.LocalTime;

/**
 * TimeSlot entity — defines available time blocks for lab sessions.
 * e.g., Slot 1: 08:00–10:00, Slot 2: 10:00–12:00
 */
@Entity
@Table(name = "time_slots")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String slotLabel; // e.g., "Slot-1", "Morning-1"
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @Column(nullable = false)
    private boolean active;

    /**
     * Checks whether this slot overlaps with another slot
     */
    public boolean overlaps(TimeSlot other) {
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    private static boolean $default$active() {
        return true;
    }


    public static class TimeSlotBuilder {
        private Long id;
        private String slotLabel;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean active$set;
        private boolean active$value;

        TimeSlotBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public TimeSlot.TimeSlotBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public TimeSlot.TimeSlotBuilder slotLabel(final String slotLabel) {
            this.slotLabel = slotLabel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public TimeSlot.TimeSlotBuilder startTime(final LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public TimeSlot.TimeSlotBuilder endTime(final LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public TimeSlot.TimeSlotBuilder active(final boolean active) {
            this.active$value = active;
            active$set = true;
            return this;
        }

        public TimeSlot build() {
            boolean active$value = this.active$value;
            if (!this.active$set) active$value = TimeSlot.$default$active();
            return new TimeSlot(this.id, this.slotLabel, this.startTime, this.endTime, active$value);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "TimeSlot.TimeSlotBuilder(id=" + this.id + ", slotLabel=" + this.slotLabel + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", active$value=" + this.active$value + ")";
        }
    }

    public static TimeSlot.TimeSlotBuilder builder() {
        return new TimeSlot.TimeSlotBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getSlotLabel() {
        return this.slotLabel;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setSlotLabel(final String slotLabel) {
        this.slotLabel = slotLabel;
    }

    public void setStartTime(final LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public TimeSlot() {
        this.active = TimeSlot.$default$active();
    }

    public TimeSlot(final Long id, final String slotLabel, final LocalTime startTime, final LocalTime endTime, final boolean active) {
        this.id = id;
        this.slotLabel = slotLabel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }
}
