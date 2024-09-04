package com.example.Event.Management.Service;

import com.example.Event.Management.Entity.Vendor;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing vendors.
 */
public interface VendorService {

    /**
     * Saves a new or existing vendor.
     * @param vendor the vendor to be saved.
     * @return the saved vendor.
     */
    Vendor saveVendor(Vendor vendor);

    /**
     * Retrieves all vendors.
     * @return a list of all vendors.
     */
    List<Vendor> getAllVendors();

    /**
     * Retrieves a vendor by ID.
     * @param id the ID of the vendor to retrieve.
     * @return an Optional containing the vendor with the specified ID, or an empty Optional if not found.
     */
    Optional<Vendor> getVendorById(Long id);

    /**
     * Deletes a vendor by ID.
     * @param id the ID of the vendor to delete.
     */
    void deleteVendor(Long id);
}
