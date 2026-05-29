package com.kuzmin.Project_i.dto;

import com.kuzmin.Project_i.model.Sex;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerImportDto {

    private Integer age;

    private Sex sex;

    private String region;

    private LocalDate dateOfRegistration;

    private Integer countOfOrders;

    private BigDecimal averageCheck;

    private BigDecimal totalSpends;

    private LocalDate lastOrderDate;
}