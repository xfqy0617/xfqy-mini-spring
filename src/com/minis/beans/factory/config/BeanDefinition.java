package com.minis.beans.factory.config;

import com.minis.beans.PropertyValues;

import static com.minis.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

public class BeanDefinition {

    private final String beanName;
    private final String className;
    private String scope = SCOPE_SINGLETON;

    /**
     * 懒加载
     */
    private boolean lazyInit = false;
    /**
     * 依赖的bean
     */
    private String[] dependsOn;
    /**
     * 构造器
     */
    private ConstructorArgumentValues constructorArgumentValues;
    /**
     * setter注入
     */
    private PropertyValues propertyValues;

    private String initMethodName;
    private volatile Object beanClass;

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

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String[] getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String[] dependsOn) {
        this.dependsOn = dependsOn;
    }

    public ConstructorArgumentValues getConstructorArgumentValues() {
        return constructorArgumentValues;
    }

    public void setConstructorArgumentValues(ConstructorArgumentValues constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public Object getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Object beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
