package com.projectathena.userservice.clients;

import com.projectathena.userservice.model.dto.MiningCommit;
import com.projectathena.userservice.model.dto.MiningResult;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MineWorkerClient {

    private final WebClient webClient;

    public MineWorkerClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://athena-mine-worker-service").build();
    }

    public Mono<MiningResult> getMiningResult(String userName, String userEmail, String gitRepositoryName, String gitRepositoryOwner) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/mining-results")
                        .queryParam("userName", userName)
                        .queryParam("userEmail", userEmail)
                        .queryParam("gitRepositoryName", gitRepositoryName)
                        .queryParam("gitRepositoryOwner", gitRepositoryOwner)
                        .build())
                .retrieve()
                .bodyToMono(MiningResult.class);
    }

}
