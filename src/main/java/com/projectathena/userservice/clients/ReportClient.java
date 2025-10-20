//package com.projectathena.userservice.clients;
//
//import com.projectathena.userservice.configs.FeignClientConfiguration;
//import com.projectathena.userservice.model.dto.DeveloperMetricInfo;
//import com.projectathena.userservice.model.dto.MiningResult;
//import com.projectathena.userservice.model.dto.ReportResult;
//import com.projectathena.userservice.model.dto.requests.MetricRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@FeignClient(name = "athena-report-service", configuration = FeignClientConfiguration.class)
//public interface ReportClient {
//
//    @PostMapping("/reports")
//    ReportResult createReport(@RequestBody List<DeveloperMetricInfo> infos);
//
//}
