package com.example.Event.Management.Controller;




import com.example.Event.Management.Entity.User;

import com.example.Event.Management.Service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing users in the Event Management system.
 */
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        logger.info("Created new user with ID: {}", createdUser.getId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Get all users.
     *
     * @return a list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        logger.info("Retrieved all users");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get a user by ID.
     *
     * @param id the ID of the user
     * @return the user with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Retrieved user with ID: {}", id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Update a user by ID.
     *
     * @param id the ID of the user
     * @param userDetails the updated user details
     * @return the updated user, or HTTP status NOT FOUND if the user does not exist
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser == null) {
            logger.warn("User with ID {} not found for update", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Updated user with ID: {}", id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user
     * @return a response indicating the result of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            logger.warn("User with ID {} not found for deletion", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Deleted user with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
