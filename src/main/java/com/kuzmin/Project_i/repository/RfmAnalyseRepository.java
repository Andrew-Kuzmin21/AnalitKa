package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.RfmAnalyse;
import com.kuzmin.Project_i.model.RfmSegment;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RfmAnalyseRepository extends JpaRepository<RfmAnalyse, Long> {

    List<RfmAnalyse> findByCustomerId(Long customerId);

    List<RfmAnalyse> findByRfmSegment(RfmSegment segment);

    List<RfmAnalyse> findAllByCustomerUser(User user);

    void deleteAllByCustomerUser(User user);

}