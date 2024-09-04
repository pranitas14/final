package com.example.Event.Management.Controller;

import com.example.Event.Management.Entity.Vendor;
import com.example.Event.Management.Service.VendorService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing vendors in the Event Management system.
 */
@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    /**
     * Create a new vendor.
     *
     * @param vendor the vendor to be created
     * @return the created vendor
     */
    @PostMapping
    public ResponseEntity<Vendor> saveVendor(@Valid @RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.saveVendor(vendor);
        logger.info("Created new vendor with ID: {}", savedVendor.getId());
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    /**
     * Get all vendors.
     *
     * @return a list of all vendors
     */
    @GetMapping
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        if (vendors.isEmpty()) {
            logger.warn("No vendors found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.info("Retrieved all vendors");
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    /**
     * Get a vendor by ID.
     *
     * @param id the ID of the vendor
     * @return the vendor with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) {
        Optional<Vendor> vendor = vendorService.getVendorById(id);
        if (vendor.isPresent()) {
            logger.info("Retrieved vendor with ID: {}", id);
            return new ResponseEntity<>(vendor.get(), HttpStatus.OK);
        }
        logger.warn("Vendor with ID {} not found", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update an existing vendor.
     *
     * @param id     the ID of the vendor to be updated
     * @param vendor the updated vendor details
     * @return the updated vendor
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable("id") Long id, @Valid @RequestBody Vendor vendor) {
        Optional<Vendor> existingVendor = vendorService.getVendorById(id);
        if (existingVendor.isPresent()) {
            vendor.setId(id); // Ensure the ID is set for updating
            Vendor updatedVendor = vendorService.saveVendor(vendor);
            logger.info("Updated vendor with ID: {}", id);
            return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
        }
        logger.warn("Vendor with ID {} not found for update", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a vendor by ID.
     *
     * @param id the ID of the vendor to be deleted
     * @return a response indicating the result of the deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable("id") Long id) {
        if (vendorService.getVendorById(id).isPresent()) {
            vendorService.deleteVendor(id);
            logger.info("Deleted vendor with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.warn("Vendor with ID {} not found for deletion", id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
