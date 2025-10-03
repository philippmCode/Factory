package com.philippmeyer.factory.entity;

import jakarta.persistence.*;

@Entity
public class Merkmalskategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-Inkrement-PK
    private int id;
    private String bezeichner;

    public Merkmalskategorie() {
        // Standard-Konstruktor
    }

    @Override
    public String toString() {
        return "com.philippmeyer.production.entity.Merkmalskategorie{" +
                "id=" + id +
                ", bezeichner='" + bezeichner +
                '}';
    }

    // Getter und Setter für alle Felder hinzufügen
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBezeichner() { return bezeichner; }
    public void setBezeichner(String bezeichner) { this.bezeichner = bezeichner; }
}
