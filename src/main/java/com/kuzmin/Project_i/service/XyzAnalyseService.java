package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.model.XyzAnalyse;
import com.kuzmin.Project_i.model.XyzCategory;
import com.kuzmin.Project_i.repository.CustomerRepository;
import com.kuzmin.Project_i.repository.XyzAnalyseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XyzAnalyseService {

    private final CustomerRepository customerRepository;
    private final XyzAnalyseRepository xyzAnalyseRepository;

    @Transactional
    public void runAnalysis(User user) {

        xyzAnalyseRepository.deleteAllByCustomerUser(user);

        List<Customer> customers = customerRepository.findAllByUser(user);

        for (Customer customer : customers) {

            double variation = calculateVariation(customer);

            XyzCategory category;
            String stability;

            if (variation <= 10) {
                category = XyzCategory.X;
                stability = "HIGH";
            } else if (variation <= 25) {
                category = XyzCategory.Y;
                stability = "MEDIUM";
            } else {
                category = XyzCategory.Z;
                stability = "LOW";
            }

            XyzAnalyse analyse = new XyzAnalyse();

            analyse.setCustomer(customer);

            analyse.setXyzCategory(category);

            analyse.setVariationCoefficient(variation);

            analyse.setPurchaseStability(stability);

            analyse.setCreationDate(LocalDateTime.now());

            xyzAnalyseRepository.save(analyse);
        }
    }

    private double calculateVariation(Customer customer) {
        return 100.0 / customer.getCountOfOrders();
    }

    public List<XyzAnalyse> findAll() {
        return xyzAnalyseRepository.findAll();
    }

}