package com.kuzmin.Project_i.controller;

import com.kuzmin.Project_i.dto.DashboardFormDto;
import com.kuzmin.Project_i.dto.DashboardStatisticsDto;
import com.kuzmin.Project_i.model.*;
import com.kuzmin.Project_i.service.CustomerService;
import com.kuzmin.Project_i.service.DashboardService;
import com.kuzmin.Project_i.service.DashboardSettingsService;
import com.kuzmin.Project_i.service.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final SegmentService segmentService;
    private final DashboardSettingsService dashboardSettingsService;
    private final CustomerService customerService;

    @GetMapping
    public String dashboard(
            Model model,
            Authentication authentication
    ) {

        if (authentication == null) {
            return "redirect:/login";
        }

        User user = (User) authentication.getPrincipal();

        List<Dashboard> dashboards = dashboardService.findAllByUser(user);

        model.addAttribute(
                "dashboards",
                dashboards
        );

        model.addAttribute(
                "importedFileName",
                user.getLastImportedFileName()
        );

        model.addAttribute(
                "dashboard",
                null
        );

        model.addAttribute(
                "sexValues",
                Sex.values()
        );

        model.addAttribute(
                "regions",
                customerService.findDistinctRegions()
        );

        model.addAttribute(
                "charts",
                List.of()
        );

        model.addAttribute(
                "stats",
                new DashboardStatisticsDto()
        );

        model.addAttribute(
                "dashboardForm",
                new DashboardFormDto()
        );

        if (!dashboards.isEmpty()) {

            Dashboard dashboard = dashboards.getFirst();

            model.addAttribute(
                    "dashboard",
                    dashboard
            );

            DashboardSettings settings = dashboard.getSettings();

            if (settings == null) {
                settings = new DashboardSettings();
            }

            model.addAttribute(
                    "settings",
                    settings
            );

            model.addAttribute(
                    "segment",
                    dashboard.getSegment()
            );

            model.addAttribute(
                    "sexValues",
                    Sex.values()
            );

            model.addAttribute(
                    "regions",
                    customerService.findDistinctRegions()
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
        } else {
            model.addAttribute(
                    "settings",
                    new DashboardSettings()
            );

            model.addAttribute(
                    "segment",
                    new Segment()
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
                "importedFileName",
                user.getLastImportedFileName()
        );

        model.addAttribute(
                "sexValues",
                Sex.values()
        );

        model.addAttribute(
                "regions",
                customerService.findDistinctRegions()
        );

        model.addAttribute(
                "charts",
                dashboard.getCharts()
        );

        model.addAttribute(
                "dashboards",
                dashboardService.findAllByUser(user)
        );

        DashboardSettings settings = dashboard.getSettings();

        if (settings == null) {
            settings = new DashboardSettings();
        }

        model.addAttribute(
                "settings",
                settings
        );


        model.addAttribute(
                "segment",
                dashboard.getSegment()
        );

        model.addAttribute(
                "sexValues",
                Sex.values()
        );

        model.addAttribute(
                "regions",
                customerService.findDistinctRegions()
        );

        model.addAttribute(
                "stats",
                dashboardService.getStatistics(
                        dashboard
                )
        );

        model.addAttribute(
                "dashboardForm",
                new DashboardFormDto()
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

        DashboardSettings settings = new DashboardSettings();

        settings.setDashboard(dashboard);

        dashboardSettingsService.save(settings);

        dashboard.setSettings(settings);

        dashboardService.save(dashboard);

        return "redirect:/dashboards";
    }

    @GetMapping("/{id}/edit")
    public String editPage(
            @PathVariable Long id,
            Authentication authentication,
            Model model
    ) {

        User user = (User) authentication.getPrincipal();

        Dashboard dashboard =
                dashboardService.findByIdAndUser(
                        id,
                        user
                );

        DashboardFormDto form = new DashboardFormDto();

        form.setDashboardName(
                dashboard.getName()
        );

        form.setDashboardDescription(
                dashboard.getDescription()
        );

        if (dashboard.getSegment() != null) {

            Segment segment =
                    dashboard.getSegment();

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

        Dashboard dashboard = dashboardService.findByIdAndUser(
                id,
                user
        );

        dashboard.setName(
                form.getDashboardName()
        );

        dashboard.setDescription(
                form.getDashboardDescription()
        );

        Segment segment = dashboard.getSegment();

        if (segment != null) {

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

    @PostMapping("/{id}/settings")
    public String saveSettings(
            @PathVariable Long id,

            @RequestParam String dashboardName,
            @RequestParam(required = false) String dashboardDescription,

            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,

            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String region,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateOfRegistrationFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateOfRegistrationTo,

            @RequestParam(required = false) Integer countOfOrdersFrom,
            @RequestParam(required = false) Integer countOfOrdersTo,

            @RequestParam(required = false) Double averageCheckFrom,
            @RequestParam(required = false) Double averageCheckTo,

            @RequestParam(required = false) Double totalSpendsFrom,
            @RequestParam(required = false) Double totalSpendsTo,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate lastOrderDateFrom,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate lastOrderDateTo,

            @RequestParam Double abcCategoryABorder,
            @RequestParam Double abcCategoryBBorder,

            @RequestParam Double xyzCategoryXBorder,
            @RequestParam Double xyzCategoryYBorder,

            @RequestParam Integer recency5,
            @RequestParam Integer recency4,
            @RequestParam Integer recency3,
            @RequestParam Integer recency2,

            @RequestParam Integer frequency2,
            @RequestParam Integer frequency3,
            @RequestParam Integer frequency4,
            @RequestParam Integer frequency5,

            @RequestParam Double monetary2,
            @RequestParam Double monetary3,
            @RequestParam Double monetary4,
            @RequestParam Double monetary5,

            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        Dashboard dashboard =
                dashboardService.findByIdAndUser(
                        id,
                        user
                );

        dashboard.setName(dashboardName);
        dashboard.setDescription(dashboardDescription);

        dashboardService.save(dashboard);

//        Segment
        Segment segment = dashboard.getSegment();

        if (segment != null) {

            segment.setAgeFrom(ageFrom);
            segment.setAgeTo(ageTo);

            segment.setSex(sex);
            segment.setRegion(region);

            segment.setDateOfRegistrationFrom(
                    dateOfRegistrationFrom
            );

            segment.setDateOfRegistrationTo(
                    dateOfRegistrationTo
            );

            segment.setCountOfOrdersFrom(
                    countOfOrdersFrom
            );

            segment.setCountOfOrdersTo(
                    countOfOrdersTo
            );

            segment.setAverageCheckFrom(
                    averageCheckFrom
            );

            segment.setAverageCheckTo(
                    averageCheckTo
            );

            segment.setTotalSpendsFrom(
                    totalSpendsFrom
            );

            segment.setTotalSpendsTo(
                    totalSpendsTo
            );

            segment.setLastOrderDateFrom(
                    lastOrderDateFrom
            );

            segment.setLastOrderDateTo(
                    lastOrderDateTo
            );

            segmentService.save(segment);
        }

        DashboardSettings settings = dashboard.getSettings();

        if (settings == null) {
            settings = new DashboardSettings();
            settings.setDashboard(dashboard);

            dashboard.setSettings(settings);
        }

        settings.setAbcCategoryABorder(abcCategoryABorder);
        settings.setAbcCategoryBBorder(abcCategoryBBorder);

        settings.setXyzCategoryXBorder(xyzCategoryXBorder);
        settings.setXyzCategoryYBorder(xyzCategoryYBorder);

        settings.setRecency5(recency5);
        settings.setRecency4(recency4);
        settings.setRecency3(recency3);
        settings.setRecency2(recency2);

        settings.setFrequency2(frequency2);
        settings.setFrequency3(frequency3);
        settings.setFrequency4(frequency4);
        settings.setFrequency5(frequency5);

        settings.setMonetary2(monetary2);
        settings.setMonetary3(monetary3);
        settings.setMonetary4(monetary4);
        settings.setMonetary5(monetary5);

        dashboardSettingsService.save(settings);

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

        Dashboard dashboard = dashboardService.findByIdAndUser(
                id,
                user
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