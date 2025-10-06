package com.philippmeyer.factory.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a data filtering configuration (Filter) in the database.
 * A Filter groups multiple Merkmalskategorie (Feature Categories) for application logic.
 */
@Entity // Marks this class as a JPA entity, representing a database table.
@Table(name = "FILTER") // Specifies the name of the corresponding database table.
public class Filter {

    @Id // Designates this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the PK value is automatically generated (e.g., auto-increment).
    private Long id; // The unique identifier for the filter.

    @Column(name = "NAME", unique = true, nullable = false) // Maps to the NAME column; ensures uniqueness and prevents null values.
    private String name; // The descriptive name of the filter (e.g., "Quality Control Filter").

    /**
     * Defines a Many-to-Many relationship with the Merkmalskategorie entity.
     * This means one Filter can be associated with multiple Categories, and one Category
     * can be part of multiple Filters.
     */
    @ManyToMany
    @JoinTable(
            name = "FILTER_MERKMALSKATEGORIE", // Specifies the name of the join table (the intermediate table).
            joinColumns = @JoinColumn(name = "FILTER_ID"), // Defines the foreign key column in the join table that refers to this entity (Filter).
            inverseJoinColumns = @JoinColumn(name = "MERKMALSKATEGORIE_ID") // Defines the foreign key column in the join table that refers to the target entity (Merkmalskategorie).
    )
    private Set<Merkmalskategorie> merkmalskategorien = new HashSet<>(); // The collection of associated feature categories. Using a Set ensures uniqueness.

    /**
     * Holds the IDs of the Merkmalskategorien that are selected for this filter in the frontend.
     * This field is used for data transfer (DTO-like behavior) but should NOT be persisted in the database.
     */
    @Transient // Marks the field to be ignored by the JPA persistence mechanism.
    private List<Long> merkmalskategorieIds;

    /**
     * Default constructor required by JPA.
     */
    public Filter() {
    }

    // --- Getters and Setters for merkmalskategorien ---

    /**
     * Retrieves the set of Merkmalskategorie entities associated with this filter.
     * @return The set of associated feature categories.
     */
    public Set<Merkmalskategorie> getMerkmalskategorien() {
        return merkmalskategorien;
    }

    /**
     * Sets the set of Merkmalskategorie entities associated with this filter.
     * @param merkmalskategorien The new set of associated feature categories.
     */
    public void setMerkmalskategorien(Set<Merkmalskategorie> merkmalskategorien) {
        this.merkmalskategorien = merkmalskategorien;
    }

    // --- Getters and Setters for merkmalskategorieIds (Transient Field) ---

    /**
     * Retrieves the list of IDs for the selected feature categories (used for frontend communication).
     * Initializes the list if it is currently null.
     * @return The list of category IDs.
     */
    public List<Long> getMerkmalskategorieIds() {
        if (merkmalskategorieIds == null) {
            merkmalskategorieIds = new ArrayList<>();
        }
        return merkmalskategorieIds;
    }

    /**
     * Sets the list of IDs for the selected feature categories.
     * @param merkmalskategorieIds The new list of category IDs.
     */
    public void setMerkmalskategorieIds(List<Long> merkmalskategorieIds) {
        this.merkmalskategorieIds = merkmalskategorieIds;
    }

    // --- Getters and Setters for Primary Fields ---

    /**
     * Retrieves the unique ID of the filter.
     * @return The filter ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the filter.
     * @param id The new filter ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the filter.
     * @return The filter name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the filter.
     * @param name The new filter name.
     */
    public void setName(String name) {
        this.name = name;
    }

}