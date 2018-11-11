package com.study.jmx_sample.applications.metric;

import com.study.jmx_sample.applications.mbean.ExampleMetricMXBeanCacheRepository;
import com.study.jmx_sample.applications.mbean.ExampleMetricMXBeanImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExampleMetricAppender {

    @Autowired
    private ExampleMetricMXBeanCacheRepository exampleMetricMXBeanCacheRepository;

    public ExampleMetric appender(String metricPath, int metricValue) {
        ExampleMetric metric = createExampleMetric(metricPath, metricValue);

        ExampleMetricMXBeanImpl metricMXBean = findMetricMBeanByPath(metricPath);

        metricMXBean.apply(metric);

        return metric;
    }

    private ExampleMetric createExampleMetric(String metricKey, int metricValue) {
        return ExampleMetric.builder()
                .path(metricKey)
                .value(metricValue)
                .build();
    }

    private ExampleMetricMXBeanImpl findMetricMBeanByPath(String metricPath) {
        return exampleMetricMXBeanCacheRepository.getUnchecked(metricPath);
    }
}
