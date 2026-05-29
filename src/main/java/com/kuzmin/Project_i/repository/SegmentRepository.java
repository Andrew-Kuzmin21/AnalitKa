package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Segment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {

    List<Segment> findByUserId(Long userId);

}