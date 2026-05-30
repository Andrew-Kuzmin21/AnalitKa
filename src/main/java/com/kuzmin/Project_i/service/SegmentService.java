package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.model.User;
import com.kuzmin.Project_i.repository.SegmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SegmentService {

    private final SegmentRepository segmentRepository;

    public SegmentService(SegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    public List<Segment> findAll() {
        return segmentRepository.findAll();
    }

    public Segment save(Segment segment) {
        if (segment.getCreationDate() == null) {
            segment.setCreationDate(
                    LocalDateTime.now()
            );
        }

        return segmentRepository.save(segment);
    }

    public List<Segment> findAllByUser(User user) {
        return segmentRepository.findAllByUser(user);
    }

    public Segment findByIdAndUser(
            Long id,
            User user
    ) {
        return segmentRepository
                .findByIdAndUser(id, user)
                .orElseThrow();
    }

}