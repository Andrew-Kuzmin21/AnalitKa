package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.DashboardSettings;
import com.kuzmin.Project_i.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DashboardSettingsRepository extends JpaRepository<DashboardSettings, Long> {

    Optional<DashboardSettings> findByDashboard(
            Dashboard dashboard
    );

    @Transactional
    @Modifying
    @Query("""
        delete from dashboard_settings ds
        where ds.dashboard.user = :user
    """)
    void deleteAllByUser(User user);
}