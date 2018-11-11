package com.study.jmx_sample.metric;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExampleMetric {
    private String key;
    private int value;
}
