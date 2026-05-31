package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.dto.DashboardFormDto;
import com.kuzmin.Project_i.dto.DashboardStatisticsDto;
import com.kuzmin.Project_i.model.*;
import com.kuzmin.Project_i.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CustomerRepository customerRepository;
    private final DashboardRepository dashboardRepository;

    private final SegmentFilteringService segmentFilteringService;
    private final DashboardAnalysisService dashboardAnalysisService;

    public DashboardStatisticsDto getStatistics(
            Dashboard dashboard
    ) {

        List<Customer> customers;
        DashboardSettings settings = dashboard.getSettings();

        if (settings == null) {
            settings = new DashboardSettings();
        }

        if (dashboard.getSegment() == null) {
            customers =
                    customerRepository.findAllByUser(
                            dashboard.getUser()
                    );

        } else {
            customers =
                    getCustomersBySegment(
                            dashboard.getSegment(),
                            dashboard.getUser()
                    );
        }

        DashboardStatisticsDto dto = new DashboardStatisticsDto();

        dto.setTotalCustomers((long) customers.size());

        dto.setAverageAge(
                customers.stream()
                        .mapToInt(Customer::getAge)
                        .average()
                        .orElse(0)
        );

        BigDecimal averageCheck =
                customers.stream()
                        .map(Customer::getAverageCheck)
                        .filter(java.util.Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!customers.isEmpty()) {
            averageCheck =
                    averageCheck.divide(
                            BigDecimal.valueOf(customers.size()),
                            2,
                            RoundingMode.HALF_UP
                    );
        }

        dto.setAverageCheck(averageCheck);

        long maleCount =
                customers.stream()
                        .filter(c -> c.getSex() == Sex.м)
                        .count();

        long femaleCount =
                customers.stream()
                        .filter(c -> c.getSex() == Sex.ж)
                        .count();

        if (!customers.isEmpty()) {
            dto.setMalePercent(maleCount * 100.0 / customers.size());
            dto.setFemalePercent(femaleCount * 100.0 / customers.size());
        }

        Map<String, Long> regions = new LinkedHashMap<>();

        for (Customer customer : customers) {
            regions.put(
                    customer.getRegion(),
                    regions.getOrDefault(
                            customer.getRegion(),
                            0L
                    ) + 1
            );
        }

        dto.setCustomersByRegion(regions);

        Map<String, Long> ageGroups = new LinkedHashMap<>();

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

        dto.setCustomersByAgeGroup(ageGroups);


        Map<String, Long> sexGroups = new LinkedHashMap<>();
        sexGroups.put("Male", maleCount);
        sexGroups.put("Female", femaleCount);
        dto.setCustomersBySex(sexGroups);

        Map<String, Long> ordersGroups = new LinkedHashMap<>();

        ordersGroups.put("0-5", 0L);
        ordersGroups.put("6-10", 0L);
        ordersGroups.put("11-20", 0L);
        ordersGroups.put("20+", 0L);

        for (Customer customer : customers) {

            Integer orders = customer.getCountOfOrders();

            if (orders == null) {
                continue;
            }

            if (orders <= 5) {
                ordersGroups.put("0-5", ordersGroups.get("0-5") + 1);
            } else if (orders <= 10) {
                ordersGroups.put("6-10", ordersGroups.get("6-10") + 1);
            } else if (orders <= 20) {
                ordersGroups.put("11-20", ordersGroups.get("11-20") + 1);
            } else {
                ordersGroups.put("20+", ordersGroups.get("20+") + 1);
            }
        }

        dto.setCustomersByOrders(ordersGroups);

        Map<String, Long> averageCheckGroups = new LinkedHashMap<>();

        averageCheckGroups.put("<1000", 0L);
        averageCheckGroups.put("1000-3000", 0L);
        averageCheckGroups.put("3000-5000", 0L);
        averageCheckGroups.put("5000+", 0L);

        for (Customer customer : customers) {

            if (customer.getAverageCheck() == null) {
                continue;
            }

            BigDecimal check = customer.getAverageCheck();

            if (check.compareTo(BigDecimal.valueOf(1000)) < 0) {
                averageCheckGroups.put("<1000",
                        averageCheckGroups.get("<1000") + 1);
            }
            else if (check.compareTo(BigDecimal.valueOf(3000)) < 0) {
                averageCheckGroups.put("1000-3000",
                        averageCheckGroups.get("1000-3000") + 1);
            }
            else if (check.compareTo(BigDecimal.valueOf(5000)) < 0) {
                averageCheckGroups.put("3000-5000",
                        averageCheckGroups.get("3000-5000") + 1);
            }
            else {
                averageCheckGroups.put("5000+",
                        averageCheckGroups.get("5000+") + 1);
            }
        }

        dto.setCustomersByAverageCheck(averageCheckGroups);

        Map<String, Long> spendGroups = new LinkedHashMap<>();

        spendGroups.put("<10000", 0L);
        spendGroups.put("10000-50000", 0L);
        spendGroups.put("50000-100000", 0L);
        spendGroups.put("100000+", 0L);

        for (Customer customer : customers) {

            if (customer.getTotalSpent() == null) {
                continue;
            }


            BigDecimal spends = customer.getTotalSpent();

            if (spends.compareTo(BigDecimal.valueOf(10000)) < 0) {

                spendGroups.put(
                        "<10000",
                        spendGroups.get("<10000") + 1
                );

            } else if (spends.compareTo(BigDecimal.valueOf(50000)) < 0) {

                spendGroups.put(
                        "10000-50000",
                        spendGroups.get("10000-50000") + 1
                );

            } else if (spends.compareTo(BigDecimal.valueOf(100000)) < 0) {

                spendGroups.put(
                        "50000-100000",
                        spendGroups.get("50000-100000") + 1
                );

            } else {

                spendGroups.put(
                        "100000+",
                        spendGroups.get("100000+") + 1
                );
            }
        }

        dto.setCustomersByTotalSpends(spendGroups);



        dto.setAbcCategories(
                dashboardAnalysisService.calculateAbc(
                        customers,
                        settings
                )
        );

        dto.setXyzCategories(
                dashboardAnalysisService.calculateXyz(
                        customers,
                        settings
                )
        );

        dto.setRfmSegments(
                dashboardAnalysisService.calculateRfm(
                        customers,
                        settings
                )
        );

        dto.setAbcXyzGroups(
                dashboardAnalysisService.calculateAbcXyz(
                        customers,
                        settings
                )
        );

        return dto;
    }

    public List<Dashboard> findAll() {
        return dashboardRepository.findAll();
    }

    public Dashboard findById(Long id) {
        return dashboardRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Dashboard not found")
                );
    }

    public Dashboard save(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

    public void deleteById(Long id) {
        dashboardRepository.deleteById(id);
    }

    public List<Dashboard> findAllByUser(User user) {
        return dashboardRepository.findAllByUser(user);
    }

    public Dashboard findByIdAndUser(
            Long id,
            User user
    ) {

        return dashboardRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Dashboard not found"));
    }

    private List<Customer> getCustomersBySegment(
            Segment segment,
            User user
    ) {

        List<Customer> customers = customerRepository.findAllByUser(user);

        return segmentFilteringService
                .filterCustomers(
                        customers,
                        segment
                );
    }

    public Dashboard update(
            Dashboard dashboard,
            DashboardFormDto form
    ) {

        dashboard.setName(
                form.getDashboardName()
        );

        dashboard.setDescription(
                form.getDashboardDescription()
        );

        return dashboardRepository.save(
                dashboard
        );
    }

}