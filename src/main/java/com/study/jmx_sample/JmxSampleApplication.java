package com.study.jmx_sample;

import com.study.jmx_sample.applications.scan.ScanBaseInterface;
import com.study.jmx_sample.applications.scan.SpecificBean;
import com.study.jmx_sample.applications.scan.SpecificBeanRegister;
import com.study.jmx_sample.applications.scan.SpecificClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class JmxSampleApplication {

    @Autowired
    private SpecificBeanRegister specificBeanRegister;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public static void main(String[] args) {
        SpringApplication.run(JmxSampleApplication.class, args);
    }

    @PostConstruct
    public void init() {
        specificBeanRegister.specificBeanRegist(ScanBaseInterface.class, SpecificBean.class);
        SpecificClass specificClass = (SpecificClass) beanFactory.getBean(SpecificClass.class.getName());

        specificClass.print("test");
    }
}
