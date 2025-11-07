package com.livingletters.bookapi.dto;

/*
  DTO for authentication request (login).
  ES: DTO para la petición de autenticación (login).
*/
public class AuthRequestDTO {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
