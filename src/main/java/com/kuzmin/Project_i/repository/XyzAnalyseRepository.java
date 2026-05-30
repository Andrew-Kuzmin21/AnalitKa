package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.model.XyzAnalyse;
import com.kuzmin.Project_i.model.XyzCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface XyzAnalyseRepository extends JpaRepository<XyzAnalyse, Long> {

    List<XyzAnalyse> findAllByCustomerIn(
            List<Customer> customers
    );

    List<XyzAnalyse> findByCustomerId(Long customerId);

    List<XyzAnalyse> findByXyzCategory(XyzCategory category);

    List<XyzAnalyse> findAllByCustomerUser(User user);

    void deleteAllByCustomerUser(User user);

    Optional<XyzAnalyse> findByCustomer(Customer customer);

    @Modifying
    @Query("""
        delete from xyz_analysis x
        where x.customer.user = :user
    """)
    void deleteAllByUser(
            @Param("user") User user
    );

}