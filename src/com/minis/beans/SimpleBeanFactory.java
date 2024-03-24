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
        Object object = null;
        Constructor<?> constructor = null;

        try {
            clz = Class.forName(bd.getClassName());

            //handle constructor
            ArgumentValues argumentValues = bd.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                // 组装构造方法参数类型列表及参数列表
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
//                    if (String.class.getSimpleName().equals(argumentValue.getType()) || String.class.getName().equals(argumentValue.getType())) {
//                        paramTypes[i] = String.class;
//                        paramValues[i] = argumentValue.getValue();
//                    } else

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

                try {
                    constructor = clz.getConstructor(paramTypes);
                    object = constructor.newInstance(paramValues);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                object = clz.newInstance();
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //handle properties
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.size(); i++) {
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();

                Class<?>[] paramTypes = new Class<?>[1];
                if (String.class.getSimpleName().equals(pType) || String.class.getName().equals(pType)) {
                    paramTypes[0] = String.class;
                } else if (Integer.class.getSimpleName().equals(pType) || Integer.class.getName().equals(pType)) {
                    paramTypes[0] = Integer.class;
                } else if (int.class.getName().equals(pType)) {
                    paramTypes[0] = int.class;
                } else {
                    paramTypes[0] = String.class;
                }

                Object[] paramValues = new Object[1];
                paramValues[0] = pValue;

                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);

                Method method = null;
                try {
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    method.invoke(object, paramValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }


        return object;

    }





}
