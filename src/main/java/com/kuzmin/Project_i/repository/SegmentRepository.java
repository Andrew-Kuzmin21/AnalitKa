package com.kuzmin.Project_i.repository;

import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {

    List<Segment> findByUserId(Long userId);

    List<Segment> findAllByUser(User user);

    Optional<Segment> findByIdAndUser(
            Long id,
            User user
    );

}