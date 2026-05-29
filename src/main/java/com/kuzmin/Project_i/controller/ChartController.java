package com.kuzmin.Project_i.controller;
import com.kuzmin.Project_i.model.Chart;
import com.kuzmin.Project_i.service.ChartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/charts")
public class ChartController {
    private final ChartService chartService;
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute(
                "chart",
                new Chart()
        );
        return "charts/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute Chart chart
    ) {
        chartService.save(chart);
        return "redirect:/dashboards/default";
    }
}