package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.RfmAnalyse;
import com.kuzmin.Project_i.repository.RfmAnalyseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RfmAnalyseService {

    private final RfmAnalyseRepository rfmAnalyseRepository;

    public RfmAnalyseService(RfmAnalyseRepository rfmAnalyseRepository) {
        this.rfmAnalyseRepository = rfmAnalyseRepository;
    }

    public List<RfmAnalyse> findAll() {
        return rfmAnalyseRepository.findAll();
    }

}