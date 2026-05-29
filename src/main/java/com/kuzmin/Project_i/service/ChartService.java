package com.kuzmin.Project_i.service;

import com.kuzmin.Project_i.model.Chart;
import com.kuzmin.Project_i.repository.ChartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartService {

    private final ChartRepository chartRepository;

    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    public List<Chart> findAll() {
        return chartRepository.findAll();
    }

    public Chart save(Chart chart) {
        return chartRepository.save(chart);
    }

}