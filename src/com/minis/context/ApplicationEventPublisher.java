package com.minis.context;

/**
 * 事件发布者
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     */
    void publishEvent(ApplicationEvent event);

    /**
     * 添加一个事件监听器
     */
    void addApplicationListener(ApplicationListener applicationListener);

}
