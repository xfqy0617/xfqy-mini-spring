package com.minis.core;


import org.dom4j.Element;

public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinition(Resource resource) {
        for (Object o : resource) {
            Element ele = (Element) o;
            String beanName = ele.attributeValue("id");
            String className = ele.attributeValue("class");
            beanFactory.registerBeanDefinition(beanName, className);
        }
    }

}
