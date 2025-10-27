package com.projectathena.userservice.services;

import com.projectathena.userservice.calculators.CalculatorFactory;
import com.projectathena.userservice.clients.MineWorkerClient;
import com.projectathena.userservice.clients.ReportClient;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MetricValue;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.model.dto.responses.ReportResponse;
import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MetricService {

    private final CalculatorFactory calculatorFactory;
    private final MineWorkerClient mineWorkerClient;
    private final ReportClient reportClient;

    public MetricService(CalculatorFactory calculatorFactory, MineWorkerClient mineWorkerClient, ReportClient reportClient) {
        this.calculatorFactory = calculatorFactory;
        this.mineWorkerClient = mineWorkerClient;
        this.reportClient = reportClient;
    }

    public Flux<DeveloperMetricInfo> mineAllMetrics(MetricRequest request) {
        Mono<MiningResult> cachedMiningCommits = mineWorkerClient.getMiningResult(
                request.userName(),
                request.userEmail(),
                request.gitRepositoryName(),
                request.gitRepositoryOwner()
        ).share();

        return Flux.fromIterable(MetricType.getAll())
                .flatMap(metricType ->
                        Mono.justOrEmpty(calculatorFactory.getCalculator(metricType))
                                .switchIfEmpty(Mono.error(new IllegalStateException("No calculator for metric: " + metricType)))
                )
                .flatMap(calculator -> calculator.calculateMetric(cachedMiningCommits.flatMapMany(miningResult -> Flux.fromIterable(miningResult.getCommits()).cache())))
                .groupBy(DeveloperMetricInfo::getDeveloperUsername)
                .flatMap(groupedFlux -> groupedFlux.reduce(this::mergeDeveloperInfo));
    }


    private DeveloperMetricInfo mergeDeveloperInfo(DeveloperMetricInfo accumulator, DeveloperMetricInfo current) {
        if (accumulator.getDeveloperEmail() == null) {
            accumulator.setDeveloperEmail(current.getDeveloperEmail());
        }
        Map<String, MetricValue> accumulatorMetricsMap = accumulator.getMetricValues().stream()
                .collect(Collectors.toMap(
                        metric -> metric.getMetricType().toString() + "::" + metric.getDescription(),
                        Function.identity()
                ));

        for (MetricValue currentMetric : current.getMetricValues()) {
            String key = currentMetric.getMetricType().toString() + "::" + currentMetric.getDescription();
            MetricValue existingMetric = accumulatorMetricsMap.get(key);

            if (existingMetric != null) {
                existingMetric.setValue(existingMetric.getValue().add(currentMetric.getValue()));
            } else {
                accumulator.getMetricValues().add(currentMetric);
            }
        }

        return accumulator;
    }

    public Flux<ReportResponse> getMetricReport(MetricRequest request) {
        return mineAllMetrics(request).collectList()
                .flatMapMany(reportClient::getReport);
    }
}