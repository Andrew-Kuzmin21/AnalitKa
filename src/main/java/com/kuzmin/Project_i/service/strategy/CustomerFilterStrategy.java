package com.kuzmin.Project_i.service.strategy;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Segment;


public interface CustomerFilterStrategy {

    boolean matches(
            Customer customer,
            Segment segment
    );

}
