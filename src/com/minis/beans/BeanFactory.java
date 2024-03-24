package com.minis.beans;

import com.minis.exception.BeansException;

public interface BeanFactory {

    Object getBean(String id) throws BeansException;

    boolean containsBean(String name);

    void registerBean(String id, Object o);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
