package com.minis.beans;

import com.minis.exception.BeansException;

public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String name);

    void registerBean(String beanName, Object o);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
