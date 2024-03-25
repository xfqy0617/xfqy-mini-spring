package com.minis.beans;

import com.minis.exception.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();


    @Override
    public Object getBean(String beanName) {
        Object singleton = getSingleton(beanName);
        if (singleton == null) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException("can not found bean" + beanName);
            }

            try {
                singleton = createBean(beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
            registerSingleton(beanName, singleton);
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
        if (!beanDefinition.isLazyInit()) {
            getBean(beanName);
        }
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
        Object obj = null;

        try {
            clz = Class.forName(bd.getClassName());

            //handle constructor
            obj = handleConstructor(bd, clz);

            //handle properties
            handleProperties(bd, clz, obj);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return obj;

    }

    private Object handleConstructor(BeanDefinition bd, Class<?> clz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor;
        Object obj;
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
        return obj;
    }

    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        //handle properties
        System.out.println("handle properties for bean : " + bd.getBeanName());
        PropertyValues pvs = bd.getPropertyValues();
        if (!pvs.isEmpty()) {
            for (int i = 0; i < pvs.size(); i++) {
                PropertyValue pv = pvs.getPropertyValueList().get(i);
                String pName = pv.getName();
                String pType = pv.getType();
                Object pValue = pv.getValue();
                boolean isRef = pv.isRef();
                Class<?> type;
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
                    type = Class.forName(pType);
                    val = getBean((String) pValue);
                }

                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                Method method = clz.getMethod(methodName, type);
                method.invoke(obj, val);
            }
        }

    }


}
