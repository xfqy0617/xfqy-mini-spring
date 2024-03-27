package com.minis.core;

public interface Environment extends PropertyResolver{

    /**
     * 获取配置
     */
    String[] getActiveProfiles();

    /**
     * 获取默认配置
     */
    String[] getDefaultProfiles();

    /**
     * 接受配置
     */
    boolean acceptsProfiles(String... profiles);
}
