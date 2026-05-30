package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "segments")
@Table(name = "segments")
public class Segment {

    @Id
    @SequenceGenerator(name = "segment_id_seq", sequenceName = "segment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segment_id_seq")
    private Long id;

    @Column(name = "segment_name", nullable = false)
    private String name;

    @Column(name = "segment_age_from")
    private Integer ageFrom;

    @Column(name = "segment_age_to")
    private Integer ageTo;

    @Column(name = "segment_sex")
    private String sex;

    @Column(name = "segment_region")
    private String region;

    @Column(name = "segment_date_of_registration_from")
    private LocalDate dateOfRegistrationFrom;

    @Column(name = "segment_date_of_registration_to")
    private LocalDate dateOfRegistrationTo;

    @Column(name = "segment_count_of_orders_from")
    private Integer countOfOrdersFrom;

    @Column(name = "segment_count_of_orders_to")
    private Integer countOfOrdersTo;

    @Column(name = "segment_average_check_from")
    private Double averageCheckFrom;

    @Column(name = "segment_average_check_to")
    private Double averageCheckTo;

    @Column(name = "segment_total_spends_from")
    private Double totalSpendsFrom;

    @Column(name = "segment_total_spends_to")
    private Double totalSpendsTo;

    @Column(name = "segment_last_order_date_from")
    private LocalDate lastOrderDateFrom;

    @Column(name = "segment_last_order_date_to")
    private LocalDate lastOrderDateTo;

    @Column(name = "segment_creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "segment")
    private List<Dashboard> dashboards;

}
