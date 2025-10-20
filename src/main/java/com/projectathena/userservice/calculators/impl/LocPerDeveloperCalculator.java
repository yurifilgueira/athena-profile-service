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
    public Flux<DeveloperMetricInfo> calculateMetric(Mono<MiningResult> miningResultMono) {
        return miningResultMono
                .flatMapMany(miningResult -> Flux.fromIterable(miningResult.getCommits()))
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .groupBy(miningCommit -> miningCommit.getAuthor().getLogin())
                .flatMap(groupedFlux ->
                        groupedFlux.reduce(new LocAccumulator(), LocAccumulator::accumulate)
                                .filter(acc -> Objects.nonNull(acc.firstCommit))
                                .map(accumulator -> {
                                    var firstCommit = accumulator.firstCommit;
                                    var developerInfo = new DeveloperMetricInfo();
                                    developerInfo.setDeveloperUsername(firstCommit.getAuthor().getLogin());
                                    developerInfo.setDeveloperEmail(firstCommit.getAuthor().getEmail());

                                    developerInfo.addMetric(
                                            new BigDecimal(accumulator.totalAdditions),
                                            LINES_ADDED_METRIC,
                                            getMetricType()
                                    );
                                    developerInfo.addMetric(
                                            new BigDecimal(accumulator.totalDeletions),
                                            LINES_DELETED_METRIC,
                                            getMetricType()
                                    );
                                    return developerInfo;
                                })
                );
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.LOC_PER_DEVELOPER;
    }

    private static class LocAccumulator {
        MiningCommit firstCommit;
        long totalAdditions = 0L;
        long totalDeletions = 0L;

        public LocAccumulator accumulate(MiningCommit currentCommit) {
            if (this.firstCommit == null) {
                this.firstCommit = currentCommit;
            }
            this.totalAdditions += currentCommit.getAdditions();
            this.totalDeletions += currentCommit.getDeletions();
            return this;
        }
    }
}