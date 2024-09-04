package com.example.Event.Management.EventServiceImpl;

import com.example.Event.Management.Entity.Event;
import com.example.Event.Management.Entity.RegistrationRequest;
import com.example.Event.Management.Entity.User;
import com.example.Event.Management.EventRepository.AllEventRepository;
import com.example.Event.Management.EventRepository.UserRepository;
import com.example.Event.Management.Service.AllEventService;
import com.example.Event.Management.Service.PdfService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the AllEventService interface.
 * Provides business logic for managing events, including CRUD operations and PDF generation.
 */
@Service

public class AllEventServiceImpl implements AllEventService {

    private static final Logger logger = LoggerFactory.getLogger(AllEventServiceImpl.class);

    @Autowired
    private AllEventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PdfService pdfService;

    @Override
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);
        logger.info("Retrieved all events");
        return events;
    }

    @Override
    public Event getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.orElseGet(() -> {
            logger.warn("Event with id {} not found", id);
            return null;
        });
    }

    @Override
    public Event createEvent(Event event) {
        Event createdEvent = eventRepository.save(event);
        logger.info("Created new event with id {}", createdEvent.getId());
        return createdEvent;
    }

    @Override
    public Event updateEvent(Long id, Event eventDetails) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            event.setEventTitle(eventDetails.getEventTitle());
            event.setEventDetails(eventDetails.getEventDetails());
            event.setDate(eventDetails.getDate());
            event.setLocation(eventDetails.getLocation());
            event.setTime(eventDetails.getTime());
            Event updatedEvent = eventRepository.save(event);
            logger.info("Updated event with id {}", updatedEvent.getId());
            return updatedEvent;
        } else {
            logger.warn("Event with id {} not found for update", id);
            return null;
        }
    }

    @Override
    public boolean deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            logger.info("Deleted event with id {}", id);
            return true;
        } else {
            logger.warn("Event with id {} not found for deletion", id);
            return false;
        }
    }

    @Override
    public void registerEvent(Long eventId, RegistrationRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> {
                    logger.warn("Event with id {} not found", eventId);
                    return new RuntimeException("Event not found");
                });

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> {
                    logger.warn("User with id {} not found", request.getUserId());
                    return new RuntimeException("User not found");
                });

        Set<User> registeredUsers = event.getRegisteredUsers();
        if (registeredUsers == null) {
            registeredUsers = new HashSet<>();
            event.setRegisteredUsers(registeredUsers);
        }
        registeredUsers.add(user);

        eventRepository.save(event);

        logger.info("User with id {} registered for event with id {}", request.getUserId(), eventId);
    }

    @Override
    public byte[] generateEventPdf(Long id) {
        Optional<Event> eventOpt = eventRepository.findById(id);
        if (eventOpt.isPresent()) {
            Event event = eventOpt.get();
            try {
                byte[] pdfBytes = pdfService.createEventPdf(event);
                logger.info("PDF generated for event with id {}", id);
                return pdfBytes;
            } catch (Exception e) {
                logger.error("Error generating PDF for event with id {}", id, e);
                return null;
            }
        } else {
            logger.warn("Event with id {} not found for PDF generation", id);
            return null;
        }
    }
}
