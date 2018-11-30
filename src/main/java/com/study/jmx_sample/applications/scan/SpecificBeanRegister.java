package com.study.jmx_sample.applications.scan;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SpecificBeanRegister {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public void specificBeanRegist(Class baseClass, Class targetAnnotation) {
        List<Class> targetClassList = SpecificBeanScanner.findTargetClassList(baseClass, targetAnnotation);

        for (Class clazz : targetClassList) {
            try {
                SpecificBean specificBeanAnnotation = (SpecificBean) clazz.getDeclaredAnnotation(targetAnnotation);

                log.info("#### annotation information: {}", specificBeanAnnotation.myValue());

//            for (Constructor declaredConstructor : clazz.getDeclaredConstructors()) {
//                declaredConstructor.
//            }

                beanFactory.registerSingleton(clazz.getName(), clazz.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                log.info("#### {} regist failed", clazz.getName(), e);
            }
        }
    }
}
