package com.example.Event.Management.Controller;

import com.example.Event.Management.Entity.Event;
import com.example.Event.Management.Entity.RegistrationRequest;
import com.example.Event.Management.Service.AllEventService;
import com.example.Event.Management.Service.PdfService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/all-events")
public class AllEventController {

    private static final Logger logger = LoggerFactory.getLogger(AllEventController.class);

    @Autowired
    private AllEventService eventService;

    @Autowired
    private PdfService pdfService;
    @Autowired

	private AllEventService AllEventService;

    // Constructor injection for AllEventService
    @Autowired
    public AllEventController(@Autowired AllEventService allEventService) {
        this.AllEventService = allEventService;
    }

    // Creates a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        logger.info("Creating new event with details: {}", event);
        try {
            Event createdEvent = eventService.createEvent(event);
            logger.info("Event created successfully with ID: {}", createdEvent.getId());
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating event: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Retrieves all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        logger.info("Fetching all events.");
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Retrieves an event by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        logger.info("Fetching event with ID: {}", id);
        Event event = eventService.getEventById(id);
        if (event != null) {
            logger.info("Retrieved event with ID: {}", id);
            return ResponseEntity.ok(event);
        } else {
            logger.warn("Event with ID: {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Updates an existing event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody Event eventDetails) {
        logger.info("Updating event with ID: {}", id);
        try {
            Event existingEvent = eventService.getEventById(id);
            if (existingEvent != null) {
                existingEvent.setTime(eventDetails.getTime());
                existingEvent.setDate(eventDetails.getDate());
                existingEvent.setLocation(eventDetails.getLocation());
                Event updatedEvent = eventService.createEvent(existingEvent); // Save method corrected
                logger.info("Event with ID: {} updated successfully.", id);
                return ResponseEntity.ok(updatedEvent);
            } else {
                logger.warn("Event with ID: {} not found for update.", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error updating event with ID: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Deletes an event by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        logger.info("Deleting event with ID: {}", id);
        try {
            boolean isDeleted = eventService.deleteEvent(id);
            if (isDeleted) {
                logger.info("Event with ID: {} deleted successfully.", id);
                return ResponseEntity.noContent().build();
            } else {
                logger.warn("Event with ID {} not found for deletion", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error deleting event with ID: {}. Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Generates PDF for the event by its ID
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generateEventPdf(@PathVariable Long id) {
        logger.info("Request to generate PDF for Event ID: {}", id);

        Event event = eventService.getEventById(id);
        if (event == null) {
            logger.warn("Event with ID {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] pdfBytes = pdfService.createEventPdf(event);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=event_" + id + ".pdf");
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

        logger.info("PDF successfully generated for Event ID: {}", id);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // Registers a user for an event
    @PostMapping("/{eventId}/register")
    public ResponseEntity<Void> registerEvent(@PathVariable Long eventId, @Valid @RequestBody RegistrationRequest request) {
        try {
            eventService.registerEvent(eventId, request);
            logger.info("User with ID {} registered for event with ID {}", request.getUserId(), eventId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error("Error registering user with ID {} for event with ID {}", request.getUserId(), eventId, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
