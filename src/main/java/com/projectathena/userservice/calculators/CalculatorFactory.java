package com.projectathena.userservice.calculators;

import com.projectathena.userservice.model.enums.MetricType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CalculatorFactory {

    private final Map<MetricType, Calculator> calculatorMap;

    public CalculatorFactory(List<Calculator> calculators) {
        calculatorMap = new EnumMap<>(MetricType.class);
        for (Calculator calculator : calculators) {
            calculatorMap.put(calculator.getMetricType(), calculator);
        }
    }


    public Optional<Calculator> getCalculator(MetricType type) {
        return Optional.ofNullable(calculatorMap.get(type));
    }
}