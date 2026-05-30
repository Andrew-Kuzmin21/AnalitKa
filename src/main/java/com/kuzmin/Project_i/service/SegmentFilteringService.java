package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.service.strategy.CustomerFilterStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SegmentFilteringService {

    private final List<CustomerFilterStrategy> strategies;

    public List<Customer> filterCustomers(
            List<Customer> customers,
            Segment segment
    ) {

        return customers.stream()
                .filter(customer ->
                        strategies.stream()
                                .allMatch(strategy ->
                                        strategy.matches(
                                                customer,
                                                segment
                                        )
                                )
                )
                .toList();
    }
}