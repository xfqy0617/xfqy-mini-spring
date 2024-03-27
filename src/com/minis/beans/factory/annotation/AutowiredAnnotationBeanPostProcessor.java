package com.minis.beans.factory.annotation;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.exception.BeansException;

import java.lang.reflect.Field;

public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private BeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 反射获取所有的成员
        Class<?> clz = bean.getClass();
        Field[] fields = clz.getDeclaredFields();

        // 遍历成员, 并寻找带有Autowired注解的成员进行注入
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object ref = beanFactory.getBean(field.getName());
                try {
                    field.setAccessible(true);
                    field.set(bean, ref);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
