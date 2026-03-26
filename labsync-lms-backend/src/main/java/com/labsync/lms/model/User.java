package com.labsync.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity — stores system users (admins, staff).
 * Linked to Role for Spring Security authorization.
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank
    @Size(max = 100)
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Size(max = 120)
    @Column(nullable = false)
    private String password;
    @Size(max = 100)
    private String fullName;
    @Column(nullable = false)
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private static boolean $default$enabled() {
        return true;
    }

    private static Set<Role> $default$roles() {
        return new HashSet<>();
    }


    public static class UserBuilder {
        private Long id;
        private String username;
        private String email;
        private String password;
        private String fullName;
        private boolean enabled$set;
        private boolean enabled$value;
        private boolean roles$set;
        private Set<Role> roles$value;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        UserBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder enabled(final boolean enabled) {
            this.enabled$value = enabled;
            enabled$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder roles(final Set<Role> roles) {
            this.roles$value = roles;
            roles$set = true;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public User.UserBuilder updatedAt(final LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            boolean enabled$value = this.enabled$value;
            if (!this.enabled$set) enabled$value = User.$default$enabled();
            Set<Role> roles$value = this.roles$value;
            if (!this.roles$set) roles$value = User.$default$roles();
            return new User(this.id, this.username, this.email, this.password, this.fullName, enabled$value, roles$value, this.createdAt, this.updatedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "User.UserBuilder(id=" + this.id + ", username=" + this.username + ", email=" + this.email + ", password=" + this.password + ", fullName=" + this.fullName + ", enabled$value=" + this.enabled$value + ", roles$value=" + this.roles$value + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User() {
        this.enabled = User.$default$enabled();
        this.roles = User.$default$roles();
    }

    public User(final Long id, final String username, final String email, final String password, final String fullName, final boolean enabled, final Set<Role> roles, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.enabled = enabled;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
