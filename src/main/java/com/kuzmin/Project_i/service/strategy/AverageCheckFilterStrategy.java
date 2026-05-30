package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AverageCheckFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getAverageCheckFrom() != null
                && customer.getAverageCheck().compareTo(
                BigDecimal.valueOf(segment.getAverageCheckFrom())
        ) < 0) {
            return false;
        }

        if (segment.getAverageCheckTo() != null
                && customer.getAverageCheck().compareTo(
                BigDecimal.valueOf(segment.getAverageCheckTo())
        ) > 0) {
            return false;
        }

        return true;
    }
}