package com.kuzmin.Project_i.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

}