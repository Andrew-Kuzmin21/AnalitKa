package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Customer;
import com.kuzmin.Project_i.model.RfmAnalyse;
import com.kuzmin.Project_i.model.RfmSegment;
import com.kuzmin.Project_i.repository.CustomerRepository;
import com.kuzmin.Project_i.repository.RfmAnalyseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RfmAnalyseService {

    private final CustomerRepository customerRepository;
    private final RfmAnalyseRepository rfmAnalyseRepository;

    @Transactional
    public void runAnalysis() {

        rfmAnalyseRepository.deleteAll();

        List<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {

            long recencyDays = ChronoUnit.DAYS.between(
                    customer.getLastOrderDate(),
                    LocalDate.now()
            );

            int frequency = customer.getCountOfOrders();

            double monetary = customer.getTotalSpent().doubleValue();

            int rScore = calculateRecencyScore(recencyDays);

            int fScore = calculateFrequencyScore(frequency);

            int mScore = calculateMonetaryScore(monetary);

            String rfmScore = rScore + "" + fScore + "" + mScore;

            RfmSegment segment = determineSegment(
                    rScore,
                    fScore,
                    mScore
            );

            RfmAnalyse analyse = new RfmAnalyse();

            analyse.setCustomer(customer);

            analyse.setRecency(
                    (double) recencyDays
            );

            analyse.setFrequency(
                    (double) frequency
            );

            analyse.setMonetary(monetary);

            analyse.setRScore(rScore);

            analyse.setFScore(fScore);

            analyse.setMScore(mScore);

            analyse.setRfmScore(rfmScore);

            analyse.setRfmSegment(segment);

            analyse.setCreationDate(LocalDateTime.now());

            rfmAnalyseRepository.save(analyse);
        }
    }

    private int calculateRecencyScore(long days) {

        if (days <= 30) {
            return 5;
        }

        if (days <= 90) {
            return 4;
        }

        if (days <= 180) {
            return 3;
        }

        if (days <= 365) {
            return 2;
        }

        return 1;
    }

    private int calculateFrequencyScore(int frequency) {

        if (frequency >= 50) {
            return 5;
        }

        if (frequency >= 20) {
            return 4;
        }

        if (frequency >= 10) {
            return 3;
        }

        if (frequency >= 5) {
            return 2;
        }

        return 1;
    }

    private int calculateMonetaryScore(double monetary) {

        if (monetary >= 10000) {
            return 5;
        }

        if (monetary >= 5000) {
            return 4;
        }

        if (monetary >= 2000) {
            return 3;
        }

        if (monetary >= 500) {
            return 2;
        }

        return 1;
    }

    private RfmSegment determineSegment(int r, int f, int m) {

        if (r >= 4 && f >= 4 && m >= 4) {
            return RfmSegment.VIP;
        }

        if (r <= 2 && f <= 2) {
            return RfmSegment.LOST;
        }

        return RfmSegment.ACTIVE;
    }

    public List<RfmAnalyse> findAll() {
        return rfmAnalyseRepository.findAll();
    }

}
