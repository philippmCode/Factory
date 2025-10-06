package com.philippmeyer.factory.repository;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for data access operations related to the Merkmalskategorie entity.
 * Spring Data JPA automatically provides the implementation for this interface at runtime.
 */
public interface MerkmalskategorieRepository extends JpaRepository<Merkmalskategorie, Long> {

    // Inherits standard CRUD methods (save, findById, findAll, delete) from JpaRepository.

    /**
     * Finds all Merkmalskategorie entities.
     * Note: This method is inherited from JpaRepository but is explicitly declared here.
     * @return A list of all Merkmalskategorie entities.
     */
    List<Merkmalskategorie> findAll();

    /**
     * Finds all Merkmalskategorie entities whose IDs are contained in the given iterable collection.
     * This is useful for fetching multiple categories based on a list of IDs (e.g., from a frontend filter selection).
     * Spring Data JPA automatically generates the query for this method name.
     * @param ids An iterable containing the IDs (primary keys) to search for.
     * @return A list of matching Merkmalskategorie entities.
     */
    List<Merkmalskategorie> findAllById(Iterable<Long> ids);
}