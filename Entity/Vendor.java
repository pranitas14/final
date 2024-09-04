package com.example.Event.Management.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a vendor in the Event Management system.
 */
@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the vendor.
     * Must not be blank.
     */
    @NotBlank(message = "Vendor name is mandatory")
    @Column(nullable = false)
    private String name;

    /**
     * Details of the vendor.
     * Must not be blank.
     */
    @NotBlank(message = "Vendor details are mandatory")
    @Column(nullable = false)
    private String details;

    /**
     * The user associated with the vendor.
     * Represented as a many-to-one relationship.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
