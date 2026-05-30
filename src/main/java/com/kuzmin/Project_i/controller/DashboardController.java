package com.kuzmin.Project_i.controller;

import com.kuzmin.Project_i.dto.DashboardFormDto;
import com.kuzmin.Project_i.dto.DashboardStatisticsDto;
import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.service.DashboardService;
import com.kuzmin.Project_i.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final SegmentService segmentService;

    @GetMapping
    public String dashboard(
            Model model,
            Authentication authentication
    ) {

        if (authentication == null) {
            return "redirect:/login";
        }

        User user = (User) authentication.getPrincipal();

        List<Dashboard> dashboards =
                dashboardService.findAllByUser(user);

        model.addAttribute(
                "dashboards",
                dashboards
        );

        model.addAttribute(
                "dashboard",
                null
        );

        model.addAttribute(
                "charts",
                List.of()
        );

        model.addAttribute(
                "stats",
                new DashboardStatisticsDto()
        );

        if (!dashboards.isEmpty()) {

            Dashboard dashboard =
                    dashboards.getFirst();

            model.addAttribute(
                    "dashboard",
                    dashboard
            );

            model.addAttribute(
                    "charts",
                    dashboard.getCharts()
            );

            model.addAttribute(
                    "stats",
                    dashboardService.getStatistics(
                            dashboard
                    )
            );
        }

        return "dashboard/index";
    }

    @GetMapping("/{id}")
    public String dashboardById(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        if (authentication == null) {
            return "redirect:/login";
        }

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
                dashboardService.getStatistics(
                        dashboard
                )
        );

        return "dashboard/index";
    }

    @GetMapping("/create")
    public String createPage(Model model) {

        model.addAttribute(
                "dashboardForm",
                new DashboardFormDto()
        );

        model.addAttribute(
                "isEdit",
                false
        );

        return "dashboard/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute DashboardFormDto form,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        Segment segment = new Segment();

        segment.setName(form.getSegmentName());

        segment.setAgeFrom(form.getAgeFrom());
        segment.setAgeTo(form.getAgeTo());

        segment.setSex(form.getSex());

        segment.setRegion(form.getRegion());

        segment.setDateOfRegistrationFrom(
                form.getDateOfRegistrationFrom()
        );

        segment.setDateOfRegistrationTo(
                form.getDateOfRegistrationTo()
        );

        segment.setCountOfOrdersFrom(
                form.getCountOfOrdersFrom()
        );

        segment.setCountOfOrdersTo(
                form.getCountOfOrdersTo()
        );

        segment.setAverageCheckFrom(
                form.getAverageCheckFrom()
        );

        segment.setAverageCheckTo(
                form.getAverageCheckTo()
        );

        segment.setTotalSpendsFrom(
                form.getTotalSpendsFrom()
        );

        segment.setTotalSpendsTo(
                form.getTotalSpendsTo()
        );

        segment.setLastOrderDateFrom(
                form.getLastOrderDateFrom()
        );

        segment.setLastOrderDateTo(
                form.getLastOrderDateTo()
        );

        segment.setUser(user);

        segmentService.save(segment);

        Dashboard dashboard = new Dashboard();

        dashboard.setName(
                form.getDashboardName()
        );

        dashboard.setDescription(
                form.getDashboardDescription()
        );

        dashboard.setUser(user);

        dashboard.setSegment(segment);

        dashboardService.save(dashboard);

        return "redirect:/dashboards";
    }

    @GetMapping("/{id}/edit")
    public String editPage(
            @PathVariable Long id,
            Authentication authentication,
            Model model
    ) {

        User user =
                (User) authentication.getPrincipal();

        Dashboard dashboard =
                dashboardService.findByIdAndUser(
                        id,
                        user
                );

        DashboardFormDto form =
                new DashboardFormDto();

        form.setDashboardName(
                dashboard.getName()
        );

        form.setDashboardDescription(
                dashboard.getDescription()
        );

        if (dashboard.getSegment() != null) {

            Segment segment =
                    dashboard.getSegment();

            form.setSegmentName(
                    segment.getName()
            );

            form.setAgeFrom(
                    segment.getAgeFrom()
            );

            form.setAgeTo(
                    segment.getAgeTo()
            );

            form.setSex(
                    segment.getSex()
            );

            form.setRegion(
                    segment.getRegion()
            );
        }

        model.addAttribute(
                "dashboardForm",
                form
        );

        model.addAttribute(
                "dashboardId",
                dashboard.getId()
        );

        model.addAttribute(
                "isEdit",
                true
        );

        return "dashboard/create";
    }

    @PostMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute DashboardFormDto form,
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        Dashboard dashboard =
                dashboardService.findByIdAndUser(
                        id,
                        user
                );

        dashboard.setName(
                form.getDashboardName()
        );

        dashboard.setDescription(
                form.getDashboardDescription()
        );

        Segment segment =
                dashboard.getSegment();

        if (segment != null) {

            segment.setName(
                    form.getSegmentName()
            );

            segment.setAgeFrom(
                    form.getAgeFrom()
            );

            segment.setAgeTo(
                    form.getAgeTo()
            );

            segment.setSex(
                    form.getSex()
            );

            segment.setRegion(
                    form.getRegion()
            );

            segmentService.save(
                    segment
            );
        }

        dashboardService.save(
                dashboard
        );

        return "redirect:/dashboards/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        if (authentication == null) {
            return "redirect:/login";
        }

        User user = (User) authentication.getPrincipal();

        Dashboard dashboard =
                dashboardService.findByIdAndUser(
                        id,
                        user
                );

        dashboardService.deleteById(
                dashboard.getId()
        );

        Segment segment =
                dashboard.getSegment();

        dashboardService.deleteById(
                dashboard.getId()
        );

        if (segment != null) {
            segmentService.deleteById(
                    segment.getId()
            );
        }


        return "redirect:/dashboards";
    }

}