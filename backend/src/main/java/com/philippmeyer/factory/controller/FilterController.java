package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Filter;
import com.philippmeyer.factory.service.FilterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsible for handling web requests related to the Filter entity.
 * It exposes API endpoints for retrieving filter data.
 */
@RestController
@RequestMapping("/filter")
public class FilterController {

    private final FilterService service;

    public FilterController(FilterService service) {
        this.service = service;
    }

    /**
     * GET /filter
     * Retrieves all Filter entities.
     * @return A list of all available filters.
     */
    @GetMapping
    public List<Filter> getAll() {
        return service.findAll();
    }

    /**
     * POST /filter
     * Creates a new Filter or updates an existing one.
     * @param filter The Filter object to be saved (contains transient category IDs).
     * @return The newly created or updated Filter entity.
     */
    @PostMapping
    public Filter create(@RequestBody Filter filter) {
        return service.save(filter);
    }
}

