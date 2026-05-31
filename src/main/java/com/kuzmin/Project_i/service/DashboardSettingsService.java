package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.DashboardSettings;
import com.kuzmin.Project_i.repository.DashboardSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardSettingsService {

    private final DashboardSettingsRepository repository;

    public DashboardSettings getOrCreate(
            Dashboard dashboard
    ) {

        return repository.findByDashboard(dashboard)
                .orElseGet(() -> {

                    DashboardSettings settings = new DashboardSettings();

                    settings.setDashboard(dashboard);

                    return repository.save(settings);
                });
    }

    public DashboardSettings save(
            DashboardSettings settings
    ) {
        return repository.save(settings);
    }
}