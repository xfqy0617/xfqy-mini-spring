package com.minis.beans.factory.config;

import com.minis.beans.factory.BeanFactory;
import com.minis.exception.BeansException;

public interface BeanPostProcessor {

    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

    void setBeanFactory(BeanFactory beanFactory);
}
