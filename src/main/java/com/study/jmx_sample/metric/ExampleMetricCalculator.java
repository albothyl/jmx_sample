package com.study.jmx_sample.metric;

import org.springframework.stereotype.Service;

@Service
public class ExampleMetricCalculator {

    public ExampleMetric calculate(int value) {
        ExampleMetric metric = ExampleMetric.builder()
                .key("jmx.sample.key")
                .value(value)
                .build();

        return metric;
    }
}
