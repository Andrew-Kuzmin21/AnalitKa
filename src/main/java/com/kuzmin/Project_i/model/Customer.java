package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "customers")
@Table(name = "customers")
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
    private Long id;

    @Column(name = "customer_age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "customer_sex", nullable = false)
    private Sex sex;

    @Column(name = "customer_region", nullable = false)
    private String region;

    @Column(name = "customer_date_of_registration", nullable = false)
    private LocalDate dateOfRegistration;

    @Column(name = "customer_count_of_orders", nullable = false)
    private Integer countOfOrders;

    @Column(name = "customer_average_check", nullable = false)
    private BigDecimal averageCheck;

    @Column(name = "customer_total_spends", nullable = false)
    private BigDecimal totalSpent;

    @Column(name = "customer_last_order_date")
    private LocalDate lastOrderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "customer")
    private List<AbcAnalyse> abcAnalyses;

    @OneToMany(mappedBy = "customer")
    private List<XyzAnalyse> xyzAnalyses;

    @OneToMany(mappedBy = "customer")
    private List<RfmAnalyse> rfmAnalyses;
}