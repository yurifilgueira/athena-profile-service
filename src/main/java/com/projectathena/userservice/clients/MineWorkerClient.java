package com.projectathena.userservice.clients;

import com.projectathena.userservice.configs.FeignClientConfiguration;
import com.projectathena.userservice.model.dto.MiningResult;
import com.projectathena.userservice.model.dto.requests.MetricRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "athena-mine-worker-service", configuration = FeignClientConfiguration.class)
public interface MineWorkerClient {

    @GetMapping( "/mining-results")
    MiningResult getMiningResult(
            @RequestParam String userName,
            @RequestParam String userEmail,
            @RequestParam String gitRepositoryName,
            @RequestParam String gitRepositoryOwner);

}
