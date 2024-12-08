package org.kevin.garrett.service;

import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.repository.CarbonFootprintEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private CarbonFootprintEntryRepository entryRepository;

    /**
     * Retrieves all activities (Carbon Footprint Entries) for a specific user ID.
     *
     * @param userId The ID of the user whose activities are to be retrieved.
     * @return A list of CarbonFootprintEntry objects for the user.
     */
    public List<CarbonFootprintEntry> getActivitiesByUser(Long userId) {
        if (userId == null || userId <= 0) {
            logger.warn("Invalid user ID provided: {}", userId);
            return Collections.emptyList();
        }
        try {
            List<CarbonFootprintEntry> activities = entryRepository.findByUserId(userId);
            logger.info("Retrieved {} activities for user ID {}", activities.size(), userId);
            return activities;
        } catch (Exception e) {
            logger.error("Error retrieving activities for user ID {}: {}", userId, e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves all activities for a user within a specific date range by user ID.
     *
     * @param userId The ID of the user.
     * @param date   The specific date for filtering activities.
     * @return A list of CarbonFootprintEntry objects matching the criteria.
     */
    public List<CarbonFootprintEntry> getActivitiesByUserAndDate(Long userId, LocalDate date) {
        if (userId == null || userId <= 0 || date == null) {
            logger.warn("Invalid parameters provided: userId={}, date={}", userId, date);
            return Collections.emptyList();
        }
        try {
            return entryRepository.findByUserIdAndDate(userId, date);
        } catch (Exception e) {
            logger.error("Error retrieving activities for user ID {} on date {}: {}", userId, date, e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves all activities (Carbon Footprint Entries) in the system.
     *
     * @return A list of all CarbonFootprintEntry objects.
     */
    public List<CarbonFootprintEntry> getAllActivities() {
        try {
            List<CarbonFootprintEntry> activities = entryRepository.findAll();
            logger.info("Retrieved {} total activities", activities.size());
            return activities;
        } catch (Exception e) {
            logger.error("Error retrieving all activities: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Saves a new CarbonFootprintEntry to the database.
     *
     * @param entry The CarbonFootprintEntry object to save.
     * @return The saved CarbonFootprintEntry object.
     */
    public CarbonFootprintEntry saveActivity(CarbonFootprintEntry entry) {
        if (entry == null) {
            logger.warn("Attempted to save a null CarbonFootprintEntry");
            return null;
        }
        try {
            CarbonFootprintEntry savedEntry = entryRepository.save(entry);
            logger.info("Saved CarbonFootprintEntry with ID {}", savedEntry.getId());
            return savedEntry;
        } catch (Exception e) {
            logger.error("Error saving CarbonFootprintEntry: {}", e.getMessage());
            return null;
        }
    }
}
