package org.kevin.garrett.controller;

import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.model.User;
import org.kevin.garrett.service.CarbonFootprintService;
import org.kevin.garrett.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/employee/carbon-footprint")
public class CarbonFootprintController {

    @Autowired
    private CarbonFootprintService service;

    @Autowired
    private UserService userService;

    // Render the Carbon Footprint Form
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("entry", new CarbonFootprintEntry());
        return "Carbon Footprint Form"; // Renders Carbon Footprint Form.html
    }

    // Handle Form Submission (Create Entry)
    @PostMapping("/create")
    public String createEntry(@ModelAttribute CarbonFootprintEntry entry,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        entry.setUser(currentUser);
        entry.setDate(LocalDate.now());

        // Additional calculations (if needed)
        if ("Transportation".equalsIgnoreCase(entry.getActivityType())) {
            double co2 = service.calculateTransportationCO2(entry.getDistance(), entry.getCarType());
            entry.setCo2Saved(co2);
            int points = service.calculatePoints(entry.getActivityType(), entry.getDistance());
            entry.setPoints(points);
        }

        service.saveEntry(entry);
        return "redirect:/employee/carbon-footprint/dashboard";
    }

    // Show the Employee Dashboard with Entries
    @GetMapping("/dashboard")
    public String showDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        List<CarbonFootprintEntry> entries = service.getEntriesByUser(currentUser.getId());
        model.addAttribute("activities", entries);
        model.addAttribute("user", currentUser);
        return "employeeDashboard"; // Renders employeeDashboard.html
    }

    // Handle Delete Request
    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        service.deleteEntry(id, currentUser);
        return "redirect:/employee/carbon-footprint/dashboard";
    }

    // Render Update Form with Existing Data
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        CarbonFootprintEntry entry = service.getEntryByIdAndUser(id, currentUser);
        model.addAttribute("entry", entry);
        return "Carbon Footprint Form"; // Reuse the form for updating
    }

    // Handle Update Request
    @PostMapping("/update/{id}")
    public String updateEntry(@PathVariable Long id, @ModelAttribute CarbonFootprintEntry updatedEntry,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.getUserByUsername(userDetails.getUsername());
        service.updateEntry(id, updatedEntry, currentUser);
        return "redirect:/employee/carbon-footprint/dashboard";
    }
}

