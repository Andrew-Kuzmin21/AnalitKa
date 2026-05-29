package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.XyzAnalyse;
import com.kuzmin.Project_i.model.XyzCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XyzAnalyseRepository extends JpaRepository<XyzAnalyse, Long> {

    List<XyzAnalyse> findByCustomerId(Long customerId);

    List<XyzAnalyse> findByXyzCategory(XyzCategory category);

}