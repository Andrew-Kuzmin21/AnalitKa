package com.kuzmin.Project_i.service;

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
    private final RfmAnalyseRepository rfmAnalyseRepository;
    private final AbcAnalyseRepository abcAnalyseRepository;
    private final XyzAnalyseRepository xyzAnalyseRepository;
    private final AbcXyzMatrixRepository abcXyzMatrixRepository;

    public DashboardStatisticsDto getStatistics(
            Dashboard dashboard
    ) {

        List<Customer> customers;

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

        List<RfmAnalyse> rfmAnalyses =
                rfmAnalyseRepository.findAllByCustomerIn(
                        customers
                );

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

        Map<String, Long> rfmSegments = new LinkedHashMap<>();

        for (RfmAnalyse analyse : rfmAnalyses) {
            String segment = analyse.getRfmSegment().name();

            rfmSegments.put(
                    segment,
                    rfmSegments.getOrDefault(
                            segment,
                            0L
                    ) + 1
            );
        }

        Map<String, Long> abcCategories = new LinkedHashMap<>();

        abcCategories.put("A", 0L);
        abcCategories.put("B", 0L);
        abcCategories.put("C", 0L);

        abcAnalyseRepository.findAllByCustomerIn(
                        customers
                )
                .forEach(analyse -> {

                    String category =
                            analyse.getAbcCategory().name();

                    abcCategories.put(
                            category,
                            abcCategories.get(category) + 1
                    );
                });


        dto.setAbcCategories(abcCategories);

        Map<String, Long> xyzCategories = new LinkedHashMap<>();

        xyzCategories.put("X", 0L);
        xyzCategories.put("Y", 0L);
        xyzCategories.put("Z", 0L);

        xyzAnalyseRepository.findAllByCustomerIn(
                        customers
                )
                .forEach(analyse -> {

                    String category =
                            analyse.getXyzCategory().name();

                    xyzCategories.put(
                            category,
                            xyzCategories.get(category) + 1
                    );
                });

        dto.setXyzCategories(xyzCategories);

        Map<String, Long> matrixGroups = new LinkedHashMap<>();

        abcXyzMatrixRepository.findAllByCustomerIn(
                        customers
                )
                .forEach(matrix -> {

                    String group =
                            matrix.getMatrixGroup();

                    matrixGroups.put(
                            group,
                            matrixGroups.getOrDefault(
                                    group,
                                    0L
                            ) + 1
                    );
                });

        dto.setAbcXyzGroups(matrixGroups);
        dto.setRfmSegments(rfmSegments);

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

        List<Customer> customers =
                customerRepository.findAllByUser(user);

        return customers.stream()

                .filter(customer ->
                        segment.getAgeFrom() == null
                                || customer.getAge() >= segment.getAgeFrom()
                )

                .filter(customer ->
                        segment.getAgeTo() == null
                                || customer.getAge() <= segment.getAgeTo()
                )

                .filter(customer ->
                        segment.getSex() == null
                                || segment.getSex().isBlank()
                                || customer.getSex().name()
                                .equals(segment.getSex())
                )

                .filter(customer ->
                        segment.getRegion() == null
                                || segment.getRegion().isBlank()
                                || customer.getRegion()
                                .equalsIgnoreCase(segment.getRegion())
                )

                .filter(customer ->
                        segment.getDateOfRegistrationFrom() == null
                                || !customer.getDateOfRegistration()
                                .isBefore(
                                        segment.getDateOfRegistrationFrom()
                                )
                )

                .filter(customer ->
                        segment.getDateOfRegistrationTo() == null
                                || !customer.getDateOfRegistration()
                                .isAfter(
                                        segment.getDateOfRegistrationTo()
                                )
                )

                .filter(customer ->
                        segment.getCountOfOrdersFrom() == null
                                || customer.getCountOfOrders()
                                >= segment.getCountOfOrdersFrom()
                )

                .filter(customer ->
                        segment.getCountOfOrdersTo() == null
                                || customer.getCountOfOrders()
                                <= segment.getCountOfOrdersTo()
                )

                .filter(customer ->
                        segment.getAverageCheckFrom() == null
                                || customer.getAverageCheck().doubleValue()
                                >= segment.getAverageCheckFrom()
                )

                .filter(customer ->
                        segment.getAverageCheckTo() == null
                                || customer.getAverageCheck().doubleValue()
                                <= segment.getAverageCheckTo()
                )

                .filter(customer ->
                        segment.getTotalSpendsFrom() == null
                                || customer.getTotalSpent().doubleValue()
                                >= segment.getTotalSpendsFrom()
                )

                .filter(customer ->
                        segment.getTotalSpendsTo() == null
                                || customer.getTotalSpent().doubleValue()
                                <= segment.getTotalSpendsTo()
                )

                .filter(customer ->
                        segment.getLastOrderDateFrom() == null
                                || (
                                customer.getLastOrderDate() != null
                                        &&
                                        !customer.getLastOrderDate()
                                                .isBefore(
                                                        segment.getLastOrderDateFrom()
                                                )
                        )
                )

                .filter(customer ->
                        segment.getLastOrderDateTo() == null
                                || (
                                customer.getLastOrderDate() != null
                                        &&
                                        !customer.getLastOrderDate()
                                                .isAfter(
                                                        segment.getLastOrderDateTo()
                                                )
                        )
                )

                .toList();
    }

}