// src/main/java/com/philippmeyer/production/service/FilterService.java
package com.philippmeyer.factory.service;

import com.philippmeyer.factory.entity.Filter;
import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.repository.FilterRepository;
import com.philippmeyer.factory.repository.MerkmalskategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

/**
 * Service class for managing Filter entities, handling business logic
 * such as linking transient category IDs to persistent entities.
 */
@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final MerkmalskategorieRepository merkmalskategorieRepository;

    /**
     * Injects the required repositories via constructor injection.
     */
    @Autowired
    public FilterService(FilterRepository filterRepository, MerkmalskategorieRepository merkmalskategorieRepository) {
        this.filterRepository = filterRepository;
        this.merkmalskategorieRepository = merkmalskategorieRepository;
    }

    /**
     * Saves a Filter entity, first resolving and linking Merkmalskategorie entities
     * based on the transient list of IDs (merkmalskategorieIds).
     *
     * @param filter The Filter object to save, potentially containing category IDs.
     * @return The persisted Filter entity.
     */
    @Transactional // Ensures the entire operation (fetching and saving) is atomic.
    public Filter save(Filter filter) {
        // Check if category IDs were provided from the frontend
        if (filter.getMerkmalskategorieIds() != null && !filter.getMerkmalskategorieIds().isEmpty()) {

            // Fetch the actual Merkmalskategorie entities based on the received IDs
            List<Merkmalskategorie> selectedCategories = merkmalskategorieRepository.findAllById(filter.getMerkmalskategorieIds());

            // Set the loaded entities on the filter's persistent set.
            filter.setMerkmalskategorien(new HashSet<>(selectedCategories));
        } else {
            // If no IDs were sent (null or empty list), ensure the persistent set is empty.
            filter.setMerkmalskategorien(new HashSet<>());
        }

        // Save the Filter. JPA will automatically manage the Many-to-Many relationship
        // in the join table (FILTER_MERKMALSKATEGORIE).
        return filterRepository.save(filter);
    }

    /**
     * Retrieves all Filter entities.
     * @return A list of all filters.
     */
    public List<Filter> findAll() {
        return filterRepository.findAll();
    }

}