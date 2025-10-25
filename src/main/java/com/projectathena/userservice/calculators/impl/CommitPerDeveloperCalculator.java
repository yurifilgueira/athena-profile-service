package com.projectathena.userservice.calculators.impl;

import com.projectathena.userservice.calculators.Calculator;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.enums.MetricType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
public class CommitPerDeveloperCalculator extends Calculator {

    Logger logger = LoggerFactory.getLogger(CommitPerDeveloperCalculator.class);

    @Override
    public Flux<DeveloperMetricInfo> calculateMetric(Flux<MiningCommit> miningCommitFlux) {
        return miningCommitFlux
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .groupBy(commit -> commit.getAuthor().getLogin())
                .flatMap(rawGroupedFlux -> {
                    Flux<MiningCommit> sharedGroup = rawGroupedFlux.cache();

                    Mono<MiningCommit> firstCommitMono = sharedGroup.next();
                    Mono<Long> countMono = sharedGroup.count();

                    return Mono.zip(countMono, firstCommitMono)
                            .map(tuple -> {
                                Long count = tuple.getT1();
                                MiningCommit firstCommit = tuple.getT2();

                                var developerInfo = new DeveloperMetricInfo();
                                developerInfo.setDeveloperUsername(firstCommit.getAuthor().getLogin());
                                developerInfo.setDeveloperEmail(firstCommit.getAuthor().getEmail());

                                developerInfo.addMetric(
                                        new BigDecimal(count),
                                        "commit_count",
                                        getMetricType()
                                );
                                return developerInfo;
                            });
                });
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.COMMIT_PER_DEVELOPER;
    }
}