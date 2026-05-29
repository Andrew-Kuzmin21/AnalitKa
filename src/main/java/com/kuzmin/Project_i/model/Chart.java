package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@Entity(name = "charts")
@Table(name = "charts")
public class Chart {

    @Id
    @SequenceGenerator(name = "chart_id_seq", sequenceName = "chart_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chart_id_seq")
    private Long id;

    @Column(name = "chart_name", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", nullable = false)
    private Dashboard dashboard;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_type", nullable = false)
    private ChartType chartType;

    @Column(name = "chart_axis_x", nullable = false)
    private String axisX;

    @Column(name = "chart_axis_y")
    private String axisY;

    @Enumerated(EnumType.STRING)
    @Column(name = "chart_aggregation_type", nullable = false)
    private AggregationType aggregationType;

    @Column(name = "chart_position_x")
    private Integer positionX;

    @Column(name = "chart_position_y")
    private Integer positionY;

    @Column(name = "chart_width")
    private Integer width;

    @Column(name = "chart_height")
    private Integer height;

}
