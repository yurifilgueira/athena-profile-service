package com.projectathena.userservice.services;

import com.projectathena.userservice.calculators.CalculatorFactory;
import com.projectathena.userservice.clients.MineWorkerClient;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MetricService {

    private final CalculatorFactory calculatorFactory;
    private final MineWorkerClient mineWorkerClient;

    public MetricService(CalculatorFactory calculatorFactory, MineWorkerClient mineWorkerClient) {
        this.calculatorFactory = calculatorFactory;
        this.mineWorkerClient = mineWorkerClient;
    }

    public Flux<DeveloperMetricInfo> mineAllMetrics(MetricRequest request) {
        Mono<MiningResult> miningResultMono = mineWorkerClient.getMiningResult(
                request.userName(),
                request.userEmail(),
                request.gitRepositoryName(),
                request.gitRepositoryOwner()
        );

        return Flux.fromIterable(MetricType.getAll())
                .flatMap(metricType ->
                        Mono.justOrEmpty(calculatorFactory.getCalculator(metricType))
                                .switchIfEmpty(Mono.error(new IllegalStateException("No calculator for metric: " + metricType)))
                )

                .flatMap(calculator -> calculator.calculateMetric(miningResultMono))
                .groupBy(DeveloperMetricInfo::getDeveloperUsername)
                .flatMap(groupedFlux -> groupedFlux.reduce(this::mergeDeveloperInfo));
    }

    private DeveloperMetricInfo mergeDeveloperInfo(DeveloperMetricInfo acc, DeveloperMetricInfo current) {
        acc.getMetricValues().addAll(current.getMetricValues());
        if (acc.getDeveloperEmail() == null) {
            acc.setDeveloperEmail(current.getDeveloperEmail());
        }
        return acc;
    }
}