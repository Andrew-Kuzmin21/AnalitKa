package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
@Entity(name = "rfm_analysis")
@Table(name = "rfm_analysis")
public class RfmAnalyse {

    @Id
    @SequenceGenerator(name = "rfm_analyse_id_seq", sequenceName = "rfm_analyse_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rfm_analyse_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "rfm_analyse_recency", nullable = false)
    private Double recency;

    @Column(name = "rfm_analyse_frequency", nullable = false)
    private Double frequency;

    @Column(name = "rfm_analyse_monetary", nullable = false)
    private Double monetary;

    @Column(name = "rfm_analyse_r_score", nullable = false)
    private Integer rScore;

    @Column(name = "rfm_analyse_f_score", nullable = false)
    private Integer fScore;

    @Column(name = "rfm_analyse_m_score", nullable = false)
    private Integer mScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "rfm_analyse_segment", nullable = false)
    private RfmSegment rfmSegment;

    @Column(name = "rfm_analyse_creation_date", nullable = false)
    private LocalDateTime creationDate;

}
