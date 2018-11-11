package com.study.jmx_sample.metric;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

@Slf4j
@Component
public class ExampleMetricJMXManager {

    @Autowired
    private ExampleMetricCalculator metricCalculator;

    private final LoadingCache<String, ExampleMetricMXBeanImpl> exampleMetricMXBeanLoadingCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .removalListener((RemovalListener<String, ExampleMetricMXBeanImpl>) entry -> {
                final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                String key = entry.getKey();
                try {
                    final ObjectName objectName = getObjectName(key);
                    server.unregisterMBean(objectName);
                } catch (Exception e) {
                    log.info("unregisterMBean error : {}" + key);
                }
            })
            .build(new CacheLoader<String, ExampleMetricMXBeanImpl>() {
                @Override
                public ExampleMetricMXBeanImpl load(String key) throws Exception {
                    ExampleMetricMXBeanImpl result = new ExampleMetricMXBeanImpl(key);
                    final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                    final ObjectName objectName = getObjectName(key);
                    server.registerMBean(result, objectName);

                    log.info("mBean registered : {}" + key);

                    return result;
                }
            });

    private ObjectName getObjectName(String key) {
        try {
            return new ObjectName("com.java.metric:type=Test,name=" + key);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @PreDestroy
    public void unregister() {
        exampleMetricMXBeanLoadingCache.invalidateAll();
    }

    public ExampleMetric applyMetric(int value) {
        ExampleMetric metric = metricCalculator.calculate(value);

        ExampleMetricMXBeanImpl exampleMetricMXBean = exampleMetricMXBeanLoadingCache.getUnchecked(metric.getKey());
        exampleMetricMXBean.apply(metric);

        return metric;
    }

    @Getter
    @RequiredArgsConstructor
    public static class ExampleMetricMXBeanImpl implements ExampleMetricMXBean {
        private final String key;
        private int value = 0;

        public void apply(ExampleMetric metric) {
            checkState(this.key.equals(metric.getKey()));

            synchronized (this.key) {
                value += metric.getValue();
            }
        }
    }
}