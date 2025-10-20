package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.services.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public ResponseEntity<?> getMetrics(@RequestBody MetricRequest request) {

        var response = metricService.mineAllMetrics(request);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/report")
    public ResponseEntity<?> getMetricsReport(@RequestBody MetricRequest request) {
        var response = metricService.getMetricReport(request);

        return ResponseEntity.ok().body(response);
    }
}
