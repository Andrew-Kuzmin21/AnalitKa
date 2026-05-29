package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.AbcAnalyse;
import com.kuzmin.Project_i.repository.AbcAnalyseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbcAnalyseService {

    private final AbcAnalyseRepository abcAnalyseRepository;

    public AbcAnalyseService(AbcAnalyseRepository abcAnalyseRepository) {
        this.abcAnalyseRepository = abcAnalyseRepository;
    }

    public List<AbcAnalyse> findAll() {
        return abcAnalyseRepository.findAll();
    }

}