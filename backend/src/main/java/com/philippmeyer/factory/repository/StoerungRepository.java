package com.philippmeyer.factory.repository;

import com.philippmeyer.factory.entity.Stoerung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for data access operations related to the Stoerung (Disruption) entity.
 * It extends JpaRepository for standard CRUD operations and defines custom methods
 * to query aggregated data directly from a database Materialized View (MV).
 */
public interface StoerungRepository extends JpaRepository<Stoerung, Long> {

    // Inherits standard CRUD methods (save, findById, findAll, delete) from JpaRepository.

    /**
     * Retrieves aggregated disruption sums for today directly from the Materialized View.
     * This query is native SQL executed against the database, bypassing standard JPA entity mapping for performance.
     * The results are mapped to the StoerungSumToday projection interface.
     *
     * @return A list of aggregated disruption sums, ordered by date and sum descending.
     */
    @Query(value = "SELECT s.merkmal AS merkmal, s.datum AS datum, s.summe AS summe " +
            "FROM SYSTEM.MV_STOERUNG_SUMME_HEUTE s " + // Direct path to the Materialized View
            "ORDER BY s.datum DESC, s.summe DESC",
            nativeQuery = true) // Crucial: Specifies that the query is native SQL.
    List<StoerungSumToday> findAggregatedStoerungSums();

    /**
     * Retrieves aggregated disruption sums for today, filtered by a specific Filter ID.
     * This query joins the Materialized View (MV) with the application's mapping tables
     * (MERKMAL, MERKMALSKATEGORIE, FILTER_MERKMALSKATEGORIE) to apply the filter logic.
     *
     * @param filterId The ID of the filter configuration used to limit the results.
     * @return A list of filtered, aggregated disruption sums.
     */
    @Query(value = "SELECT mv.merkmal, mv.datum, mv.summe " +
            "FROM SYSTEM.MV_STOERUNG_SUMME_HEUTE mv " +
            "INNER JOIN SYSTEM.MERKMAL m ON m.merkmal_bezeichner = mv.merkmal " +
            "INNER JOIN SYSTEM.MERKMALSKATEGORIE mk ON mk.id = m.merkmalskategorie_id " +
            "INNER JOIN SYSTEM.FILTER_MERKMALSKATEGORIE fmk ON fmk.merkmalskategorie_id = mk.id " +
            "WHERE fmk.filter_id = :filterId",
            nativeQuery = true)
    List<StoerungSumToday> findAggregatedStoerungSumsByFilter(@Param("filterId") Long filterId);
}