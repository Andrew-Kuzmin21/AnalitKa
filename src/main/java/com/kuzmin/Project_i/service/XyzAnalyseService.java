package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.XyzAnalyse;
import com.kuzmin.Project_i.repository.XyzAnalyseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XyzAnalyseService {

    private final XyzAnalyseRepository xyzAnalyseRepository;

    public XyzAnalyseService(XyzAnalyseRepository xyzAnalyseRepository) {
        this.xyzAnalyseRepository = xyzAnalyseRepository;
    }

    public List<XyzAnalyse> findAll() {
        return xyzAnalyseRepository.findAll();
    }

}