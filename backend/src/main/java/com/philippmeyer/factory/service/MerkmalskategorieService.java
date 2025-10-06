package com.philippmeyer.factory.service;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.repository.MerkmalskategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer component for handling business logic related to Merkmalskategorie (Feature Categories).
 * It acts as the intermediary between the Controller and the Repository.
 */
@Service
public class MerkmalskategorieService {

    // Dependency: The repository handles direct database interaction.
    private final MerkmalskategorieRepository repository;

    /**
     * Constructor for Dependency Injection. Spring automatically injects the Repository instance.
     * @param repository The data access object for Merkmalskategorie entities.
     */
    public MerkmalskategorieService(MerkmalskategorieRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all Merkmalskategorie entities from the database.
     * @return A list of all Feature Categories.
     */
    public List<Merkmalskategorie> findAll() {
        // Delegates the data retrieval to the JPA Repository.
        return repository.findAll();
    }
}