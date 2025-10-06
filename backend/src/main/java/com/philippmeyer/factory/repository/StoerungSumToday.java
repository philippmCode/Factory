package com.philippmeyer.factory.repository;

import java.time.Instant;

/**
 * Interface Projection used to capture the results of a database aggregation query,
 * specifically the aggregated sums of disruptions (Stoerung) for today retrieved from the Materialized View.
 * * The method names MUST match the column aliases returned by the native SQL query in StoerungRepository.
 */
public interface StoerungSumToday {

    /**
     * Maps to the 'merkmal' (characteristic/type) column from the query result.
     * @return The disruption characteristic name.
     */
    String getMerkmal();

    /**
     * Maps to the 'datum' (date/timestamp) column from the query result.
     * Uses Instant to capture a full timestamp.
     * @return The timestamp of the aggregation result.
     */
    Instant getDatum();

    /**
     * Maps to the 'summe' (total sum of downtime/improvements) column from the query result.
     * @return The aggregated sum value.
     */
    Long getSumme();
}