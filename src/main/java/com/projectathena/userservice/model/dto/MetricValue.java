package com.projectathena.userservice.model.dto;

import java.math.BigDecimal;

public class MetricValue {
    private BigDecimal value;
    private String description;

    public MetricValue() {
    }

    public MetricValue(BigDecimal value, String description) {
        this.value = value;
        this.description = description;
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
}
