package com.minis.beans.factory.support;

import com.minis.beans.factory.config.SingletonBeanRegistry;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected final LinkedHashSet<String> beanNames = new LinkedHashSet<>();
    protected final Map<String, Object> singletons = new ConcurrentHashMap<>();


    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        System.out.println("register bean [ " + beanName + " ]");
        synchronized (this.singletons) {
            if (containsSingleton(beanName)) {
                System.out.println("unable to register duplicate beans, bean name:" + beanName);
                return;
            }
            singletons.put(beanName, singletonObject);
            beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }



    public void registerDependentBean(String beanName, String dependentBeanName) {
        // todo ddd

    }

    public String[] getDependentBeans(String beanName) {
        // todo ddd
        return new String[0];
    }

    public String[] getDependenciesForBean(String beanName) {
        // todo ddd
        return new String[0];
    }
}
