package com.book.store.dto;


import javax.validation.constraints.NotBlank;


public class LoginRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
