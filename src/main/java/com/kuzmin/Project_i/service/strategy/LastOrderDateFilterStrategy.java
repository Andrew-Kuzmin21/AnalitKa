package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class LastOrderDateFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getLastOrderDateFrom() != null
                && customer.getLastOrderDate()
                .isBefore(segment.getLastOrderDateFrom())) {
            return false;
        }

        if (segment.getLastOrderDateTo() != null
                && customer.getLastOrderDate()
                .isAfter(segment.getLastOrderDateTo())) {
            return false;
        }

        return true;
    }
}