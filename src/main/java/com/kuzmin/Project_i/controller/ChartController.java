package com.kuzmin.Project_i.controller;

import com.kuzmin.Project_i.model.AggregationType;
import com.kuzmin.Project_i.model.Chart;
import com.kuzmin.Project_i.model.ChartType;
import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.service.ChartService;
import com.kuzmin.Project_i.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/charts")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;
    private final DashboardService dashboardService;

    @GetMapping("/create/{dashboardId}")
    public String createPage(
            @PathVariable Long dashboardId,
            Model model
    ) {

        model.addAttribute(
                "chart",
                new Chart()
        );

        model.addAttribute(
                "dashboardId",
                dashboardId
        );

        model.addAttribute(
                "chartTypes",
                ChartType.values()
        );

        model.addAttribute(
                "aggregationTypes",
                AggregationType.values()
        );

        return "charts/create";
    }

    @PostMapping("/create/{dashboardId}")
    public String create(
            @PathVariable Long dashboardId,
            @ModelAttribute Chart chart
    ) {

        Dashboard dashboard = dashboardService.findById(dashboardId);

        chart.setDashboard(dashboard);

        chartService.save(chart);

        return "redirect:/dashboard";
    }

}