package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findAllByUser(User user) {
        return customerRepository.findAllByUser(user);
    }

    public Customer findByIdAndUser(
            Long id,
            User user
    ) {
        return customerRepository
                .findByIdAndUser(id, user)
                .orElseThrow();
    }

}