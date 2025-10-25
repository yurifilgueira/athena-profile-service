package com.projectathena.userservice.clients;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.responses.ReportResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class ReportClient {

    private final WebClient webClient;

    public ReportClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://athena-report-service").build();
    }

    public Flux<ReportResponse> getReport(List<DeveloperMetricInfo> developerMetricInfos) {
        return this.webClient.post()
                .uri("/reports")
                .bodyValue(developerMetricInfos)
                .retrieve()
                .bodyToFlux(ReportResponse.class);
    }
}
