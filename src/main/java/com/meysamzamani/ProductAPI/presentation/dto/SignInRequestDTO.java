package com.meysamzamani.ProductAPI.presentation.dto;

public class SignInRequestDTO {

    private String email;
    private String password;

    public SignInRequestDTO() {
    }

    public SignInRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
