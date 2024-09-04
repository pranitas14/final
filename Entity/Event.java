package com.example.Event.Management.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * Entity representing an event in the Event Management system.
 */
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Event title is mandatory")
    @Size(max = 100, message = "Event title must not exceed 100 characters")
    private String eventTitle;

    @NotBlank(message = "Event details are mandatory")
    @Size(max = 500, message = "Event details must not exceed 500 characters")
    private String eventDetails;

    @NotNull(message = "Event date is mandatory")
    private String date;

    @NotBlank(message = "Event location is mandatory")
    @Size(max = 200, message = "Event location must not exceed 200 characters")
    private String location;

    @NotNull(message = "Event time is mandatory")
    private String time;

    @ManyToMany
    @JoinTable(
            name = "event_user",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> registeredUsers;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Set<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(Set<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }
}
