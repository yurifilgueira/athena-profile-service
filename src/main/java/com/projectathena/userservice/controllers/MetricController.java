package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.services.MetricService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Controller
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @SubscriptionMapping
    public Flux<DeveloperMetricInfo> getMetrics(
            @Argument String userName,
            @Argument String userEmail,
            @Argument String gitRepositoryName,
            @Argument String gitRepositoryOwner) {

        var request = new MetricRequest(userName, userEmail, gitRepositoryName, gitRepositoryOwner);
        return metricService.mineAllMetrics(request);
    }
//    @GetMapping("/report")
//    public ResponseEntity<?> getMetricsReport(@RequestBody MetricRequest request) {
//        var response = metricService.getMetricReport(request);
//
//        return ResponseEntity.ok().body(response);
//    }
}
