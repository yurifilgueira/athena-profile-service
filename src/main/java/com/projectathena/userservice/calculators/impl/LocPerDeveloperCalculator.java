package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MetricValue;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.enums.MetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Component
public class LocPerDeveloperCalculator extends Calculator {

    private static final String LINES_ADDED_METRIC = "additions";
    private static final String LINES_DELETED_METRIC = "deletions";

    Logger logger = LoggerFactory.getLogger(LocPerDeveloperCalculator.class);

    @Override
    public Flux<DeveloperMetricInfo> calculateMetric(Flux<MiningCommit> miningCommitFlux) {

        return miningCommitFlux
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .groupBy(commit -> commit.getAuthor().getLogin())
                .flatMap(groupedFlux ->
                        groupedFlux.map(commit -> {
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
                                })
                                .reduce(this::mergeDeveloperMetrics)
                );
    }

    private DeveloperMetricInfo mergeDeveloperMetrics(DeveloperMetricInfo acc, DeveloperMetricInfo current) {
        MetricValue currentAdditions = current.getMetricValues().get(0);
        MetricValue currentDeletions = current.getMetricValues().get(1);

        var accAdditions = acc.getMetricValues().stream()
                .filter(m -> m.getDescription().equals(LINES_ADDED_METRIC))
                .findFirst();
        var accDeletions = acc.getMetricValues().stream()
                .filter(m -> m.getDescription().equals(LINES_DELETED_METRIC))
                .findFirst();

        accAdditions.ifPresentOrElse(
                accMetric -> accMetric.setValue(accMetric.getValue().add(currentAdditions.getValue())),
                () -> acc.getMetricValues().add(currentAdditions)
        );
        accDeletions.ifPresentOrElse(
                accMetric -> accMetric.setValue(accMetric.getValue().add(currentDeletions.getValue())),
                () -> acc.getMetricValues().add(currentDeletions)
        );

        if (acc.getDeveloperEmail() == null) {
            acc.setDeveloperEmail(current.getDeveloperEmail());
        }

        return acc;
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.LOC_PER_DEVELOPER;
    }
}