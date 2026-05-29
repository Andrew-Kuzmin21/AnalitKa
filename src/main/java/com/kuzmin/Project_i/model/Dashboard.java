package com.kuzmin.Project_i.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity(name = "dashboards")
@Table(name = "dashboards")
public class Dashboard {

    @Id
    @SequenceGenerator(name = "dashboard_id_seq", sequenceName = "dashboard_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard_id_seq")
    private Long id;

    @Column(name = "dashboard_name", nullable = false)
    private String name;

    @Column(name = "dashboard_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id")
    private Segment segment;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "dashboard_creation_date", nullable = false)
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "dashboard")
    private List<Chart> charts;

    @PrePersist
    public void prePersist() {

        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }

        if (isDefault == null) {
            isDefault = false;
        }
    }

}
