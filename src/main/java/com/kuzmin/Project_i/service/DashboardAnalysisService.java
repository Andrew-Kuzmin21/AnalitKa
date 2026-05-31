package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.DashboardSettings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DashboardAnalysisService {

    public Map<String, Long> calculateAbc(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        if (settings == null) {
            settings = new DashboardSettings();
        }

        Map<String, Long> result = new LinkedHashMap<>();

        result.put("A", 0L);
        result.put("B", 0L);
        result.put("C", 0L);

        if (customers.isEmpty()) {
            return result;
        }

        List<Customer> sortedCustomers = new ArrayList<>(customers);

        sortedCustomers.sort(
                Comparator.comparing(
                        Customer::getTotalSpent
                ).reversed()
        );

        BigDecimal totalRevenue =
                sortedCustomers.stream()
                        .map(Customer::getTotalSpent)
                        .filter(Objects::nonNull)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        if (totalRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return result;
        }

        BigDecimal cumulative = BigDecimal.ZERO;

        for (Customer customer : sortedCustomers) {

            cumulative =
                    cumulative.add(
                            customer.getTotalSpent()
                    );

            double percent =
                    cumulative
                            .multiply(
                                    BigDecimal.valueOf(100)
                            )
                            .divide(
                                    totalRevenue,
                                    2,
                                    RoundingMode.HALF_UP
                            )
                            .doubleValue();

            if (percent <= settings.getAbcCategoryABorder()) {
                result.put(
                        "A",
                        result.get("A") + 1
                );

            } else if (percent <= settings.getAbcCategoryBBorder()) {
                result.put(
                        "B",
                        result.get("B") + 1
                );

            } else {
                result.put(
                        "C",
                        result.get("C") + 1
                );
            }
        }

        return result;
    }

    public Map<String, Long> calculateXyz(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        if (settings == null) {
            settings = new DashboardSettings();
        }

        Map<String, Long> result = new LinkedHashMap<>();

        result.put("X", 0L);
        result.put("Y", 0L);
        result.put("Z", 0L);

        for (Customer customer : customers) {

            if (customer.getCountOfOrders() == null || customer.getCountOfOrders() == 0) {
                continue;
            }

            double variation = 100.0 / customer.getCountOfOrders();

            if (variation <= settings.getXyzCategoryXBorder()) {
                result.put(
                        "X",
                        result.get("X") + 1
                );

            } else if (variation <= settings.getXyzCategoryYBorder()) {
                result.put(
                        "Y",
                        result.get("Y") + 1
                );

            } else {
                result.put(
                        "Z",
                        result.get("Z") + 1
                );
            }
        }

        return result;
    }

    public Map<String, Long> calculateRfm(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        if (settings == null) {
            settings = new DashboardSettings();
        }

        Map<String, Long> result = new LinkedHashMap<>();

        result.put("VIP", 0L);
        result.put("ACTIVE", 0L);
        result.put("LOST", 0L);

        for (Customer customer : customers) {
            if (customer.getLastOrderDate() == null) {
                continue;
            }

            long recencyDays =
                    ChronoUnit.DAYS.between(
                            customer.getLastOrderDate(),
                            LocalDate.now()
                    );

            int r =
                    calculateRecencyScore(
                            recencyDays,
                            settings
                    );

            int f =
                    calculateFrequencyScore(
                            customer.getCountOfOrders(),
                            settings
                    );

            int m =
                    calculateMonetaryScore(
                            customer.getTotalSpent()
                                    .doubleValue(),
                            settings
                    );

            String segment;

            if (r >= 4 && f >= 4 && m >= 4) {
                segment = "VIP";
            } else if (r <= 2 && f <= 2) {
                segment = "LOST";
            } else {
                segment = "ACTIVE";
            }

            result.put(
                    segment,
                    result.get(segment) + 1
            );
        }

        return result;
    }

    public Map<String, Long> calculateAbcXyz(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        if (settings == null) {
            settings = new DashboardSettings();
        }

        Map<String, Long> result = new LinkedHashMap<>();

        String[] groups = {
                "AX","AY","AZ",
                "BX","BY","BZ",
                "CX","CY","CZ"
        };

        for (String group : groups) {
            result.put(group, 0L);
        }

        Map<Customer, String> abcMap =
                calculateAbcByCustomer(
                        customers,
                        settings
                );

        Map<Customer, String> xyzMap =
                calculateXyzByCustomer(
                        customers,
                        settings
                );

        for (Customer customer : customers) {
            String abc = abcMap.get(customer);
            String xyz = xyzMap.get(customer);

            if (abc == null || xyz == null) {
                continue;
            }

            String group = abc + xyz;

            result.put(
                    group,
                    result.get(group) + 1
            );
        }

        return result;
    }

    private int calculateRecencyScore(
            long days,
            DashboardSettings settings
    ) {
        if (days <= settings.getRecency5()) return 5;
        if (days <= settings.getRecency4()) return 4;
        if (days <= settings.getRecency3()) return 3;
        if (days <= settings.getRecency2()) return 2;

        return 1;
    }

    private int calculateFrequencyScore(
            int frequency,
            DashboardSettings settings
    ) {
        if (frequency >= settings.getFrequency5()) return 5;
        if (frequency >= settings.getFrequency4()) return 4;
        if (frequency >= settings.getFrequency3()) return 3;
        if (frequency >= settings.getFrequency2()) return 2;

        return 1;
    }

    private int calculateMonetaryScore(
            double monetary,
            DashboardSettings settings
    ) {
        if (monetary >= settings.getMonetary5()) return 5;
        if (monetary >= settings.getMonetary4()) return 4;
        if (monetary >= settings.getMonetary3()) return 3;
        if (monetary >= settings.getMonetary2()) return 2;

        return 1;
    }

    private Map<Customer, String> calculateAbcByCustomer(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        Map<Customer, String> result = new HashMap<>();

        List<Customer> sorted = new ArrayList<>(customers);

        sorted.sort(
                Comparator.comparing(
                        Customer::getTotalSpent
                ).reversed()
        );

        BigDecimal totalRevenue =
                sorted.stream()
                        .map(Customer::getTotalSpent)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        if (totalRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return result;
        }

        BigDecimal cumulative = BigDecimal.ZERO;

        for (Customer customer : sorted) {

            cumulative =
                    cumulative.add(
                            customer.getTotalSpent()
                    );

            double percent =
                    cumulative
                            .multiply(
                                    BigDecimal.valueOf(100)
                            )
                            .divide(
                                    totalRevenue,
                                    2,
                                    RoundingMode.HALF_UP
                            )
                            .doubleValue();

            if (percent <= settings.getAbcCategoryABorder()) {
                result.put(customer, "A");
            } else if (
                    percent <= settings.getAbcCategoryBBorder()
            ) {
                result.put(customer, "B");
            } else {
                result.put(customer, "C");
            }
        }

        return result;
    }

    private Map<Customer, String> calculateXyzByCustomer(
            List<Customer> customers,
            DashboardSettings settings
    ) {

        Map<Customer, String> result = new HashMap<>();

        for (Customer customer : customers) {

            if (customer.getCountOfOrders() == null
                    || customer.getCountOfOrders() == 0) {
                continue;
            }

            double variation = 100.0 / customer.getCountOfOrders();

            if (variation <= settings.getXyzCategoryXBorder()) {
                result.put(customer, "X");

            } else if (variation <= settings.getXyzCategoryYBorder()
            ) {
                result.put(customer, "Y");

            } else {
                result.put(customer, "Z");
            }
        }

        return result;
    }
}