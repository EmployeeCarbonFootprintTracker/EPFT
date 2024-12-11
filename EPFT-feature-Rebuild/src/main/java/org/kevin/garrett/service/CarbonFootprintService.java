package org.kevin.garrett.service;

import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.model.User;
import org.kevin.garrett.repository.CarbonFootprintEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarbonFootprintService {

    private final CarbonFootprintEntryRepository repository;

    @Autowired
    public CarbonFootprintService(CarbonFootprintEntryRepository repository) {
        this.repository = repository;
    }

    // Existing Methods
    public List<CarbonFootprintEntry> getEntriesByUserAndDate(Long userId, LocalDate date) {
        return repository.findByUserIdAndDate(userId, date);
    }

    public double calculateTransportationCO2(double distance, String carType) {
        return distance * (carType.equalsIgnoreCase("Electric") ? 0.1 : 0.25);
    }

    public int calculatePoints(String activityType, double distance) {
        return (int) distance * (activityType.equalsIgnoreCase("Transportation") ? 2 : 1);
    }

    public CarbonFootprintEntry saveEntry(CarbonFootprintEntry entry) {
        return repository.save(entry);
    }

    public List<CarbonFootprintEntry> getEntriesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    // New Methods

    // 1. Fetch a single entry by ID and validate ownership
    public CarbonFootprintEntry getEntryByIdAndUser(Long id, User user) {
        return repository.findById(id)
                .filter(entry -> entry.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Entry not found or unauthorized"));
    }

    // 2. Update an entry (ownership validation included)
    public CarbonFootprintEntry updateEntry(Long id, CarbonFootprintEntry updatedEntry, User user) {
        CarbonFootprintEntry existingEntry = getEntryByIdAndUser(id, user);

        existingEntry.setActivityType(updatedEntry.getActivityType());
        existingEntry.setDistance(updatedEntry.getDistance());
        existingEntry.setCarType(updatedEntry.getCarType());
        existingEntry.setThermostatAdjustment(updatedEntry.getThermostatAdjustment());
        existingEntry.setDegreeChange(updatedEntry.getDegreeChange());
        existingEntry.setShowerDuration(updatedEntry.getShowerDuration());
        existingEntry.setPlantBasedMeals(updatedEntry.getPlantBasedMeals());
        existingEntry.setMealCount(updatedEntry.getMealCount());
        existingEntry.setWasteType(updatedEntry.getWasteType());
        existingEntry.setWasteAmount(updatedEntry.getWasteAmount());

        return repository.save(existingEntry);
    }

    // 3. Delete an entry (ownership validation included)
    public void deleteEntry(Long id, User user) {
        CarbonFootprintEntry entry = getEntryByIdAndUser(id, user);
        repository.delete(entry);
    }
}
