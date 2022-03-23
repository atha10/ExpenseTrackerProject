package com.example.expensetrackerproject;

public class Users {
    private String name,email,password;
    private double phoneno;

    public Users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(double phoneno) {
        this.phoneno = phoneno;
    }
}
