package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.repository.AbcXyzMatrixRepository;
import org.springframework.stereotype.Service;

@Service
public class AbcXyzMatrixService {

    private final AbcXyzMatrixRepository abcXyzMatrixRepository;

    public AbcXyzMatrixService(AbcXyzMatrixRepository abcXyzMatrixRepository) {
        this.abcXyzMatrixRepository = abcXyzMatrixRepository;
    }

}
