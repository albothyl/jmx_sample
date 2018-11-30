package com.study.jmx_sample.applications.scan;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SpecificBeanScanner {

    public static List<Class> findTargetClassList(Class baseClass, Class targetAnnotation) {
        try {
            ImmutableSet<ClassPath.ClassInfo> rootClassInformation = findRootClassInformation(baseClass);
            return findTargetClassList(rootClassInformation, targetAnnotation);
        } catch (Exception e) {
            log.info("#### scan failed", e);
        }

        return Lists.newArrayList();
    }

    private static ImmutableSet<ClassPath.ClassInfo> findRootClassInformation(Class baseClass) throws IOException {
        String basePackage = baseClass.getPackage().getName();
        ClassPath classPath = ClassPath.from(baseClass.getClassLoader());

        return classPath.getTopLevelClassesRecursive(basePackage);
    }

    private static List<Class> findTargetClassList(ImmutableSet<ClassPath.ClassInfo> rootClassInformation, Class targetAnnotation) {
        return rootClassInformation.stream()
                .map(classInfo -> classInfo.load())
                .filter(candidateClass -> candidateClass.isAnnotationPresent(targetAnnotation))
                .collect(Collectors.toList());
    }
}
