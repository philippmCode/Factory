package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Stoerung;
import com.philippmeyer.factory.repository.StoerungSumToday;
import com.philippmeyer.factory.service.StoerungService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsible for handling web requests related to the Stoerung (Disruption/Fault) entity.
 * It provides endpoints for CRUD operations and retrieving aggregated data.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/stoerung") // Defines the base path for all endpoints (e.g., /stoerung).
public class StoerungController {

    // Dependency Injection: The service layer handles business logic.
    private final StoerungService service;

    /**
     * Constructor for dependency injection.
     * @param service The service component for Stoerung business logic.
     */
    public StoerungController(StoerungService service) {
        this.service = service;
    }

    /**
     * Handles HTTP GET requests to /stoerung/sum.
     * This endpoint retrieves aggregated disruption data (e.g., sums of downtime).
     * The aggregation can optionally be filtered by a specific filter ID.
     *
     * @param filterId Optional parameter to filter the aggregation results.
     * @return A list of aggregated Stoerung data, mapped to the StoerungSumToday interface.
     */
    @GetMapping("/sum")
    public List<StoerungSumToday> getStoerungSumByFilter(@RequestParam(required = false) Long filterId) {
        if (filterId != null) {
            // Retrieve sums filtered by a specific ID
            return service.getAggregatedStoerungSumsByFilter(filterId);
        } else {
            // Retrieve unfiltered, aggregated sums
            return service.getAggregatedStoerungSums();
        }
    }

    /**
     * Handles HTTP GET requests to the base path (/stoerung).
     * Retrieves all raw Stoerung (Disruption) entities.
     * @return A list of all Stoerung entities.
     */
    @GetMapping
    public List<Stoerung> getAll() {
        return service.findAll();
    }

    /**
     * Handles HTTP POST requests to the base path (/stoerung).
     * Creates a new Stoerung (Disruption) entity in the database.
     * @param stoerung The Stoerung object to be saved, passed in the request body.
     * @return The newly created and saved Stoerung entity, including its generated ID.
     */
    @PostMapping
    public Stoerung create(@RequestBody Stoerung stoerung) {
        return service.save(stoerung);
    }
}