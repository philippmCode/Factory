package com.philippmeyer.factory.repository;

import java.time.Instant;

public interface StoerungSumToday {
    String getMerkmal();
    Instant getDatum(); // Use LocalDate for date-only values
    Long getSumme();
}

