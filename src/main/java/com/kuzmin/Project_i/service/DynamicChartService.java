package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.dto.ChartDataDto;
import com.kuzmin.Project_i.model.AggregationType;
import com.kuzmin.Project_i.model.Chart;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DynamicChartService {

    private final CustomerRepository customerRepository;

    public ChartDataDto buildChart(Chart chart) {

        List<Customer> customers = customerRepository.findAll();

        Map<String, Double> data = new LinkedHashMap<>();

        for (Customer customer : customers) {

            String key =
                    extractKey(
                            customer,
                            chart.getChartAxisX()
                    );

            double value =
                    extractValue(
                            customer,
                            chart.getChartAxisY()
                    );

            if (chart.getChartAggregationType() == AggregationType.COUNT) {
                data.put(key, data.getOrDefault(key, 0.0) + 1);
            } else if (chart.getChartAggregationType() == AggregationType.SUM) {
                data.put(key, data.getOrDefault(key, 0.0) + value);
            } else {
                data.put(key, data.getOrDefault(key, 0.0) + value);
            }
        }

        if (chart.getChartAggregationType() == AggregationType.AVG) {
            Map<String, Integer> counts = new HashMap<>();

            for (Customer customer : customers) {
                String key =
                        extractKey(
                                customer,
                                chart.getChartAxisX()
                        );

                counts.put(key, counts.getOrDefault(key, 0) + 1);
            }

            for (String key : data.keySet()) {
                data.put(key, data.get(key) / counts.get(key));
            }
        }

        ChartDataDto dto = new ChartDataDto();

        dto.setChartName(chart.getChartName());

        dto.setChartType(chart.getChartType().name());

        dto.setLabels(
                new ArrayList<>(data.keySet())
        );

        dto.setValues(
                new ArrayList<>(data.values())
        );

        return dto;
    }

    private String extractKey(
            Customer customer,
            String axis
    ) {

        return switch (axis) {
            case "region" -> customer.getRegion();

            case "sex" -> customer.getSex().name();

            case "ageGroup" -> buildAgeGroup(customer.getAge());

            default -> "UNKNOWN";
        };
    }

    private double extractValue(
            Customer customer,
            String axis
    ) {

        return switch (axis) {
            case "totalSpent" -> customer.getTotalSpent().doubleValue();

            case "averageCheck" -> customer.getAverageCheck().doubleValue();

            case "countOfOrders" -> customer.getCountOfOrders();

            default -> 1;
        };
    }

    private String buildAgeGroup(
            int age
    ) {

        if (age <= 25) {
            return "18-25";
        }

        if (age <= 35) {
            return "26-35";
        }

        if (age <= 45) {
            return "36-45";
        }

        return "46+";
    }
}
