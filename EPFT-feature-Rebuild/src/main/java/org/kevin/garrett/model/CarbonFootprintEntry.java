package org.kevin.garrett.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class CarbonFootprintEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDate date;

    private String activityType; // e.g., "Transportation", "Waste", etc.
    private double distance;     // For transportation
    private String carType;      // e.g., "Gasoline", "Diesel", "Electric"
    private double weight;       // For waste
    private int points;          // Gamification points
    private double co2Saved;     // CO2 impact in kg

    // New Fields
    private String thermostatAdjustment;

    // Changed to Integer to allow for null values
    private Integer degreeChange;

    private int showerDuration;
    private String plantBasedMeals;
    private int mealCount;
    private String wasteType;
    private double wasteAmount;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getCo2Saved() {
        return co2Saved;
    }

    public void setCo2Saved(double co2Saved) {
        this.co2Saved = co2Saved;
    }

    // New Getters and Setters
    public String getThermostatAdjustment() {
        return thermostatAdjustment;
    }

    public void setThermostatAdjustment(String thermostatAdjustment) {
        this.thermostatAdjustment = thermostatAdjustment;
    }

    /**
     * Getter for degreeChange.
     * Fixes NullPointerException when degreeChange is null.
     * Returns 0 as a default value if degreeChange is not set.
     */
    public int getDegreeChange() {
        return (degreeChange != null) ? degreeChange : 0; // Default to 0 if null
    }

    public void setDegreeChange(Integer degreeChange) {
        this.degreeChange = degreeChange; // Accepts null values
    }

    public int getShowerDuration() {
        return showerDuration;
    }

    public void setShowerDuration(int showerDuration) {
        this.showerDuration = showerDuration;
    }

    public String getPlantBasedMeals() {
        return plantBasedMeals;
    }

    public void setPlantBasedMeals(String plantBasedMeals) {
        this.plantBasedMeals = plantBasedMeals;
    }

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public double getWasteAmount() {
        return wasteAmount;
    }

    public void setWasteAmount(double wasteAmount) {
        this.wasteAmount = wasteAmount;
    }
}
