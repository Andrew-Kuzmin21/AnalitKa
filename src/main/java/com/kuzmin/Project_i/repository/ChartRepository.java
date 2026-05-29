package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Chart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChartRepository extends CrudRepository<Chart, Long> {

    List<Chart> findAll();

    List<Chart> findByDashboardId(Long dashboardId);

}
