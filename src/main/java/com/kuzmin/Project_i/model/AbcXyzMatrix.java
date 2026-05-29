package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity(name = "abc_xyz_matrix")
@Table(name = "abc_xyz_matrix")
public class AbcXyzMatrix {

    @Id
    @SequenceGenerator(name = "abc_xyz_matrix_id_seq", sequenceName = "abc_xyz_matrix_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abc_xyz_matrix_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_analyse_category", nullable = false)
    private AbcCategory abcCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "xyz_analyse_category", nullable = false)
    private XyzCategory xyzCategory;

    @Column(name = "abc_xyz_matrix_matrix_group", nullable = false)
    private String matrixGroup;

    @Column(name = "abc_xyz_matrix_recommendations")
    private String recommendations;

}
