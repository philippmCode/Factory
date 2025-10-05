package com.philippmeyer.factory.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "FILTER")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "FILTER_MERKMALSKATEGORIE",
            joinColumns = @JoinColumn(name = "FILTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MERKMALSKATEGORIE_ID")
    )
    private Set<Merkmalskategorie> merkmalskategorien = new HashSet<>();

    // the ID's of the merkmalskategorien that are selected for the filter in the frontend
    @Transient
    private List<Long> merkmalskategorieIds;

    // Constructor
    public Filter() {
    }

    public Set<Merkmalskategorie> getMerkmalskategorien() {
        return merkmalskategorien;
    }

    public void setMerkmalskategorien(Set<Merkmalskategorie> merkmalskategorien) {
        this.merkmalskategorien = merkmalskategorien;
    }
    
    public List<Long> getMerkmalskategorieIds() {
        if (merkmalskategorieIds == null) {
            merkmalskategorieIds = new ArrayList<>();
        }
        return merkmalskategorieIds;
    }

    public void setMerkmalskategorieIds(List<Long> merkmalskategorieIds) {
        this.merkmalskategorieIds = merkmalskategorieIds;
    }

}