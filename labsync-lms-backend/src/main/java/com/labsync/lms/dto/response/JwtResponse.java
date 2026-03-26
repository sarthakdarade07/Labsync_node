package com.labsync.lms.dto.response;

import java.util.List;

/**
 * JwtResponse DTO — returned after successful login.
 * Contains the JWT token, user info, and assigned roles.
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private List<String> roles;


    public static class JwtResponseBuilder {
        private String token;
        private String type;
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private List<String> roles;

        JwtResponseBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder token(final String token) {
            this.token = token;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder type(final String type) {
            this.type = type;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder fullName(final String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public JwtResponse.JwtResponseBuilder roles(final List<String> roles) {
            this.roles = roles;
            return this;
        }

        public JwtResponse build() {
            return new JwtResponse(this.token, this.type, this.id, this.username, this.email, this.fullName, this.roles);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "JwtResponse.JwtResponseBuilder(token=" + this.token + ", type=" + this.type + ", id=" + this.id + ", username=" + this.username + ", email=" + this.email + ", fullName=" + this.fullName + ", roles=" + this.roles + ")";
        }
    }

    public static JwtResponse.JwtResponseBuilder builder() {
        return new JwtResponse.JwtResponseBuilder();
    }

    public String getToken() {
        return this.token;
    }

    public String getType() {
        return this.type;
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

    public String getFullName() {
        return this.fullName;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setType(final String type) {
        this.type = type;
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

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof JwtResponse)) return false;
        final JwtResponse other = (JwtResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        final java.lang.Object this$type = this.getType();
        final java.lang.Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final java.lang.Object this$username = this.getUsername();
        final java.lang.Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$fullName = this.getFullName();
        final java.lang.Object other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) return false;
        final java.lang.Object this$roles = this.getRoles();
        final java.lang.Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof JwtResponse;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        final java.lang.Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final java.lang.Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final java.lang.Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "JwtResponse(token=" + this.getToken() + ", type=" + this.getType() + ", id=" + this.getId() + ", username=" + this.getUsername() + ", email=" + this.getEmail() + ", fullName=" + this.getFullName() + ", roles=" + this.getRoles() + ")";
    }

    public JwtResponse(final String token, final String type, final Long id, final String username, final String email, final String fullName, final List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
    }
}
