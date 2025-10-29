package com.projectathena.userservice.services;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.calculators.CalculatorFactory;
import com.projectathena.userservice.clients.MineWorkerClient;
import com.projectathena.userservice.clients.ReportClient;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.dto.ReportResult;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.model.enums.MetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetricService {

    private final CalculatorFactory calculatorFactory;
    private final MineWorkerClient mineWorkerClient;
    private final ReportClient reportClient;
    private final Logger logger = LoggerFactory.getLogger(MetricService.class);

    public MetricService(CalculatorFactory calculatorFactory, MineWorkerClient mineWorkerClient, ReportClient reportClient) {
        this.calculatorFactory = calculatorFactory;
        this.mineWorkerClient = mineWorkerClient;
        this.reportClient = reportClient;
    }

    @Cacheable(value = "miningResults", key = "#request")

    public List<DeveloperMetricInfo> mineAllMetrics(MetricRequest request) {

        logger.warn("Executed without cache...");

        MiningResult miningResult = mineWorkerClient.getMiningResult(
                request.userName(),
                request.userEmail(),
                request.gitRepositoryName(),
                request.gitRepositoryOwner()
        );

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

    public ReportResult getMetricReport(MetricRequest request) {

        List<DeveloperMetricInfo> result = mineAllMetrics(request);

        return reportClient.createReport(result);
    }
}
