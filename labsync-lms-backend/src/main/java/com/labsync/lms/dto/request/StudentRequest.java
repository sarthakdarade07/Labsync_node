package com.labsync.lms.dto.request;

import jakarta.validation.constraints.*;

/**
 * StudentRequest DTO — payload for creating/updating a student.
 */
public class StudentRequest {
    @NotBlank
    @Size(max = 100)
    private String fullName;
    @NotBlank
    @Size(max = 20)
    private String prn;
    @Email
    @Size(max = 100)
    private String email;
    @Size(max = 15)
    private String phone;
    @NotNull
    private Long batchId;
    @NotNull
    private Long programId;
    @NotNull
    private Long academicYearId;

    public StudentRequest() {
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

    public Long getBatchId() {
        return this.batchId;
    }

    public Long getProgramId() {
        return this.programId;
    }

    public Long getAcademicYearId() {
        return this.academicYearId;
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

    public void setBatchId(final Long batchId) {
        this.batchId = batchId;
    }

    public void setProgramId(final Long programId) {
        this.programId = programId;
    }

    public void setAcademicYearId(final Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof StudentRequest)) return false;
        final StudentRequest other = (StudentRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$batchId = this.getBatchId();
        final java.lang.Object other$batchId = other.getBatchId();
        if (this$batchId == null ? other$batchId != null : !this$batchId.equals(other$batchId)) return false;
        final java.lang.Object this$programId = this.getProgramId();
        final java.lang.Object other$programId = other.getProgramId();
        if (this$programId == null ? other$programId != null : !this$programId.equals(other$programId)) return false;
        final java.lang.Object this$academicYearId = this.getAcademicYearId();
        final java.lang.Object other$academicYearId = other.getAcademicYearId();
        if (this$academicYearId == null ? other$academicYearId != null : !this$academicYearId.equals(other$academicYearId)) return false;
        final java.lang.Object this$fullName = this.getFullName();
        final java.lang.Object other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final java.lang.Object this$prn = this.getPrn();
        final java.lang.Object other$prn = other.getPrn();
        if (this$prn == null ? other$prn != null : !this$prn.equals(other$prn)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$phone = this.getPhone();
        final java.lang.Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof StudentRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $batchId = this.getBatchId();
        result = result * PRIME + ($batchId == null ? 43 : $batchId.hashCode());
        final java.lang.Object $programId = this.getProgramId();
        result = result * PRIME + ($programId == null ? 43 : $programId.hashCode());
        final java.lang.Object $academicYearId = this.getAcademicYearId();
        result = result * PRIME + ($academicYearId == null ? 43 : $academicYearId.hashCode());
        final java.lang.Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final java.lang.Object $prn = this.getPrn();
        result = result * PRIME + ($prn == null ? 43 : $prn.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "StudentRequest(fullName=" + this.getFullName() + ", prn=" + this.getPrn() + ", email=" + this.getEmail() + ", phone=" + this.getPhone() + ", batchId=" + this.getBatchId() + ", programId=" + this.getProgramId() + ", academicYearId=" + this.getAcademicYearId() + ")";
    }
}
