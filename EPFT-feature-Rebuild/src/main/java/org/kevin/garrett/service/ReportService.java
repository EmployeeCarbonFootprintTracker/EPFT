package org.kevin.garrett.service;



import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.repository.CarbonFootprintEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private CarbonFootprintEntryRepository entryRepository;

    public double getTotalCo2Saved() {
        // Sum up all CO2 saved across all entries
        return entryRepository.findAll().stream()
                .mapToDouble(CarbonFootprintEntry::getCo2Saved)
                .sum();
    }

    public List<CarbonFootprintEntry> getAggregatedReports() {
        // Placeholder for more complex logic
        return entryRepository.findAll();
    }
}
