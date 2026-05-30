package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DashboardRepository extends PagingAndSortingRepository<Dashboard, Long>, CrudRepository<Dashboard, Long> {

    List<Dashboard> findAll();

    List<Dashboard> findByUserId(Long userId);

    List<Dashboard> findBySegmentId(Long segmentId);

    List<Dashboard> findAllByUser(User user);

    Optional<Dashboard> findByIdAndUser(Long id, User user);

    @Modifying
    @Query("""
        delete from dashboards d
        where d.user = :user
    """)
    void deleteAllByUser(
            @Param("user") User user
    );

}
