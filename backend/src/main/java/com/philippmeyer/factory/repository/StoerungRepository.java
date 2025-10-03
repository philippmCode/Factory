package com.philippmeyer.factory.repository;

import com.philippmeyer.factory.entity.Stoerung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoerungRepository extends JpaRepository<Stoerung, Long> {

    // Methode zum Abrufen der Daten aus der Materialized View
    @Query(value = "SELECT s.merkmal AS merkmal, s.datum AS datum, s.summe AS summe " +
            "FROM SYSTEM.MV_STOERUNG_SUMME_HEUTE s " + // Direkter Pfad zur MV
            "ORDER BY s.datum DESC, s.summe DESC",
            nativeQuery = true) // Wichtig: Es ist eine native SQL-Abfrage gegen die MV
    List<StoerungSumToday> findAggregatedStoerungSums();

    @Query(value = "SELECT mv.merkmal, mv.datum, mv.summe " +
            "FROM SYSTEM.MV_STOERUNG_SUMME_HEUTE mv " +
            "INNER JOIN SYSTEM.MERKMAL m ON m.merkmal_bezeichner = mv.merkmal " +
            "INNER JOIN SYSTEM.MERKMALSKATEGORIE mk ON mk.id = m.merkmalskategorie_id " +
            "INNER JOIN SYSTEM.FILTER_MERKMALSKATEGORIE fmk ON fmk.merkmalskategorie_id = mk.id " +
            "WHERE fmk.filter_id = :filterId",
            nativeQuery = true)
    List<StoerungSumToday> findAggregatedStoerungSumsByFilter(@Param("filterId") Long filterId);

}