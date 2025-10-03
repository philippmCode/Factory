package com.philippmeyer.factory.repository;

import com.philippmeyer.factory.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {

    // Methode zum Abrufen der Daten aus der Materialized View
    @Query(value = "SELECT s.ID, s.NAME FROM FILTER s", nativeQuery = true)
    List<Filter> findAll();
}