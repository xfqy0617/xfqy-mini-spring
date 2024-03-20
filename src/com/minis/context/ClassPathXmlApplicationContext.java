package com.minis.context;

import com.minis.core.*;
import com.minis.exception.BeansException;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        beanFactory = new XmlBeanFactory();
        Resource resource = new ClassPathXmlResource(fileName);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(resource);
    }

    @Override
    public Object getBean(String id) throws BeansException {
        return beanFactory.getBean(id);
    }

    @Override
    public void registerBeanDefinition(String id, String className) {
        beanFactory.registerBeanDefinition(id, className);
    }
}