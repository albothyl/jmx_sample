package com.study.jmx_sample.metric;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class metricController {

    @Autowired
    private ExampleMetricJMXManager jmxManager;

    @RequestMapping(method = RequestMethod.GET, value = "/metric/add/{value}")
    public ExampleMetric addMetricValue(@PathVariable("value") int value) {
        log.info("input metric value : {}", value);

        ExampleMetric exampleMetric = jmxManager.applyMetric(value);

        return exampleMetric;
    }
}
