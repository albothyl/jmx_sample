package com.study.jmx_sample;

import com.study.jmx_sample.metric.Metrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {Metrics.class})
public class JmxSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmxSampleApplication.class, args);
    }
}
