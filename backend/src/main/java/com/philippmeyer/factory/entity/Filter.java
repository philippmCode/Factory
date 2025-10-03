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

    // NEU: Transient-Feld, um die IDs vom Frontend zu empfangen.
    // Dies ist KEIN Persistenz-Feld und wird nicht in der Datenbank gespeichert.
    @Transient
    private List<Long> merkmalskategorieIds;

    // Konstruktoren
    public Filter() {
    }

    public Filter(String name) {
        this.name = name;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Merkmalskategorie> getMerkmalskategorien() {
        return merkmalskategorien;
    }

    public void setMerkmalskategorien(Set<Merkmalskategorie> merkmalskategorien) {
        this.merkmalskategorien = merkmalskategorien;
    }

    // Getter und Setter für das Transient-Feld
    public List<Long> getMerkmalskategorieIds() {
        if (merkmalskategorieIds == null) {
            merkmalskategorieIds = new ArrayList<>();
        }
        return merkmalskategorieIds;
    }

    public void setMerkmalskategorieIds(List<Long> merkmalskategorieIds) {
        this.merkmalskategorieIds = merkmalskategorieIds;
    }

    // Hilfsmethode zum Hinzufügen einer einzelnen Kategorie (optional)
    public void addMerkmalskategorie(Merkmalskategorie kategorie) {
        this.merkmalskategorien.add(kategorie);
    }
}