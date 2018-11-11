package com.study.jmx_sample.applications.mbean;

import com.study.jmx_sample.applications.metric.ExampleMetric;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkState;

@Getter
@ToString
@RequiredArgsConstructor
public class ExampleMetricMXBeanImpl implements ExampleMetricMXBean {
    private final String key;
    private int value = 0;

    public ExampleMetric apply(ExampleMetric metric) {
        checkState(this.key.equals(metric.getPath()));

        synchronized (this.key) {
            value += metric.getValue();
        }

        return metric;
    }
}
