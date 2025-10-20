package com.projectathena.userservice.model.dto;

import com.projectathena.userservice.model.enums.MetricType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class MetricValue implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal value;
    private String description;
    private MetricType metricType;

    public MetricValue() {
    }

    public MetricValue(BigDecimal value, String description, MetricType metricType) {
        this.value = value;
        this.description = description;
        this.metricType = metricType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }
}
