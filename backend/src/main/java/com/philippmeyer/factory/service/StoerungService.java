package com.philippmeyer.factory.service;

import com.philippmeyer.factory.entity.Stoerung;
import com.philippmeyer.factory.repository.StoerungRepository;
import com.philippmeyer.factory.repository.StoerungSumToday;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer component for handling business logic related to Stoerung (Disruption/Fault) events.
 * It coordinates data retrieval from the repository and prepares it for the controller.
 */
@Service
public class StoerungService {

    // Dependency: The repository handles direct database interaction.
    private final StoerungRepository repository;

    /**
     * Constructor for Dependency Injection. Spring automatically injects the Repository instance.
     * @param repository The data access object for Stoerung entities.
     */
    public StoerungService(StoerungRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all raw Stoerung entities from the database.
     * @return A list of all raw Disruption entities.
     */
    public List<Stoerung> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves aggregated disruption sums (downtime, etc.) for today, without filtering.
     * This data is sourced from a Materialized View via the repository.
     * @return A list of aggregated sums mapped to the StoerungSumToday projection.
     */
    public List<StoerungSumToday> getAggregatedStoerungSums() {
        return repository.findAggregatedStoerungSums();
    }

    /**
     * Retrieves aggregated disruption sums for today, filtered based on a specific configuration.
     * @param filterId The ID of the filter to apply to the aggregation query.
     * @return A list of filtered aggregated sums mapped to the StoerungSumToday projection.
     */
    public List<StoerungSumToday> getAggregatedStoerungSumsByFilter(Long filterId) {
        return repository.findAggregatedStoerungSumsByFilter(filterId);
    }

    /**
     * Saves a new Stoerung entity to the database or updates an existing one.
     * @param stoerung The Stoerung object to be persisted.
     * @return The saved/updated Stoerung entity.
     */
    public Stoerung save(Stoerung stoerung) {
        return repository.save(stoerung);
    }
}