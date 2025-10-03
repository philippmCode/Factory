package com.philippmeyer.factory.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Stoerung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-Inkrement-PK
    private int id;
    private String merkmal;
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    private int anzahl_verbesserungen;
    private int stoerminuten;

    public Stoerung() {
        // Standard-Konstruktor
    }

    @Override
    public String toString() {
        return "com.philippmeyer.production.entity.Stoerung{" +
                "id=" + id +
                ", merkmal='" + merkmal + '\'' +
                ", datum=" + datum +
                ", anzahl_verbesserungen=" + anzahl_verbesserungen +
                ", stoerminuten=" + stoerminuten +
                '}';
    }

    // Getter und Setter für alle Felder hinzufügen
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMerkmal() { return merkmal; }
    public void setMerkmal(String merkmal) { this.merkmal = merkmal; }

    public Date getDatum() { return datum; }
    public void setDatum(Date datum) { this.datum = datum; }

    public int getAnzahl_verbesserungen() { return anzahl_verbesserungen; }
    public void setAnzahl_verbesserungen(int anzahl_verbesserungen) { this.anzahl_verbesserungen = anzahl_verbesserungen; }

    public int getStoerminuten() { return stoerminuten; }
    public void setStoerminuten(int stoerminuten) { this.stoerminuten = stoerminuten; }


}
