package com.minis.beans.factory.support;

import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.minis.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) {
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            singleton = beanProcessor.postProcessAfterInitialization(singleton, beanName);
            if (singleton == null) {
                return null;
            }
        }
        return singleton;

    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) {
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            singleton = beanProcessor.postProcessBeforeInitialization(singleton, beanName);
            if (singleton == null) {
                return null;
            }
        }
        return singleton;
    }


}
