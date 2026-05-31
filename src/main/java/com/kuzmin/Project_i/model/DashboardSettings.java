package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "dashboard_settings")
@Table(name = "dashboard_settings")
public class DashboardSettings {

    @Id
    @SequenceGenerator(name = "dashboard_settings_id_seq", sequenceName = "dashboard_settings_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_settings_id_seq")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", nullable = false, unique = true)
    private Dashboard dashboard;

    // ---------- ABC ----------

    @Column(name = "dashboard_settings_abc_category_a_border", nullable = false)
    private Double abcCategoryABorder = 80.0;

    @Column(name = "dashboard_settings_abc_category_b_border", nullable = false)
    private Double abcCategoryBBorder = 95.0;

    // ---------- XYZ ----------

    @Column(name = "dashboard_settings_xyz_category_x_border", nullable = false)
    private Double xyzCategoryXBorder = 10.0;

    @Column(name = "dashboard_settings_xyz_category_y_border", nullable = false)
    private Double xyzCategoryYBorder = 25.0;

    // ---------- R ----------

    @Column(name = "dashboard_settings_recency5", nullable = false)
    private Integer recency5 = 30;

    @Column(name = "dashboard_settings_recency4", nullable = false)
    private Integer recency4 = 90;

    @Column(name = "dashboard_settings_recency3", nullable = false)
    private Integer recency3 = 180;

    @Column(name = "dashboard_settings_recency2", nullable = false)
    private Integer recency2 = 365;

    // ---------- F ----------

    @Column(name = "dashboard_settings_frequency2", nullable = false)
    private Integer frequency2 = 5;

    @Column(name = "dashboard_settings_frequency3", nullable = false)
    private Integer frequency3 = 10;

    @Column(name = "dashboard_settings_frequency4", nullable = false)
    private Integer frequency4 = 20;

    @Column(name = "dashboard_settings_frequency5", nullable = false)
    private Integer frequency5 = 50;

    // ---------- M ----------

    @Column(name = "dashboard_settings_monetary2", nullable = false)
    private Double monetary2 = 500.0;

    @Column(name = "dashboard_settings_monetary3", nullable = false)
    private Double monetary3 = 2000.0;

    @Column(name = "dashboard_settings_monetary4", nullable = false)
    private Double monetary4 = 5000.0;

    @Column(name = "dashboard_settings_monetary5", nullable = false)
    private Double monetary5 = 10000.0;
}