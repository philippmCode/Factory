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

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final MerkmalskategorieRepository merkmalskategorieRepository;

    @Autowired // Konstruktor-Injection
    public FilterService(FilterRepository filterRepository, MerkmalskategorieRepository merkmalskategorieRepository) {
        this.filterRepository = filterRepository;
        this.merkmalskategorieRepository = merkmalskategorieRepository;
    }

    @Transactional // Sorgt dafür, dass die gesamte Operation (Filter speichern und Verknüpfungen) atomar ist
    public Filter save(Filter filter) {
        // Überprüfe, ob IDs von Merkmalskategorien vom Frontend gesendet wurden
        if (filter.getMerkmalskategorieIds() != null && !filter.getMerkmalskategorieIds().isEmpty()) {
            // Lade die tatsächlichen Merkmalskategorie-Entitäten anhand der empfangenen IDs
            List<Merkmalskategorie> selectedCategories = merkmalskategorieRepository.findAllById(filter.getMerkmalskategorieIds());

            // Setze die geladenen Entitäten auf das merkmalskategorien Set des Filter-Objekts
            // (Dies überschreibt ggf. ein leeres Set, das beim Instanziieren erstellt wurde)
            filter.setMerkmalskategorien(new HashSet<>(selectedCategories));
        } else {
            // Wenn keine IDs gesendet wurden, stelle sicher, dass das Set leer ist
            filter.setMerkmalskategorien(new HashSet<>());
        }

        // Speichere den Filter. JPA wird die Many-to-Many-Beziehung in der Join-Tabelle
        // (FILTER_MERKMALSKATEGORIE) automatisch verwalten.
        return filterRepository.save(filter);
    }

    public List<Filter> findAll() {
        return filterRepository.findAll(); // Nutzt deine überschriebene findAll() im Repository
    }

    // Optional: Eine Methode, um Filter nach ID zu finden, falls benötigt
    public Filter findById(Long id) {
        return filterRepository.findById(id).orElse(null);
    }

    // Optional: Eine Methode, um Filter zu löschen
    @Transactional
    public void deleteById(Long id) {
        filterRepository.deleteById(id);
    }
}