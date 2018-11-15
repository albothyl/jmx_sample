package com.study.jmx_sample.applications.mbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.management.*;
import java.lang.management.ManagementFactory;

@Slf4j
@Service
public class ExampleMetricMbeanServer {

    /**
     * vmOption: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false
     */
    private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    public ExampleMetricMXBeanImpl registerMBean(String key) {
        ExampleMetricMXBeanImpl result = new ExampleMetricMXBeanImpl(key);
        ObjectName objectName = getObjectName(key);

        try {
            server.registerMBean(result, objectName);
        } catch (Exception e) {
            log.error("mBean register failed : {}", key, e);
        }

        log.info("mBean registered : {}", key);

        return result;
    }

    public void unregisterMBean(String key) {
        ObjectName objectName = getObjectName(key);

        try {
            server.unregisterMBean(objectName);

            log.info("mBean unRegistered : {}", key);
        } catch (Exception e) {
            log.error("mBean unRegister failed : {}", key, e);
        }
    }

    private ObjectName getObjectName(String key) {
        try {
            return new ObjectName("com.java.metric:type=Count,name=" + key);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
