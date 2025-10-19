package com.projectathena.userservice.model.dto.requests;

public record MetricRequest(
    String userName,
    String userEmail,
    String gitRepositoryName,
    String gitRepositoryOwner
) {

}
