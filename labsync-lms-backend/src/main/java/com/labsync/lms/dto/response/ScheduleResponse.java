package com.labsync.lms.dto.response;

import java.time.LocalTime;

/**
 * ScheduleResponse DTO — flattened view of a schedule entry for timetable display.
 */
public class ScheduleResponse {
    private Long scheduleId;
    // Batch info
    private Long batchId;
    private String batchName;
    private String division;
    // Subject info
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    // Staff info
    private Long staffId;
    private String staffName;
    // Lab info
    private Long labId;
    private String labName;
    // Day info
    private Long dayId;
    private String dayName;
    private Integer dayOrder;
    // Time slot info
    private Long slotId;
    private String slotLabel;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean generatedByGA;


    public static class ScheduleResponseBuilder {
        private Long scheduleId;
        private Long batchId;
        private String batchName;
        private String division;
        private Long subjectId;
        private String subjectName;
        private String subjectCode;
        private Long staffId;
        private String staffName;
        private Long labId;
        private String labName;
        private Long dayId;
        private String dayName;
        private Integer dayOrder;
        private Long slotId;
        private String slotLabel;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean generatedByGA;

        ScheduleResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder scheduleId(final Long scheduleId) {
            this.scheduleId = scheduleId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder batchId(final Long batchId) {
            this.batchId = batchId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder batchName(final String batchName) {
            this.batchName = batchName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder division(final String division) {
            this.division = division;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder subjectId(final Long subjectId) {
            this.subjectId = subjectId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder subjectName(final String subjectName) {
            this.subjectName = subjectName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder subjectCode(final String subjectCode) {
            this.subjectCode = subjectCode;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder staffId(final Long staffId) {
            this.staffId = staffId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder staffName(final String staffName) {
            this.staffName = staffName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder labId(final Long labId) {
            this.labId = labId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder labName(final String labName) {
            this.labName = labName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder dayId(final Long dayId) {
            this.dayId = dayId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder dayName(final String dayName) {
            this.dayName = dayName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder dayOrder(final Integer dayOrder) {
            this.dayOrder = dayOrder;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder slotId(final Long slotId) {
            this.slotId = slotId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder slotLabel(final String slotLabel) {
            this.slotLabel = slotLabel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder startTime(final LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder endTime(final LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ScheduleResponse.ScheduleResponseBuilder generatedByGA(final boolean generatedByGA) {
            this.generatedByGA = generatedByGA;
            return this;
        }

        public ScheduleResponse build() {
            return new ScheduleResponse(this.scheduleId, this.batchId, this.batchName, this.division, this.subjectId, this.subjectName, this.subjectCode, this.staffId, this.staffName, this.labId, this.labName, this.dayId, this.dayName, this.dayOrder, this.slotId, this.slotLabel, this.startTime, this.endTime, this.generatedByGA);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ScheduleResponse.ScheduleResponseBuilder(scheduleId=" + this.scheduleId + ", batchId=" + this.batchId + ", batchName=" + this.batchName + ", division=" + this.division + ", subjectId=" + this.subjectId + ", subjectName=" + this.subjectName + ", subjectCode=" + this.subjectCode + ", staffId=" + this.staffId + ", staffName=" + this.staffName + ", labId=" + this.labId + ", labName=" + this.labName + ", dayId=" + this.dayId + ", dayName=" + this.dayName + ", dayOrder=" + this.dayOrder + ", slotId=" + this.slotId + ", slotLabel=" + this.slotLabel + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", generatedByGA=" + this.generatedByGA + ")";
        }
    }

    public static ScheduleResponse.ScheduleResponseBuilder builder() {
        return new ScheduleResponse.ScheduleResponseBuilder();
    }

    public Long getScheduleId() {
        return this.scheduleId;
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public String getBatchName() {
        return this.batchName;
    }

    public String getDivision() {
        return this.division;
    }

    public Long getSubjectId() {
        return this.subjectId;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public Long getLabId() {
        return this.labId;
    }

    public String getLabName() {
        return this.labName;
    }

    public Long getDayId() {
        return this.dayId;
    }

    public String getDayName() {
        return this.dayName;
    }

    public Integer getDayOrder() {
        return this.dayOrder;
    }

    public Long getSlotId() {
        return this.slotId;
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

    public boolean isGeneratedByGA() {
        return this.generatedByGA;
    }

    public void setScheduleId(final Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setBatchId(final Long batchId) {
        this.batchId = batchId;
    }

    public void setBatchName(final String batchName) {
        this.batchName = batchName;
    }

    public void setDivision(final String division) {
        this.division = division;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(final String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectCode(final String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(final String staffName) {
        this.staffName = staffName;
    }

    public void setLabId(final Long labId) {
        this.labId = labId;
    }

    public void setLabName(final String labName) {
        this.labName = labName;
    }

    public void setDayId(final Long dayId) {
        this.dayId = dayId;
    }

    public void setDayName(final String dayName) {
        this.dayName = dayName;
    }

    public void setDayOrder(final Integer dayOrder) {
        this.dayOrder = dayOrder;
    }

    public void setSlotId(final Long slotId) {
        this.slotId = slotId;
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

    public void setGeneratedByGA(final boolean generatedByGA) {
        this.generatedByGA = generatedByGA;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ScheduleResponse)) return false;
        final ScheduleResponse other = (ScheduleResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isGeneratedByGA() != other.isGeneratedByGA()) return false;
        final java.lang.Object this$scheduleId = this.getScheduleId();
        final java.lang.Object other$scheduleId = other.getScheduleId();
        if (this$scheduleId == null ? other$scheduleId != null : !this$scheduleId.equals(other$scheduleId)) return false;
        final java.lang.Object this$batchId = this.getBatchId();
        final java.lang.Object other$batchId = other.getBatchId();
        if (this$batchId == null ? other$batchId != null : !this$batchId.equals(other$batchId)) return false;
        final java.lang.Object this$subjectId = this.getSubjectId();
        final java.lang.Object other$subjectId = other.getSubjectId();
        if (this$subjectId == null ? other$subjectId != null : !this$subjectId.equals(other$subjectId)) return false;
        final java.lang.Object this$staffId = this.getStaffId();
        final java.lang.Object other$staffId = other.getStaffId();
        if (this$staffId == null ? other$staffId != null : !this$staffId.equals(other$staffId)) return false;
        final java.lang.Object this$labId = this.getLabId();
        final java.lang.Object other$labId = other.getLabId();
        if (this$labId == null ? other$labId != null : !this$labId.equals(other$labId)) return false;
        final java.lang.Object this$dayId = this.getDayId();
        final java.lang.Object other$dayId = other.getDayId();
        if (this$dayId == null ? other$dayId != null : !this$dayId.equals(other$dayId)) return false;
        final java.lang.Object this$dayOrder = this.getDayOrder();
        final java.lang.Object other$dayOrder = other.getDayOrder();
        if (this$dayOrder == null ? other$dayOrder != null : !this$dayOrder.equals(other$dayOrder)) return false;
        final java.lang.Object this$slotId = this.getSlotId();
        final java.lang.Object other$slotId = other.getSlotId();
        if (this$slotId == null ? other$slotId != null : !this$slotId.equals(other$slotId)) return false;
        final java.lang.Object this$batchName = this.getBatchName();
        final java.lang.Object other$batchName = other.getBatchName();
        if (this$batchName == null ? other$batchName != null : !this$batchName.equals(other$batchName)) return false;
        final java.lang.Object this$division = this.getDivision();
        final java.lang.Object other$division = other.getDivision();
        if (this$division == null ? other$division != null : !this$division.equals(other$division)) return false;
        final java.lang.Object this$subjectName = this.getSubjectName();
        final java.lang.Object other$subjectName = other.getSubjectName();
        if (this$subjectName == null ? other$subjectName != null : !this$subjectName.equals(other$subjectName)) return false;
        final java.lang.Object this$subjectCode = this.getSubjectCode();
        final java.lang.Object other$subjectCode = other.getSubjectCode();
        if (this$subjectCode == null ? other$subjectCode != null : !this$subjectCode.equals(other$subjectCode)) return false;
        final java.lang.Object this$staffName = this.getStaffName();
        final java.lang.Object other$staffName = other.getStaffName();
        if (this$staffName == null ? other$staffName != null : !this$staffName.equals(other$staffName)) return false;
        final java.lang.Object this$labName = this.getLabName();
        final java.lang.Object other$labName = other.getLabName();
        if (this$labName == null ? other$labName != null : !this$labName.equals(other$labName)) return false;
        final java.lang.Object this$dayName = this.getDayName();
        final java.lang.Object other$dayName = other.getDayName();
        if (this$dayName == null ? other$dayName != null : !this$dayName.equals(other$dayName)) return false;
        final java.lang.Object this$slotLabel = this.getSlotLabel();
        final java.lang.Object other$slotLabel = other.getSlotLabel();
        if (this$slotLabel == null ? other$slotLabel != null : !this$slotLabel.equals(other$slotLabel)) return false;
        final java.lang.Object this$startTime = this.getStartTime();
        final java.lang.Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final java.lang.Object this$endTime = this.getEndTime();
        final java.lang.Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ScheduleResponse;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isGeneratedByGA() ? 79 : 97);
        final java.lang.Object $scheduleId = this.getScheduleId();
        result = result * PRIME + ($scheduleId == null ? 43 : $scheduleId.hashCode());
        final java.lang.Object $batchId = this.getBatchId();
        result = result * PRIME + ($batchId == null ? 43 : $batchId.hashCode());
        final java.lang.Object $subjectId = this.getSubjectId();
        result = result * PRIME + ($subjectId == null ? 43 : $subjectId.hashCode());
        final java.lang.Object $staffId = this.getStaffId();
        result = result * PRIME + ($staffId == null ? 43 : $staffId.hashCode());
        final java.lang.Object $labId = this.getLabId();
        result = result * PRIME + ($labId == null ? 43 : $labId.hashCode());
        final java.lang.Object $dayId = this.getDayId();
        result = result * PRIME + ($dayId == null ? 43 : $dayId.hashCode());
        final java.lang.Object $dayOrder = this.getDayOrder();
        result = result * PRIME + ($dayOrder == null ? 43 : $dayOrder.hashCode());
        final java.lang.Object $slotId = this.getSlotId();
        result = result * PRIME + ($slotId == null ? 43 : $slotId.hashCode());
        final java.lang.Object $batchName = this.getBatchName();
        result = result * PRIME + ($batchName == null ? 43 : $batchName.hashCode());
        final java.lang.Object $division = this.getDivision();
        result = result * PRIME + ($division == null ? 43 : $division.hashCode());
        final java.lang.Object $subjectName = this.getSubjectName();
        result = result * PRIME + ($subjectName == null ? 43 : $subjectName.hashCode());
        final java.lang.Object $subjectCode = this.getSubjectCode();
        result = result * PRIME + ($subjectCode == null ? 43 : $subjectCode.hashCode());
        final java.lang.Object $staffName = this.getStaffName();
        result = result * PRIME + ($staffName == null ? 43 : $staffName.hashCode());
        final java.lang.Object $labName = this.getLabName();
        result = result * PRIME + ($labName == null ? 43 : $labName.hashCode());
        final java.lang.Object $dayName = this.getDayName();
        result = result * PRIME + ($dayName == null ? 43 : $dayName.hashCode());
        final java.lang.Object $slotLabel = this.getSlotLabel();
        result = result * PRIME + ($slotLabel == null ? 43 : $slotLabel.hashCode());
        final java.lang.Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final java.lang.Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ScheduleResponse(scheduleId=" + this.getScheduleId() + ", batchId=" + this.getBatchId() + ", batchName=" + this.getBatchName() + ", division=" + this.getDivision() + ", subjectId=" + this.getSubjectId() + ", subjectName=" + this.getSubjectName() + ", subjectCode=" + this.getSubjectCode() + ", staffId=" + this.getStaffId() + ", staffName=" + this.getStaffName() + ", labId=" + this.getLabId() + ", labName=" + this.getLabName() + ", dayId=" + this.getDayId() + ", dayName=" + this.getDayName() + ", dayOrder=" + this.getDayOrder() + ", slotId=" + this.getSlotId() + ", slotLabel=" + this.getSlotLabel() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", generatedByGA=" + this.isGeneratedByGA() + ")";
    }

    public ScheduleResponse(final Long scheduleId, final Long batchId, final String batchName, final String division, final Long subjectId, final String subjectName, final String subjectCode, final Long staffId, final String staffName, final Long labId, final String labName, final Long dayId, final String dayName, final Integer dayOrder, final Long slotId, final String slotLabel, final LocalTime startTime, final LocalTime endTime, final boolean generatedByGA) {
        this.scheduleId = scheduleId;
        this.batchId = batchId;
        this.batchName = batchName;
        this.division = division;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.staffId = staffId;
        this.staffName = staffName;
        this.labId = labId;
        this.labName = labName;
        this.dayId = dayId;
        this.dayName = dayName;
        this.dayOrder = dayOrder;
        this.slotId = slotId;
        this.slotLabel = slotLabel;
        this.startTime = startTime;
        this.endTime = endTime;
        this.generatedByGA = generatedByGA;
    }

    public ScheduleResponse() {
    }
}
