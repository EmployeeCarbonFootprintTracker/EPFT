
package org.kevin.garrett.controller;
import org.kevin.garrett.service.ReportService;
import org.kevin.garrett.service.TeamService;
import org.kevin.garrett.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.kevin.garrett.model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;





@Controller
public class ManagerController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/manager/dashboard")
    public String managerDashboard(Model model) {
        model.addAttribute("teamSummary", teamService.getTeamSummary());
        model.addAttribute("reports", reportService.getAggregatedReports());
        return "managerDashboard";
    }
}
