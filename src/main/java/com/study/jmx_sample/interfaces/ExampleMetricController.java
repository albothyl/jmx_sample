package com.study.jmx_sample.interfaces;

import com.study.jmx_sample.applications.metric.ExampleMetric;
import com.study.jmx_sample.applications.metric.ExampleMetricAppender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExampleMetricController {

    @Autowired
    private ExampleMetricAppender exampleMetricAppender;

    @RequestMapping(method = RequestMethod.GET, value = "/metric/add/{path}/{value}")
    public ExampleMetric addMetricValue(@PathVariable("path") String path, @PathVariable("value") int value) {
        log.info("input metric path: {}, value: {}", path, value);

        ExampleMetric exampleMetric = exampleMetricAppender.appender(path, value);

        return exampleMetric;
    }
}
