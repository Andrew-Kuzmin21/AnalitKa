package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.DashboardSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DashboardSettingsRepository extends JpaRepository<DashboardSettings, Long> {

    Optional<DashboardSettings> findByDashboard(
            Dashboard dashboard
    );
}