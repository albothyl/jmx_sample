package com.study.jmx_sample.interfaces;

import com.study.jmx_sample.applications.mbean.JmxClient;
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

    @Autowired
    private JmxClient jmxClient;

    //http://127.0.0.1:8080/metric/add/mbeanSample/15
    @RequestMapping(method = RequestMethod.GET, value = "/metric/add/{path}/{value}")
    public ExampleMetric addMetricValue(@PathVariable("path") String path, @PathVariable("value") int value) {
        log.info("input metric path: {}, value: {}", path, value);

        ExampleMetric exampleMetric = exampleMetricAppender.appender(path, value);

        return exampleMetric;
    }

    //http://127.0.0.1:8080/metric/get/mbeanSample
    @RequestMapping(method = RequestMethod.GET, value = "/metric/get/{mBeanName}")
    public String checkMetricValue(@PathVariable("mBeanName") String mBeanName) {
        try {
            jmxClient.getMetricValue(mBeanName);
        } catch (Exception e) {
            log.error("exception occur", e);
        }

        return "check console";
    }

    //http://127.0.0.1:8080/mbean/get
    @RequestMapping(method = RequestMethod.GET, value = "/mbean/get/{mBeanName}")
    public String checkMbeanValue(@PathVariable("mBeanName") String mBeanName) {
        try {
            jmxClient.getMbeanValue(mBeanName);
        } catch (Exception e) {
            log.error("exception occur", e);
        }

        return "check console";
    }
}
