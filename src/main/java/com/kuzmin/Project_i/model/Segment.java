package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

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

    @Column(name = "segment_description")
    private String description;

    @Column(name = "segment_filter_query", columnDefinition = "TEXT")
    private String filterQuery;

    @Column(name = "segment_creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "segment")
    private List<Dashboard> dashboards;

}
