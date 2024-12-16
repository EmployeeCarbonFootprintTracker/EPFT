package org.kevin.garrett.service;

import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.model.User;
import org.kevin.garrett.repository.CarbonFootprintEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarbonFootprintService {

    private final CarbonFootprintEntryRepository repository;

    @Autowired
    public CarbonFootprintService(CarbonFootprintEntryRepository repository) {
        this.repository = repository;
    }

    // Fix: Added missing method to retrieve entries by user ID
    public List<CarbonFootprintEntry> getEntriesByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<CarbonFootprintEntry> getEntriesByUserAndDate(Long userId, LocalDate date) {
        return repository.findByUserIdAndDate(userId, date);
    }

    public double calculateTransportationCO2(double distance, String carType) {
        if ("Electric".equalsIgnoreCase(carType)) {
            return distance * 0.1;
        } else if ("Hybrid".equalsIgnoreCase(carType)) {
            return distance * 0.15;
        } else if ("Diesel".equalsIgnoreCase(carType)) {
            return distance * 0.3;
        } else {
            return distance * 0.25; // Default for Gasoline
        }
    }

    public double calculateCarpoolCO2(double distance, String carType) {
        return calculateTransportationCO2(distance, carType) / 4; // Assuming 4 passengers
    }

    public int calculatePoints(String activityType, double co2Saved) {
        if ("Transportation".equalsIgnoreCase(activityType)) {
            return (int) (co2Saved * 10);
        } else if ("Waste".equalsIgnoreCase(activityType)) {
            return (int) (co2Saved * 5);
        } else if ("Thermostat".equalsIgnoreCase(activityType)) {
            return (int) (co2Saved * 8);
        } else {
            return (int) (co2Saved * 2);
        }
    }

    public CarbonFootprintEntry saveEntry(CarbonFootprintEntry entry) {
        calculateAndSetMetrics(entry); // Invoke the calculation before saving
        return repository.save(entry);
    }

    public CarbonFootprintEntry updateEntry(Long id, CarbonFootprintEntry updatedEntry, User user) {
        CarbonFootprintEntry existingEntry = getEntryByIdAndUser(id, user);

        // Update Fields
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

        calculateAndSetMetrics(existingEntry); // Ensure recalculations
        return repository.save(existingEntry);
    }

    public void deleteEntry(Long id, User user) {
        CarbonFootprintEntry entry = getEntryByIdAndUser(id, user);
        repository.delete(entry);
    }

    public Map<String, Double> getDashboardSummary(Long userId) {
        List<CarbonFootprintEntry> entries = repository.findByUserId(userId);

        double totalCO2 = entries.stream()
                .mapToDouble(CarbonFootprintEntry::getCo2Saved).sum();
        int totalPoints = entries.stream()
                .mapToInt(CarbonFootprintEntry::getPoints).sum();
        double totalDistance = entries.stream()
                .mapToDouble(CarbonFootprintEntry::getDistance).sum();

        Map<String, Double> summary = new HashMap<>();
        summary.put("totalCO2", totalCO2);
        summary.put("totalPoints", (double) totalPoints);
        summary.put("totalDistance", totalDistance);

        return summary;
    }

    private void calculateAndSetMetrics(CarbonFootprintEntry entry) {
        double co2Saved = 0;

        if ("Transportation".equalsIgnoreCase(entry.getActivityType())) {
            if ("Carpool".equalsIgnoreCase(entry.getCarType())) {
                co2Saved = calculateCarpoolCO2(entry.getDistance(), entry.getCarType());
            } else {
                co2Saved = calculateTransportationCO2(entry.getDistance(), entry.getCarType());
            }
        } else if ("Waste".equalsIgnoreCase(entry.getActivityType())) {
            co2Saved = entry.getWasteAmount() * 0.5; // Example: 0.5 kg CO₂ saved per kg waste
        } else if ("Thermostat".equalsIgnoreCase(entry.getActivityType())) {
            co2Saved = entry.getDegreeChange() * 0.2; // 0.2 kg CO₂ saved per degree adjusted
        }

        int points = calculatePoints(entry.getActivityType(), co2Saved);
        entry.setCo2Saved(co2Saved);
        entry.setPoints(points);
    }

    public CarbonFootprintEntry getEntryByIdAndUser(Long id, User user) {
        return repository.findById(id)
                .filter(entry -> entry.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Entry not found or unauthorized"));
    }
}
