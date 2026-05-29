package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboards")
public class DashboardController {
    private final DashboardService dashboardService;
    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/default")
    public String defaultDashboard(Model model) {
        model.addAttribute(
                "dashboards",
                dashboardService.findAll()
        );
        return "dashboards/default";
    }

    @GetMapping("/{id}")
    public String dashboardById(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "dashboard",
                dashboardService.findById(id)
        );
        return "dashboards/view";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute(
                "dashboard",
                new Dashboard()
        );
        return "dashboards/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Dashboard dashboard
    ) {
        dashboardService.save(dashboard);
        return "redirect:/dashboards/default";
    }
}