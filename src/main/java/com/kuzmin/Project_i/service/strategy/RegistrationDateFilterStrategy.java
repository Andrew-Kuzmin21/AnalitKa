package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDateFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getDateOfRegistrationFrom() != null
                && customer.getDateOfRegistration()
                .isBefore(segment.getDateOfRegistrationFrom())) {
            return false;
        }

        if (segment.getDateOfRegistrationTo() != null
                && customer.getDateOfRegistration()
                .isAfter(segment.getDateOfRegistrationTo())) {
            return false;
        }

        return true;
    }
}