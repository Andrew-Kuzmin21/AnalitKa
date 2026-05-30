package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.AbcAnalyse;
import com.kuzmin.Project_i.model.AbcCategory;
import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AbcAnalyseRepository extends CrudRepository<AbcAnalyse, Long> {

    List<AbcAnalyse> findAllByCustomerIn(
            List<Customer> customers
    );

    List<AbcAnalyse> findAll();

    List<AbcAnalyse> findByCustomerId(Long customerId);

    List<AbcAnalyse> findByAbcCategory(AbcCategory category);

    List<AbcAnalyse> findAllByCustomerUser(User user);

    void deleteAllByCustomerUser(User user);

    @Modifying
    @Query("""
        delete from abc_analysis a
        where a.customer.user = :user
    """)
    void deleteAllByUser(
            @Param("user") User user
    );

}
