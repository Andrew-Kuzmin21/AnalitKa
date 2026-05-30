package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class SexFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getSex() == null || segment.getSex().isBlank()) {
            return true;
        }

        return segment.getSex().equals(
                customer.getSex().name()
        );
    }
}