package com.projectathena.userservice.model.dto;

import com.projectathena.userservice.model.enums.MetricType;

import java.math.BigDecimal;
import java.util.List;

public class DeveloperMetricInfo {

    private String developerUsername;
    private String developerEmail;
    private List<MetricValue> metricValues;
    private MetricType metricType;

    public DeveloperMetricInfo() {
    }

    public DeveloperMetricInfo(String developerUsername, String developerEmail, List<MetricValue> metricValues, MetricType metricType) {
        this.developerUsername = developerUsername;
        this.developerEmail = developerEmail;
        this.metricValues = metricValues;
        this.metricType = metricType;
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

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public void addMetric(BigDecimal value, String description) {
        this.metricValues.add(new MetricValue(value, description));
    }
}
