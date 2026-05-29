package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.dto.DashboardStatisticsDto;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.RfmAnalyse;
import com.kuzmin.Project_i.model.Sex;
import com.kuzmin.Project_i.repository.CustomerRepository;
import com.kuzmin.Project_i.repository.RfmAnalyseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final RfmAnalyseRepository rfmAnalyseRepository;

    public DashboardStatisticsDto getStatistics() {

        List<Customer> customers =
                customerRepository.findAll();

        List<RfmAnalyse> rfmAnalyses =
                rfmAnalyseRepository.findAll();

        DashboardStatisticsDto dto =
                new DashboardStatisticsDto();

        dto.setTotalCustomers(
                (long) customers.size()
        );

        dto.setAverageAge(
                customers.stream()
                        .mapToInt(Customer::getAge)
                        .average()
                        .orElse(0)
        );

        BigDecimal averageCheck =
                customers.stream()
                        .map(Customer::getAverageCheck)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!customers.isEmpty()) {

            averageCheck =
                    averageCheck.divide(
                            BigDecimal.valueOf(customers.size()),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        dto.setAverageCheck(
                averageCheck
        );

        long maleCount =
                customers.stream()
                        .filter(c -> c.getSex() == Sex.м)
                        .count();

        long femaleCount =
                customers.stream()
                        .filter(c -> c.getSex() == Sex.ж)
                        .count();

        if (!customers.isEmpty()) {

            dto.setMalePercent(
                    maleCount * 100.0 / customers.size()
            );

            dto.setFemalePercent(
                    femaleCount * 100.0 / customers.size()
            );
        }

        Map<String, Long> regions =
                new LinkedHashMap<>();

        for (Customer customer : customers) {

            regions.put(
                    customer.getRegion(),
                    regions.getOrDefault(
                            customer.getRegion(),
                            0L
                    ) + 1
            );
        }

        dto.setCustomersByRegion(
                regions
        );

        Map<String, Long> ageGroups =
                new LinkedHashMap<>();

        ageGroups.put("18-25", 0L);
        ageGroups.put("26-35", 0L);
        ageGroups.put("36-45", 0L);
        ageGroups.put("46+", 0L);

        for (Customer customer : customers) {

            int age = customer.getAge();

            if (age <= 25) {

                ageGroups.put(
                        "18-25",
                        ageGroups.get("18-25") + 1
                );

            } else if (age <= 35) {

                ageGroups.put(
                        "26-35",
                        ageGroups.get("26-35") + 1
                );

            } else if (age <= 45) {

                ageGroups.put(
                        "36-45",
                        ageGroups.get("36-45") + 1
                );

            } else {

                ageGroups.put(
                        "46+",
                        ageGroups.get("46+") + 1
                );
            }
        }

        dto.setCustomersByAgeGroup(
                ageGroups
        );

        Map<String, Long> rfmSegments =
                new LinkedHashMap<>();

        for (RfmAnalyse analyse : rfmAnalyses) {

            String segment =
                    analyse.getRfmSegment().name();

            rfmSegments.put(
                    segment,
                    rfmSegments.getOrDefault(
                            segment,
                            0L
                    ) + 1
            );
        }

        dto.setRfmSegments(
                rfmSegments
        );

        return dto;
    }
}