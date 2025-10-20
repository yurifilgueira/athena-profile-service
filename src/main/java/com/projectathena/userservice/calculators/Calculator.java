package com.projectathena.userservice.calculators;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.enums.MetricType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class Calculator {

    public abstract Flux<DeveloperMetricInfo> calculateMetric(Mono<MiningResult> miningResult);
    public abstract MetricType getMetricType();

}
