package com.minis.beans.factory.support;

import com.minis.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

public class SimpleBeanFactory extends AbstractBeanFactory {

    private List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();



    @Override
    protected List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }
}
