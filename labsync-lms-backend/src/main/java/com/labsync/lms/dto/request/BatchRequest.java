package com.labsync.lms.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.util.List;

/**
 * BatchRequest DTO — payload for creating/updating a batch.
 */
public class BatchRequest {
    @NotBlank
    @Size(max = 50)
    private String batchName;
    @NotBlank
    @Size(max = 10)
    private String division;
    @NotNull
    @Min(1)
    private Integer studentCount;
    @Size(max = 10)
    private String semester;
    @Size(max = 50)
    private String osRequirement;
    @Min(1)
    private Integer labsPerWeek = 1;
    @Min(1)
    private Integer totalHours;
    private LocalTime startTime;
    private LocalTime endTime;
    @NotNull
    private Long programId;
    @NotNull
    private Long academicYearId;
    
    private List<Long> subjectIds;

    public BatchRequest() {
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

    public Integer getTotalHours() { return this.totalHours; }
    public LocalTime getStartTime() { return this.startTime; }
    public LocalTime getEndTime() { return this.endTime; }
    public void setTotalHours(final Integer totalHours) { this.totalHours = totalHours; }
    public void setStartTime(final LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(final LocalTime endTime) { this.endTime = endTime; }

    public Long getProgramId() {
        return this.programId;
    }

    public Long getAcademicYearId() {
        return this.academicYearId;
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

    public void setProgramId(final Long programId) {
        this.programId = programId;
    }

    public void setAcademicYearId(final Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public List<Long> getSubjectIds() {
        return this.subjectIds;
    }

    public void setSubjectIds(final List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof BatchRequest)) return false;
        final BatchRequest other = (BatchRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$studentCount = this.getStudentCount();
        final java.lang.Object other$studentCount = other.getStudentCount();
        if (this$studentCount == null ? other$studentCount != null : !this$studentCount.equals(other$studentCount)) return false;
        final java.lang.Object this$programId = this.getProgramId();
        final java.lang.Object other$programId = other.getProgramId();
        if (this$programId == null ? other$programId != null : !this$programId.equals(other$programId)) return false;
        final java.lang.Object this$academicYearId = this.getAcademicYearId();
        final java.lang.Object other$academicYearId = other.getAcademicYearId();
        if (this$academicYearId == null ? other$academicYearId != null : !this$academicYearId.equals(other$academicYearId)) return false;
        final java.lang.Object this$batchName = this.getBatchName();
        final java.lang.Object other$batchName = other.getBatchName();
        if (this$batchName == null ? other$batchName != null : !this$batchName.equals(other$batchName)) return false;
        final java.lang.Object this$division = this.getDivision();
        final java.lang.Object other$division = other.getDivision();
        if (this$division == null ? other$division != null : !this$division.equals(other$division)) return false;
        final java.lang.Object this$semester = this.getSemester();
        final java.lang.Object other$semester = other.getSemester();
        if (this$semester == null ? other$semester != null : !this$semester.equals(other$semester)) return false;
        final java.lang.Object this$osRequirement = this.getOsRequirement();
        final java.lang.Object other$osRequirement = other.getOsRequirement();
        if (this$osRequirement == null ? other$osRequirement != null : !this$osRequirement.equals(other$osRequirement)) return false;
        final java.lang.Object this$labsPerWeek = this.getLabsPerWeek();
        final java.lang.Object other$labsPerWeek = other.getLabsPerWeek();
        if (this$labsPerWeek == null ? other$labsPerWeek != null : !this$labsPerWeek.equals(other$labsPerWeek)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof BatchRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $studentCount = this.getStudentCount();
        result = result * PRIME + ($studentCount == null ? 43 : $studentCount.hashCode());
        final java.lang.Object $programId = this.getProgramId();
        result = result * PRIME + ($programId == null ? 43 : $programId.hashCode());
        final java.lang.Object $academicYearId = this.getAcademicYearId();
        result = result * PRIME + ($academicYearId == null ? 43 : $academicYearId.hashCode());
        final java.lang.Object $batchName = this.getBatchName();
        result = result * PRIME + ($batchName == null ? 43 : $batchName.hashCode());
        final java.lang.Object $division = this.getDivision();
        result = result * PRIME + ($division == null ? 43 : $division.hashCode());
        final java.lang.Object $semester = this.getSemester();
        result = result * PRIME + ($semester == null ? 43 : $semester.hashCode());
        final java.lang.Object $osRequirement = this.getOsRequirement();
        result = result * PRIME + ($osRequirement == null ? 43 : $osRequirement.hashCode());
        final java.lang.Object $labsPerWeek = this.getLabsPerWeek();
        result = result * PRIME + ($labsPerWeek == null ? 43 : $labsPerWeek.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "BatchRequest(batchName=" + this.getBatchName() + ", division=" + this.getDivision() + ", studentCount=" + this.getStudentCount() + ", semester=" + this.getSemester() + ", osRequirement=" + this.getOsRequirement() + ", labsPerWeek=" + this.getLabsPerWeek() + ", totalHours=" + this.getTotalHours() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", programId=" + this.getProgramId() + ", academicYearId=" + this.getAcademicYearId() + ")";
    }
}
