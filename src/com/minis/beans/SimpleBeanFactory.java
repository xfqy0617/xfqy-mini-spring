package com.minis.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    // 初始化实例缓存
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();


    @Override
    public Object getBean(String beanName) {
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
            // 准备从初始化实例缓存中获取
            singleton = earlySingletonObjects.get(beanName);
            if (singleton == null) {
                // 若初始化实例没有值, 则准备创建
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                registerSingleton(beanName, singleton);
                // 预留bean post processor位置
                // step 1: postProcessBeforeInitialization
                // step 2: afterPropertiesSet
                // step 3: init-method
                // step 4: postProcessAfterInitialization
            }
        }

        return singleton;
    }

    @Override
    public boolean containsBean(String name) {
        return containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object o) {
        registerSingleton(beanName, o);
    }

    @Override
    public boolean isSingleton(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        return Objects.equals(BeanDefinition.SCOPE_SINGLETON, beanDefinition.getScope());
    }

    @Override
    public boolean isPrototype(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        return Objects.equals(BeanDefinition.SCOPE_PROTOTYPE, beanDefinition.getScope());
    }

    @Override
    public Class<?> getType(String beanName) {
        Object bean = getBean(beanName);
        return bean.getClass();
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getBeanName();
        beanDefinitionMap.put(beanName, beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    public void removeBeanDefinition(String name) {
        beanDefinitionMap.remove(name);
        beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionMap.get(name);
    }

    private Object createBean(BeanDefinition bd) {
        Class<?> clz = null;
        // 添加基础bean到缓存中
        Object obj = doCreateBean(bd);
        earlySingletonObjects.put(bd.getBeanName(), obj);
        try {
            clz = Class.forName(bd.getClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 假设A,B循环依赖, A先初始化, 然后调用handleProperties注入B
         * 此时B开始初始化, 然后调用handleProperties注入A, 然而A已经添加到初始化缓存中, 故能获取到A的对象, B实例化完成并返回
         * A将B注入到自己的成员变量中, A初始化完成
         */
        handleProperties(bd, clz, obj);
        return obj;

    }


    private Object doCreateBean(BeanDefinition bd) {
        Constructor<?> constructor;
        Object obj = null;
        try {
            Class<?> clz = Class.forName(bd.getClassName());
            ArgumentValues avs = bd.getConstructorArgumentValues();
            if (!avs.isEmpty()) {
                // 参数类型列表
                Class<?>[] paramTypes = new Class<?>[avs.getArgumentCount()];
                // 参数值列表
                Object[] paramValues = new Object[avs.getArgumentCount()];
                // 组装构造方法参数类型列表及参数列表
                for (int i = 0; i < avs.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = avs.getIndexedArgumentValue(i);
                    if (Integer.class.getSimpleName().equals(argumentValue.getType()) || Integer.class.getName().equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if (int.class.getName().equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                constructor = clz.getConstructor(paramTypes);
                obj = constructor.newInstance(paramValues);
            } else {
                obj = clz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        //handle properties
        System.out.println("处理bean的字段注入: " + bd.getBeanName());
        PropertyValues pvs = bd.getPropertyValues();
        if (!pvs.isEmpty()) {
            for (int i = 0; i < pvs.size(); i++) {
                PropertyValue pv = pvs.getPropertyValueList().get(i);
                String pName = pv.getName();
                String pType = pv.getType();
                Object pValue = pv.getValue();
                boolean isRef = pv.isRef();
                Class<?> type = null;
                Object val;
                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        type = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        type = Integer.class;
                    } else if ("int".equals(pType)) {
                        type = int.class;
                    } else {
                        type = String.class;
                    }
                    val = pValue;
                } else {
                    // is ref, create the dependent beans
                    try {
                        type = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    val = getBean((String) pValue);
                }

                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                try {
                    Method method = clz.getMethod(methodName, type);
                    method.invoke(obj, val);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            getBean(beanName);
        }
    }

    public void initBeans() {
        for (BeanDefinition bd : beanDefinitionMap.values()) {
            if (!bd.isLazyInit()) {
                getBean(bd.getBeanName());
            }
        }
    }

}
