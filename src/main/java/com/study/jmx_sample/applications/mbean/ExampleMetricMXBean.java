package com.study.jmx_sample.applications.mbean;

/**
 * Note: xxxMBean 같이 MBean이라는 접미사로 끝나는 이름을 가진 인터페이스를 만들어서 구현하는법과 DynamicMBean 을 상속받아 구현하는 법이 있다.
 */
public interface ExampleMetricMXBean {
    int getValue();
}
