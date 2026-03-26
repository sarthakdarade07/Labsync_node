package com.labsync.lms.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Staff entity — faculty/lab instructors who conduct lab sessions.
 * Linked to User account for login.
 */
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String fullName;
    @Column(unique = true, length = 50)
    private String employeeId;
    @Column(length = 100)
    private String email;
    @Column(length = 15)
    private String phone;
    @Column(length = 100)
    private String department;
    @Column(length = 50)
    private String designation; // e.g., "Lab Instructor", "Assistant Professor"
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;


    public static class StaffBuilder {
        private Long id;
        private String fullName;
        private String employeeId;
        private String email;
        private String phone;
        private String department;
        private String designation;
        private User user;
        private List<Schedule> schedules;

        StaffBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder employeeId(final String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder department(final String department) {
            this.department = department;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder designation(final String designation) {
            this.designation = designation;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder user(final User user) {
            this.user = user;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public Staff.StaffBuilder schedules(final List<Schedule> schedules) {
            this.schedules = schedules;
            return this;
        }

        public Staff build() {
            return new Staff(this.id, this.fullName, this.employeeId, this.email, this.phone, this.department, this.designation, this.user, this.schedules);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Staff.StaffBuilder(id=" + this.id + ", fullName=" + this.fullName + ", employeeId=" + this.employeeId + ", email=" + this.email + ", phone=" + this.phone + ", department=" + this.department + ", designation=" + this.designation + ", user=" + this.user + ", schedules=" + this.schedules + ")";
        }
    }

    public static Staff.StaffBuilder builder() {
        return new Staff.StaffBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getDesignation() {
        return this.designation;
    }

    public User getUser() {
        return this.user;
    }

    public List<Schedule> getSchedules() {
        return this.schedules;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setEmployeeId(final String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public void setDesignation(final String designation) {
        this.designation = designation;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setSchedules(final List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Staff() {
    }

    public Staff(final Long id, final String fullName, final String employeeId, final String email, final String phone, final String department, final String designation, final User user, final List<Schedule> schedules) {
        this.id = id;
        this.fullName = fullName;
        this.employeeId = employeeId;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.user = user;
        this.schedules = schedules;
    }
}
