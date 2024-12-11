package org.kevin.garrett.repository;

import org.kevin.garrett.model.CarbonFootprintEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CarbonFootprintEntryRepository extends JpaRepository<CarbonFootprintEntry, Long> {

    // Retrieve all entries for a specific user ID
    List<CarbonFootprintEntry> findByUserId(Long userId);

    // Retrieve all entries for a specific user ID and date
    List<CarbonFootprintEntry> findByUserIdAndDate(Long userId, LocalDate date);
}
