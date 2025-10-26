package com.projectathena.userservice.clients;

import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
import com.projectathena.userservice.model.dto.responses.ReportResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReportClient {

    private final HttpGraphQlClient client;

    public ReportClient(@Value("${client.service.url-report-service}") String baseUrl) {
        this.client = HttpGraphQlClient.builder()
                .webClient(b -> b.baseUrl(baseUrl + "/graphql"))
                .build();
    }

    public ReportResponse createReport(List<DeveloperMetricInfo> infos) {
        return client.documentName("getReport")
                .variable("infos", infos)
                .retrieveSync("generateReport")
                .toEntity(ReportResponse.class);
    }

}
