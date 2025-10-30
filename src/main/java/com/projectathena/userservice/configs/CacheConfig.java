package com.projectathena.userservice.configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.projectathena.userservice.cache.CacheTemplate;
import com.projectathena.userservice.model.dto.responses.ReportResponse;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;
import com.projectathena.userservice.model.dto.DeveloperMetricInfo;

@Configuration
public class CacheConfig {

    private static final Duration DEFAULT_TTL = Duration.ofMinutes(10);

    public static class StringType extends TypeReference<String> {}
    public static class DeveloperMetricInfoListType extends TypeReference<List<DeveloperMetricInfo>> {}
    public static class ReportResponseListType extends TypeReference<List<ReportResponse>> {}

    @Bean
    public CacheTemplate<String, List<DeveloperMetricInfo>> metricsCacheTemplate(RedissonReactiveClient client) {

        Codec metricsCodec = new TypedJsonJacksonCodec(
                new StringType(),
                new DeveloperMetricInfoListType()
        );

        RMapCacheReactive<String, List<DeveloperMetricInfo>> metricsCacheMap =
                client.getMapCache("cache:metrics", metricsCodec);

        return new CacheTemplate<>(metricsCacheMap, DEFAULT_TTL);
    }

    @Bean
    public CacheTemplate<String, List<ReportResponse>> reportCacheTemplate(RedissonReactiveClient client) {

        Codec reportCodec = new TypedJsonJacksonCodec(
                new StringType(),
                new ReportResponseListType()
        );

        RMapCacheReactive<String, List<ReportResponse>> reportCacheMap =
                client.getMapCache("cache:reports", reportCodec);

        return new CacheTemplate<>(reportCacheMap, DEFAULT_TTL);
    }
}