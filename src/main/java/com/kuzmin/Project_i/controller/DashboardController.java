package com.kuzmin.Project_i.controller;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String dashboard(
            Model model,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        model.addAttribute(
                "dashboards",
                dashboardService.findAllByUser(user)
        );

        model.addAttribute(
                "stats",
                dashboardService.getStatistics(user)
        );

        return "dashboard/index";
    }

    @GetMapping("/{id}")
    public String dashboardById(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        Dashboard dashboard = dashboardService.findByIdAndUser(id, user);

        model.addAttribute(
                "dashboard",
                dashboard
        );

        model.addAttribute(
                "charts",
                dashboard.getCharts()
        );

        model.addAttribute(
                "dashboards",
                dashboardService.findAllByUser(user)
        );

        model.addAttribute(
                "stats",
                dashboardService.getStatistics(user)
        );

        return "dashboard/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute(
                "dashboard",
                new Dashboard()
        );

        return "dashboard/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Dashboard dashboard,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        dashboard.setUser(user);

        dashboardService.save(dashboard);

        return "redirect:/dashboards";
    }

}