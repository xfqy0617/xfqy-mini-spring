package com.minis.beans.factory.config;

import com.minis.beans.factory.BeanFactory;
import com.minis.exception.BeansException;

/**
 * bean工厂的后置处理器
 */
public interface BeanFactoryPostProcessor {
	void postProcessBeanFactory(BeanFactory beanFactory) throws BeansException;
}
