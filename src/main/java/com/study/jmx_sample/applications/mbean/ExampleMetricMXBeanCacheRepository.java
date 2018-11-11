package com.study.jmx_sample.applications.mbean;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ExampleMetricMXBeanCacheRepository {

    @Autowired
    private ExampleMetricMbeanServer exampleMetricMbeanServer;

    private final LoadingCache<String, ExampleMetricMXBeanImpl> exampleMetricMXBeanLoadingCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS)
            .removalListener((RemovalListener<String, ExampleMetricMXBeanImpl>) entry -> {
                exampleMetricMbeanServer.unregisterMBean(entry.getKey());
            })
            .build(new CacheLoader<String, ExampleMetricMXBeanImpl>() {
                @Override
                public ExampleMetricMXBeanImpl load(String key) {
                    return exampleMetricMbeanServer.registerMBean(key);
                }
            });

    public ExampleMetricMXBeanImpl getUnchecked(String key) {
        return exampleMetricMXBeanLoadingCache.getUnchecked(key);
    }

    @PreDestroy
    public void unregister() {
    }
}
