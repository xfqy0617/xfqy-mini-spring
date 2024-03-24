package com.minis.beans;

public interface SingletonBeanRegistry {

    /**
     * 注册单例bean
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例
     */
    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    /**
     * 获取所有的单例beanName
     */
    String[] getSingletonNames();

}
