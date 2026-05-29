package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.repository.DashboardRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

    public Dashboard save(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    public @Nullable Dashboard findById(Long id) {
        return dashboardRepository.findById(id).orElse(null);
    }
}