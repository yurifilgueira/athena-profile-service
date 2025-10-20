package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.services.MetricService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<DeveloperMetricInfo> getMetrics(
            @RequestParam String userName,
            @RequestParam String userEmail,
            @RequestParam String gitRepositoryName,
            @RequestParam String gitRepositoryOwner) {

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
