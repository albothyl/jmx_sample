package com.study.jmx_sample.applications.metric;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExampleMetric {
    private String path;
    private int value;
}
