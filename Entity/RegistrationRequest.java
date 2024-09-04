package com.example.Event.Management.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a registration request for an event.
 * Contains information about the user registering for the event.
 */
public class RegistrationRequest {

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Name is mandatory")
    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
