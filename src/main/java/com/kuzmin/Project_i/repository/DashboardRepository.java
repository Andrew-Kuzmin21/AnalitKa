package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Dashboard;
import com.kuzmin.Project_i.model.XyzAnalyse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends PagingAndSortingRepository<Dashboard, Long>, CrudRepository<Dashboard, Long> {

    List<Dashboard> findAll();

    List<Dashboard> findByUserId(Long userId);

    List<Dashboard> findBySegmentId(Long segmentId);

}
