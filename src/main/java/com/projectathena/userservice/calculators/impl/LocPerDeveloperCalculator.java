package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class LocPerDeveloperCalculator extends Calculator {

    private static final String LINES_ADDED_METRIC = "additions";
    private static final String LINES_DELETED_METRIC = "deletions";

    @Override
    public Flux<DeveloperMetricInfo> calculateMetric(Flux<MiningCommit> miningCommitFlux) {

        return miningCommitFlux
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .map(commit -> {
                    var developerInfo = new DeveloperMetricInfo();
                    developerInfo.setDeveloperUsername(commit.getAuthor().getLogin());
                    developerInfo.setDeveloperEmail(commit.getAuthor().getEmail());
                    developerInfo.addMetric(
                            new BigDecimal(commit.getAdditions()),
                            LINES_ADDED_METRIC,
                            getMetricType()
                    );
                    developerInfo.addMetric(
                            new BigDecimal(commit.getDeletions()),
                            LINES_DELETED_METRIC,
                            getMetricType()
                    );
                    return developerInfo;
                });
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.LOC_PER_DEVELOPER;
    }
}