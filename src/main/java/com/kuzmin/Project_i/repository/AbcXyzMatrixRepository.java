package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.AbcXyzMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbcXyzMatrixRepository extends PagingAndSortingRepository<AbcXyzMatrix, Long>, JpaRepository<AbcXyzMatrix, Long> {

    List<AbcXyzMatrix> findByCustomerId(Long customerId);

    List<AbcXyzMatrix> findByMatrixGroup(String matrixGroup);

}
