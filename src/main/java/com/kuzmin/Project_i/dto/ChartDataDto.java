package com.kuzmin.Project_i.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChartDataDto {

    private String chartName;
    private String chartType;
    private List<String> labels;
    private List<Double> values;

}
