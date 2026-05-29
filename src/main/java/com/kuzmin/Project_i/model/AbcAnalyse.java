package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Entity(name = "abc_analysis")
@Table(name = "abc_analysis")
public class AbcAnalyse {

    @Id
    @SequenceGenerator(name = "abc_analyse_id_seq", sequenceName = "abc_analyse_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abc_analyse_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_analyse_category", nullable = false)
    private AbcCategory abcCategory;

    @Column(name = "abc_analyse_contribution_percent")
    private Double contributionPercent;

    @Column(name = "abc_analyse_customer_revenue")
    private Double customerRevenue;

    @Column(name = "abc_analyse_creation_date", nullable = false)
    private LocalDateTime creationDate;

}
