package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.services.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping
    public ResponseEntity<?> getMetrics(
            @RequestParam String userName,
            @RequestParam String userEmail,
            @RequestParam String gitRepositoryName,
            @RequestParam String gitRepositoryOwner
    ) {

        MetricRequest request = new MetricRequest(userName, userEmail, gitRepositoryName, gitRepositoryOwner);
        var response = metricService.mineAllMetrics(request);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/report")
    public ResponseEntity<?> getMetricsReport(@RequestBody MetricRequest request) {
        var response = metricService.getMetricReport(request);

        return ResponseEntity.ok().body(response);
    }
}
