package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MetricValue;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class LocPerDeveloperCalculator extends Calculator {

    private final static String LINES_ADDED_METRIC = "additions";
    private final static String LINES_DELETED_METRIC = "deletions";

    @Override
    public List<DeveloperMetricInfo> calculateMetric(MiningResult miningResult) {
        Map<String, DeveloperMetricInfo> metricsByDeveloper = new HashMap<>();

        miningResult.getCommits().forEach(miningCommit -> {
            String username = miningCommit.getAuthor().getLogin();
            String email = miningCommit.getAuthor().getEmail();

            long totalAdditions = miningCommit.getAdditions();
            long totalDeletions = miningCommit.getDeletions();

            DeveloperMetricInfo developerInfo = metricsByDeveloper.computeIfAbsent(username, key -> {
                DeveloperMetricInfo newInfo = new DeveloperMetricInfo();
                newInfo.setMetricType(getMetricType());
                newInfo.setDeveloperUsername(key);
                newInfo.setDeveloperEmail(email);
                newInfo.addMetric(BigDecimal.ZERO, LINES_ADDED_METRIC);
                newInfo.addMetric(BigDecimal.ZERO, LINES_DELETED_METRIC);
                return newInfo;
            });

            findMetric(developerInfo, LINES_ADDED_METRIC).ifPresent(metric ->
                    metric.setValue(metric.getValue().add(BigDecimal.valueOf(totalAdditions)))
            );

            findMetric(developerInfo, LINES_DELETED_METRIC).ifPresent(metric ->
                    metric.setValue(metric.getValue().add(BigDecimal.valueOf(totalDeletions)))
            );
        });

        return new ArrayList<>(metricsByDeveloper.values());
    }

    private Optional<MetricValue> findMetric(DeveloperMetricInfo info, String metricName) {
        return info.getMetricValues().stream()
                .filter(metric -> metricName.equals(metric.getDescription()))
                .findFirst();
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.LOC_PER_DEVELOPER;
    }
}
