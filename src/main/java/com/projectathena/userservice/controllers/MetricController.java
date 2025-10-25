package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import com.projectathena.userservice.services.MetricService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @QueryMapping
    public List<DeveloperMetricInfo> getMetrics(
            @Argument String userName,
            @Argument String userEmail,
            @Argument String gitRepositoryName,
            @Argument String gitRepositoryOwner
    ) {
        MetricRequest request = new MetricRequest(userName, userEmail, gitRepositoryName, gitRepositoryOwner);

        return metricService.mineAllMetrics(request);
    }

//    @GetMapping("/report")
//    public ResponseEntity<?> getMetricsReport(@RequestBody MetricRequest request) {
//        var response = metricService.getMetricReport(request);
//
//        return ResponseEntity.ok().body(response);
//    }
}
