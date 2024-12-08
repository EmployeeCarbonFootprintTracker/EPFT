


package org.kevin.garrett.controller;
import org.kevin.garrett.service.ActivityService;
import org.kevin.garrett.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.kevin.garrett.model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;





@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("activities", activityService.getAllActivities());
        return "adminDashboard";
    }
}
