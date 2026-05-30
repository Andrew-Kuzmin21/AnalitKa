package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.AbcXyzMatrix;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbcXyzMatrixRepository extends PagingAndSortingRepository<AbcXyzMatrix, Long>, JpaRepository<AbcXyzMatrix, Long> {

    List<AbcXyzMatrix> findAllByCustomerIn(
            List<Customer> customers
    );

    List<AbcXyzMatrix> findByCustomerId(Long customerId);

    List<AbcXyzMatrix> findByMatrixGroup(String matrixGroup);

    List<AbcXyzMatrix> findAllByCustomerUser(User user);

    void deleteAllByCustomerUser(User user);

    @Modifying
    @Query("""
        delete from abc_xyz_matrix m
        where m.customer.user = :user
    """)
    void deleteAllByUser(
            @Param("user") User user
    );

}
