package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TotalSpendsFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getTotalSpendsFrom() != null
                && customer.getTotalSpent().compareTo(
                BigDecimal.valueOf(segment.getTotalSpendsFrom())
        ) < 0) {
            return false;
        }

        if (segment.getTotalSpendsTo() != null
                && customer.getTotalSpent().compareTo(
                BigDecimal.valueOf(segment.getTotalSpendsTo())
        ) > 0) {
            return false;
        }

        return true;
    }

}