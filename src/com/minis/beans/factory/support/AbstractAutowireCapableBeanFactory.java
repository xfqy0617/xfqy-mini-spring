package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }


    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }


    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
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
