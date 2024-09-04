package com.example.Event.Management.EventRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Event.Management.Entity.Vendor;

/**
 * Repository interface for accessing and managing Vendor entities in the database.
 * Extends JpaRepository to provide CRUD operations and more advanced querying capabilities.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    // JpaRepository provides basic CRUD operations and pagination/sorting features.
    // Methods inherited from JpaRepository include:
    // - <S extends Vendor> S save(S entity);
    // - Optional<Vendor> findById(Long id);
    // - List<Vendor> findAll();
    // - void deleteById(Long id);
    // - Page<Vendor> findAll(Pageable pageable);

    // Additional custom query methods can be defined here if needed.
    // For example, to find vendors by name, you might add:
    // List<Vendor> findByName(String name);
}
