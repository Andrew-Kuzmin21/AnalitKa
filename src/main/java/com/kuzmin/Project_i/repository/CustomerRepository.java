package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.Sex;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, CrudRepository<Customer, Long> {

    List<Customer> findAll();

    List<Customer> findBySex(Sex sex);

    List<Customer> findByRegion(String region);

    List<Customer> findByCountOfOrdersGreaterThan(Integer count);

    List<Customer> findAllByUser(User user);

    Optional<Customer> findByIdAndUser(
            Long id,
            User user
    );

}
