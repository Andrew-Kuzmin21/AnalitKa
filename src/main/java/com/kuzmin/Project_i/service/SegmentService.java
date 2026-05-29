package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Segment;
import com.kuzmin.Project_i.repository.SegmentRepository;
import org.springframework.stereotype.Service;

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
        return segmentRepository.save(segment);
    }

}