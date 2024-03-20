package com.minis.core;

import com.minis.exception.BeansException;

public interface BeanFactory {

    Object getBean(String id) throws BeansException;

    void registerBeanDefinition(String id, String className);
}
