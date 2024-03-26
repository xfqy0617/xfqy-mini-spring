package com.minis.context;

import com.minis.beans.*;
import com.minis.core.*;
import com.minis.exception.BeansException;

public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private final SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // 创建一个最简单的bean工厂
        beanFactory = new SimpleBeanFactory();
        // 从xml中获取bean相关的资源
        Resource resource = new ClassPathXmlResource(fileName);
        // 创建一个xml资源的读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载资源到容器中
        reader.loadBeanDefinition(resource);
        if (isRefresh) {
            beanFactory.refresh();
        }
    }

    @Override
    public Object getBean(String id) throws BeansException {
        return beanFactory.getBean(id);
    }

    @Override
    public boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    @Override
    public void registerBean(String id, Object o) {
        beanFactory.registerBean(id, o);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return beanFactory.getType(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
