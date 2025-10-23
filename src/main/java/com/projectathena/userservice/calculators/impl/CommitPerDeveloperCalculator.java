package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Component
public class CommitPerDeveloperCalculator extends Calculator {

    @Override
    public Flux<DeveloperMetricInfo> calculateMetric(Flux<MiningCommit> miningCommitFlux) {
        return miningCommitFlux
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .map(commit -> {
                    var developerInfo = new DeveloperMetricInfo();
                    developerInfo.setDeveloperUsername(commit.getAuthor().getLogin());
                    developerInfo.setDeveloperEmail(commit.getAuthor().getEmail());

                    developerInfo.addMetric(
                            BigDecimal.ONE,
                            "commit_count_increment",
                            getMetricType()
                    );
                    return developerInfo;
                });
    }


    @Override
    public MetricType getMetricType() {
        return MetricType.COMMIT_PER_DEVELOPER;
    }
}