package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class AgeFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getAgeFrom() != null
                && customer.getAge() < segment.getAgeFrom()) {
            return false;
        }

        if (segment.getAgeTo() != null
                && customer.getAge() > segment.getAgeTo()) {
            return false;
        }

        return true;
    }
}