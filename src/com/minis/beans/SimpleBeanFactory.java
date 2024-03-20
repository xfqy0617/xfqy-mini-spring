package com.minis.beans;

import com.minis.exception.BeansException;

import java.util.HashMap;
import java.util.Map;

public class SimpleBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private Map<String, Object> singletons = new HashMap<>();


    @Override
    public Object getBean(String beanName) throws BeansException {
        Object o = singletons.get(beanName);
        if (o == null) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("can not found bean" + beanName);
            }

            try {
                o = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            singletons.put(beanDefinition.getBeanName(), o);
        }

        return o;
    }

    @Override
    public void registerBeanDefinition(String beanName, String className) {
        BeanDefinition beanDefinition = new BeanDefinition(beanName, className);
        beanDefinitions.put(beanName, beanDefinition);
    }

}
