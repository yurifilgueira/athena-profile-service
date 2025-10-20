package com.projectathena.userservice.model.dto;

import com.projectathena.userservice.model.enums.MetricType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DeveloperMetricInfo {

    private String developerUsername;
    private String developerEmail;
    private final List<MetricValue> metricValues;

    public DeveloperMetricInfo() {
        metricValues = new ArrayList<>();
    }

    public DeveloperMetricInfo(String developerUsername, String developerEmail, List<MetricValue> metricValues) {
        this.developerUsername = developerUsername;
        this.developerEmail = developerEmail;
        this.metricValues = metricValues;
    }

    public String getDeveloperUsername() {
        return developerUsername;
    }

    public void setDeveloperUsername(String developerUsername) {
        this.developerUsername = developerUsername;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }

    public List<MetricValue> getMetricValues() {
        return metricValues;
    }

    public void addMetric(BigDecimal value, String description, MetricType metricType) {
        this.metricValues.add(new MetricValue(value, description, metricType));
    }
}
