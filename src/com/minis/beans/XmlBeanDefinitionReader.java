package com.minis.beans;


import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.List;

public class XmlBeanDefinitionReader {
    private static final String ID = "id";
    private static final String CLASS = "class";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String PROPERTY = "property";
    private static final String CONSTRUCTOR_ARG = "constructor-arg";
    private final SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinition(Resource resource) {
        for (Object o : resource) {
            Element ele = (Element) o;
            String beanName = ele.attributeValue(ID);
            String className = ele.attributeValue(CLASS);
            BeanDefinition beanDefinition = new BeanDefinition(beanName, className);

            // 处理属性
            List<Element> propertyElements = ele.elements(PROPERTY);
            PropertyValues pvs = new PropertyValues();
            for (Element e : propertyElements) {
                String pType = e.attributeValue(TYPE);
                String pName = e.attributeValue(NAME);
                String pValue = e.attributeValue(VALUE);
                pvs.addPropertyValue(new PropertyValue(pType, pName, pValue));
            }
            beanDefinition.setPropertyValues(pvs);

            //处理构造器参数
            List<Element> constructorElements = ele.elements(CONSTRUCTOR_ARG);
            ArgumentValues avs = new ArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue(TYPE);
                String aName = e.attributeValue(NAME);
                String aValue = e.attributeValue(VALUE);
                avs.addArgumentValue(new ArgumentValue(aType, aName, aValue));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            simpleBeanFactory.registerBeanDefinition(beanDefinition);
        }
    }

}
