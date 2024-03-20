package com.minis.beans;

public class BeanDefinition {
    private final String beanName;
    private final String className;

    public BeanDefinition(String beanName, String className) {
        this.beanName = beanName;
        this.className = className;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getClassName() {
        return className;
    }
}
