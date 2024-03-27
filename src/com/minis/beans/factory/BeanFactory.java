package com.minis.beans.factory;

import com.minis.exception.BeansException;

/**
 * bean工厂
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String name);

    void registerBean(String beanName, Object o);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);

}
