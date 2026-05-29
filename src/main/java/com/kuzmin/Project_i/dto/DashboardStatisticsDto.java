package com.kuzmin.Project_i.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class DashboardStatisticsDto {

    private Long totalCustomers;
    private Double averageAge;
    private BigDecimal averageCheck;
    private Double malePercent;
    private Double femalePercent;

    private Map<String, Long> customersByRegion;
    private Map<String, Long> customersByAgeGroup;
    private Map<String, Long> rfmSegments;
    private Map<String, Long> abcCategories;
    private Map<String, Long> xyzCategories;
    private Map<String, Long> abcXyzGroups;

}