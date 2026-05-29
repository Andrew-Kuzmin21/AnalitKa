package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.AbcAnalyse;
import com.kuzmin.Project_i.model.AbcCategory;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.repository.AbcAnalyseRepository;
import com.kuzmin.Project_i.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbcAnalyseService {

    private final CustomerRepository customerRepository;
    private final AbcAnalyseRepository abcAnalyseRepository;

    @Transactional
    public void runAnalysis(User user) {

        abcAnalyseRepository.deleteAllByCustomerUser(user);

        List<Customer> customers = customerRepository.findAllByUser(user);

        customers.sort(
                Comparator.comparing(
                        Customer::getTotalSpent
                ).reversed()
        );

        BigDecimal totalRevenue =
                customers.stream()
                        .map(Customer::getTotalSpent)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal cumulative = BigDecimal.ZERO;

        for (Customer customer : customers) {

            cumulative =
                    cumulative.add(
                            customer.getTotalSpent()
                    );

            double contributionPercent =
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

            AbcCategory category;

            if (contributionPercent <= 80) {
                category = AbcCategory.A;
            } else if (contributionPercent <= 95) {
                category = AbcCategory.B;
            } else {
                category = AbcCategory.C;
            }

            AbcAnalyse analyse = new AbcAnalyse();

            analyse.setCustomer(customer);

            analyse.setAbcCategory(category);

            analyse.setContributionPercent(contributionPercent);

            analyse.setCustomerRevenue(customer.getTotalSpent().doubleValue());

            analyse.setCreationDate(LocalDateTime.now());

            abcAnalyseRepository.save(analyse);
        }
    }

    public List<AbcAnalyse> findAll() {
        return abcAnalyseRepository.findAll();
    }

}