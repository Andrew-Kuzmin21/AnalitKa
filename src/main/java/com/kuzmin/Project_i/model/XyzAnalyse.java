package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Entity(name = "xyz_analysis")
@Table(name = "xyz_analysis")
public class XyzAnalyse {

    @Id
    @SequenceGenerator(name = "xyz_analyse_id_seq", sequenceName = "xyz_analyse_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xyz_analyse_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "xyz_analyse_category", nullable = false)
    private XyzCategory xyzCategory;

    @Column(name = "xyz_analyse_variation_coefficient")
    private Double variationCoefficient;

    @Column(name = "xyz_analyse_purchase_stability")
    private String purchaseStability;

    @Column(name = "xyz_analyse_creation_date", nullable = false)
    private LocalDateTime creationDate;

}
