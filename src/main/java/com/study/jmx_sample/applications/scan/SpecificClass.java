package com.study.jmx_sample.applications.scan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpecificBean(myValue = "SpecificClass")
public class SpecificClass {

    public void print(String value) {
        log.info("#### value : {}", value);
    }
}
