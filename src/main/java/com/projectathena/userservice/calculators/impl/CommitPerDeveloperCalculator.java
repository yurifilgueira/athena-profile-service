package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MetricValue;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommitPerDeveloperCalculator extends Calculator {

    @Override
    public List<DeveloperMetricInfo> calculateMetric(MiningResult miningResult) {

        Map<String, DeveloperMetricInfo> metricsByDeveloper = new HashMap<>();

        miningResult.getCommits().forEach(miningCommit -> {
            String username = miningCommit.getAuthor().getLogin();
            String email = miningCommit.getAuthor().getEmail();

            DeveloperMetricInfo developerInfo = metricsByDeveloper.computeIfAbsent(username, key -> {
                DeveloperMetricInfo newInfo = new DeveloperMetricInfo();
                newInfo.setMetricType(getMetricType());
                newInfo.setDeveloperUsername(key);
                newInfo.setDeveloperEmail(email);
                newInfo.addMetric(new BigDecimal(0), "quantity of commits");
                return newInfo;
            });

            MetricValue commitMetric = developerInfo.getMetricValues().getFirst();
            commitMetric.setValue(commitMetric.getValue().add(new BigDecimal(1)));
        });

        return new ArrayList<>(metricsByDeveloper.values());
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.COMMIT_PER_DEVELOPER;
    }
}
