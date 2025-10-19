package com.projectathena.userservice.calculators;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;

import java.util.List;

public abstract class Calculator {

    public abstract List<DeveloperMetricInfo> calculateMetric(MiningResult miningResult);
    public abstract MetricType getMetricType();

}
