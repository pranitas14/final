package com.example.Event.Management.EventServiceImpl;

import com.example.Event.Management.Entity.Vendor;
import com.example.Event.Management.EventRepository.VendorRepository;
import com.example.Event.Management.Service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the VendorService interface.
 * Provides business logic for managing vendors, including CRUD operations.
 */
@Service
public class VendorServiceImpl implements VendorService {

    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**
     * Saves a new vendor to the repository.
     * @param vendor the vendor to be saved.
     * @return the saved vendor.
     */
    @Override
    public Vendor saveVendor(Vendor vendor) {
        if (vendor == null) {
            logger.warn("Attempted to save a null vendor");
            throw new IllegalArgumentException("Vendor must not be null");
        }
        Vendor savedVendor = vendorRepository.save(vendor);
        logger.info("Saved vendor with ID: {}", savedVendor.getId());
        return savedVendor;
    }

    /**
     * Retrieves all vendors from the repository.
     * @return a list of all vendors.
     */
    @Override
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        logger.info("Retrieved all vendors");
        return vendors;
    }

    /**
     * Retrieves a vendor by its ID.
     * @param id the ID of the vendor.
     * @return an Optional containing the vendor if found, or an empty Optional if not found.
     */
    @Override
    public Optional<Vendor> getVendorById(Long id) {
        if (id == null) {
            logger.warn("Attempted to retrieve vendor with a null ID");
            throw new IllegalArgumentException("ID must not be null");
        }
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isEmpty()) {
            logger.warn("Vendor with ID {} not found", id);
        }
        return vendor;
    }

    /**
     * Deletes a vendor by its ID.
     * @param id the ID of the vendor to be deleted.
     */
    @Override
    public void deleteVendor(Long id) {
        if (id == null) {
            logger.warn("Attempted to delete vendor with a null ID");
            throw new IllegalArgumentException("ID must not be null");
        }
        if (vendorRepository.existsById(id)) {
            vendorRepository.deleteById(id);
            logger.info("Deleted vendor with ID: {}", id);
        } else {
            logger.warn("Vendor with ID {} not found for deletion", id);
        }
    }
}
