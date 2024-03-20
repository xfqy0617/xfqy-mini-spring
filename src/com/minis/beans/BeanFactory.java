package com.minis.beans;

import com.minis.exception.BeansException;

public interface BeanFactory {

    Object getBean(String id) throws BeansException;

    void registerBeanDefinition(String id, String className);
}
