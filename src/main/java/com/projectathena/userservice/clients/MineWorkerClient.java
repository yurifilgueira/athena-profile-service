package com.projectathena.userservice.clients;

import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.MiningResult;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MineWorkerClient {

    private final HttpGraphQlClient graphQlClient;

    public MineWorkerClient(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
                .baseUrl("http://athena-mine-worker-service/graphql")
                .build();
        this.graphQlClient = HttpGraphQlClient.builder(webClient).build();
    }

    public Flux<MiningCommit> getMiningResult(String userName, String userEmail, String gitRepositoryName, String gitRepositoryOwner) {
        return this.graphQlClient.documentName("getMiningResult")
                .variable("userName", userName)
                .variable("userEmail", userEmail)
                .variable("gitRepositoryName", gitRepositoryName)
                .variable("gitRepositoryOwner", gitRepositoryOwner)
                .retrieve("getMiningResult.commits")
                .toEntityList(MiningCommit.class)
                .flatMapMany(Flux::fromIterable);
    }

}
