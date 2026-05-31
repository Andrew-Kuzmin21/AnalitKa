package com.kuzmin.Project_i.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DashboardFormDto {

    // Dashboard
    private String dashboardName;

    private String dashboardDescription;

    // Segment
    private String segmentName;

    private Integer ageFrom;
    private Integer ageTo;

    private String sex;

    private String region;

    private LocalDate dateOfRegistrationFrom;
    private LocalDate dateOfRegistrationTo;

    private Integer countOfOrdersFrom;
    private Integer countOfOrdersTo;

    private Double averageCheckFrom;
    private Double averageCheckTo;

    private Double totalSpendsFrom;
    private Double totalSpendsTo;

    private LocalDate lastOrderDateFrom;
    private LocalDate lastOrderDateTo;

    // ABC
    private Double abcCategoryABorder;
    private Double abcCategoryBBorder;

    // XYZ
    private Double xyzCategoryXBorder;
    private Double xyzCategoryYBorder;

    // R
    private Integer recency5;
    private Integer recency4;
    private Integer recency3;
    private Integer recency2;

    // F
    private Integer frequency2;
    private Integer frequency3;
    private Integer frequency4;
    private Integer frequency5;

    // M
    private Double monetary2;
    private Double monetary3;
    private Double monetary4;
    private Double monetary5;

}