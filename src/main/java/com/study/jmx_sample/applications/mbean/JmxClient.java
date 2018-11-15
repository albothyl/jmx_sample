package com.study.jmx_sample.applications.mbean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

@Slf4j
@Service
public class JmxClient {

    private MBeanServerConnection mBeanServer;

    public void getValue(String mBeanName) throws Exception {
        ObjectName objectName = new ObjectName("com.java.metric:type=Count,name=" + mBeanName);

        MBeanInfo mBeanInfo = mBeanServer.getMBeanInfo(objectName);

        MBeanAttributeInfo[] mBeanInfoAttributes = mBeanInfo.getAttributes();

        for (MBeanAttributeInfo mBeanAttributeInfo : mBeanInfoAttributes) {
            log.info("==============================");
            log.info("mBeanAttributeInfo name: {}: {}", mBeanAttributeInfo.getName(), mBeanServer.getAttribute(objectName, mBeanAttributeInfo.getName()));
        }
    }

    @PostConstruct
    private void init() throws Exception {
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:9010/jmxrmi");
        JMXConnector connector = JMXConnectorFactory.connect(address);

        mBeanServer = connector.getMBeanServerConnection();
    }
}
