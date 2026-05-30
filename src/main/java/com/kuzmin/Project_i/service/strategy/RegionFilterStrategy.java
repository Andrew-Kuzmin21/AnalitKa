package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class RegionFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getRegion() == null || segment.getRegion().isBlank()) {
            return true;
        }

        return segment.getRegion()
                .equalsIgnoreCase(
                        customer.getRegion()
                );
    }

}