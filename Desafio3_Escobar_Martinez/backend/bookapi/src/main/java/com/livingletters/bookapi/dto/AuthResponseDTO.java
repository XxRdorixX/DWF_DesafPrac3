package com.livingletters.bookapi.dto;

import java.util.List;

/**
 * DTO for returning JWT authentication responses.
 */
public class AuthResponseDTO {
    private String token;
    private long expiresAt;
    private List<String> roles;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, long expiresAt, List<String> roles) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
