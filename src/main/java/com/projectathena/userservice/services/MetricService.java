package com.projectathena.userservice.services;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.calculators.CalculatorFactory;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetricService {

    private final CalculatorFactory calculatorFactory;

    public MetricService(CalculatorFactory calculatorFactory) {
        this.calculatorFactory = calculatorFactory;
    }

    public List<DeveloperMetricInfo> mineAllMetrics(MiningResult miningResult) {
        Map<String, DeveloperMetricInfo> aggregatedMetrics = new HashMap<>();

        for (MetricType metricType : MetricType.getAll()) {
            Calculator calculator = calculatorFactory.getCalculator(metricType)
                    .orElseThrow(() -> new IllegalStateException("No calculator found for metric: " + metricType));

            List<DeveloperMetricInfo> partialResults = calculator.calculateMetric(miningResult);

            mergeResults(aggregatedMetrics, partialResults);
        }

        return new ArrayList<>(aggregatedMetrics.values());
    }

    private void mergeResults(Map<String, DeveloperMetricInfo> targetMap, List<DeveloperMetricInfo> partialResults) {
        for (DeveloperMetricInfo partialInfo : partialResults) {
            String username = partialInfo.getDeveloperUsername();

            DeveloperMetricInfo finalInfo = targetMap.computeIfAbsent(username, key -> {
                DeveloperMetricInfo newInfo = new DeveloperMetricInfo();
                newInfo.setDeveloperUsername(key);
                newInfo.setDeveloperEmail(partialInfo.getDeveloperEmail());
                return newInfo;
            });

            finalInfo.getMetricValues().addAll(partialInfo.getMetricValues());
        }
    }

}
