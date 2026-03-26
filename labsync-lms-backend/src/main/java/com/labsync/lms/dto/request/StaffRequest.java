package com.labsync.lms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StaffRequest {
    @NotBlank
    @Size(max = 100)
    private String fullName;
    @NotBlank
    @Size(max = 50)
    private String employeeId;
    @Size(max = 100)
    private String email;
    @Size(max = 15)
    private String phone;
    @Size(max = 100)
    private String department;
    @Size(max = 50)
    private String designation;

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}
