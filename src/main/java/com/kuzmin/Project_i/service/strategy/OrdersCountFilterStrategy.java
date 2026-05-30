package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;
import org.springframework.stereotype.Component;

@Component
public class OrdersCountFilterStrategy implements CustomerFilterStrategy {

    @Override
    public boolean matches(
            Customer customer,
            Segment segment
    ) {

        if (segment.getCountOfOrdersFrom() != null
                && customer.getCountOfOrders()
                < segment.getCountOfOrdersFrom()) {
            return false;
        }

        if (segment.getCountOfOrdersTo() != null
                && customer.getCountOfOrders()
                > segment.getCountOfOrdersTo()) {
            return false;
        }

        return true;
    }
}