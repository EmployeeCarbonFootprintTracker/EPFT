
package org.kevin.garrett.controller;

import org.kevin.garrett.model.User;
import org.kevin.garrett.model.CarbonFootprintEntry;
import org.kevin.garrett.service.UserService;
import org.kevin.garrett.service.CarbonFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CarbonFootprintService carbonFootprintService;

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(Model model, Principal principal) {
        // Fetch the logged-in user
        User user = userService.getUserByUsername(principal.getName());
        System.out.println("Logged-in user: " + user.getUsername());
        System.out.println("User ID: " + user.getId());

        // Fetch user-specific carbon footprint entries
        List<CarbonFootprintEntry> activities = carbonFootprintService.getEntriesByUser(user.getId());
        System.out.println("Number of activities: " + activities.size());

        // Add user and activities to the model
        model.addAttribute("user", user);
        model.addAttribute("activities", activities);

        return "employeeDashboard"; // Render the employeeDashboard.html view
    }
}