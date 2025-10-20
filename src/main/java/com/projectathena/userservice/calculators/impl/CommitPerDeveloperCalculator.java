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
public class CommitPerDeveloperCalculator extends Calculator {

    @Override
    public Flux<DeveloperMetricInfo> calculateMetric(Mono<MiningResult> miningResultMono) {
        return miningResultMono
                .flatMapMany(miningResult -> Flux.fromIterable(miningResult.getCommits()))
                .filter(commit -> commit.getAuthor() != null && commit.getAuthor().getLogin() != null)
                .groupBy(miningCommit -> miningCommit.getAuthor().getLogin())
                .flatMap(groupedFlux ->
                        groupedFlux.reduce(new CommitAccumulator(), CommitAccumulator::accumulate)
                                .filter(acc -> Objects.nonNull(acc.firstCommit))
                                .map(accumulator -> {
                                    var firstCommit = accumulator.firstCommit;
                                    var developerInfo = new DeveloperMetricInfo();
                                    developerInfo.setDeveloperUsername(firstCommit.getAuthor().getLogin());
                                    developerInfo.setDeveloperEmail(firstCommit.getAuthor().getEmail());

                                    developerInfo.addMetric(
                                            new BigDecimal(accumulator.count),
                                            "quantity of commits",
                                            getMetricType()
                                    );
                                    return developerInfo;
                                })
                );
    }

    private static class CommitAccumulator {
        MiningCommit firstCommit;
        long count = 0L;

        public CommitAccumulator accumulate(MiningCommit currentCommit) {
            if (this.firstCommit == null) {
                this.firstCommit = currentCommit;
            }
            this.count++;
            return this;
        }
    }

    @Override
    public MetricType getMetricType() {
        return MetricType.COMMIT_PER_DEVELOPER;
    }
}