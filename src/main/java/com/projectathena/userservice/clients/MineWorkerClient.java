package com.projectathena.userservice.clients;

import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.MiningResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MineWorkerClient {

    private final HttpGraphQlClient client;

    public MineWorkerClient(@Value("${client.service.url-mine-service}") String baseUrl) {
        this.client = HttpGraphQlClient.builder()
                .webClient(b -> b.baseUrl(baseUrl + "/graphql"))
                .build();
    }

    public List<MiningCommit> getMiningResult(
            String userName,
            String userEmail,
            String gitRepositoryName,
            String gitRepositoryOwner
    ) {

        return client.documentName("getMiningResult")
                .variable("userName", userName)
                .variable("userEmail", userEmail)
                .variable("gitRepositoryName", gitRepositoryName)
                .variable("gitRepositoryOwner", gitRepositoryOwner)
                .retrieveSync("getMiningResult.commits")
                .toEntityList(MiningCommit.class);
    }


}
