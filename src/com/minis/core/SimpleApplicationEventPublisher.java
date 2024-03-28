package com.minis.core;

import com.minis.context.ApplicationEvent;
import com.minis.context.ApplicationEventPublisher;
import com.minis.context.ApplicationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的事件发布者
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {
    List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        // 将一个事件推送给订阅该发布者的所有监听器
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener applicationListener) {
        listeners.add(applicationListener);
    }

}
