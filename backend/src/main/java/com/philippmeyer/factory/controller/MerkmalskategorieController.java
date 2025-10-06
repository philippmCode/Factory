package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.service.MerkmalskategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsible for handling web requests related to the Merkmalskategorie (Feature Category) entity.
 * It exposes API endpoints for retrieving category data.
 */
@RestController
@RequestMapping("/merkmalskategorie")
public class MerkmalskategorieController {

    // Dependency Injection
    private final MerkmalskategorieService service;

    /**
     * Constructor for Dependency Injection (Constructor Injection).
     * Spring automatically injects the necessary service dependency when creating the controller bean.
     * @param service The service component responsible for business logic regarding Merkmalskategorie.
     */
    public MerkmalskategorieController(MerkmalskategorieService service) {
        this.service = service;
    }

    /**
     * Handles HTTP GET requests to the base path (/merkmalskategorie).
     * This endpoint retrieves all available feature categories.
     * * @return A list of all Merkmalskategorie entities.
     */
    @GetMapping
    public List<Merkmalskategorie> getAll() {
        // Delegates the task of fetching all categories to the business service layer.
        return service.findAll();
    }

}