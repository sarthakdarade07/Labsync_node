package com.labsync.lms.model;

import jakarta.persistence.*;

/**
 * Role entity — defines access levels: ROLE_ADMIN, ROLE_STAFF.
 * Used in Spring Security for authorization.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private ERole name;


    public enum ERole {
        ROLE_ADMIN, ROLE_STAFF;
    }

    public Long getId() {
        return this.id;
    }

    public ERole getName() {
        return this.name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final ERole name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(final Long id, final ERole name) {
        this.id = id;
        this.name = name;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Role(id=" + this.getId() + ", name=" + this.getName() + ")";
    }
}
