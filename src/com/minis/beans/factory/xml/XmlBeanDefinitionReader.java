package com.minis.beans.factory.xml;


import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.support.AbstractBeanFactory;
import com.minis.core.Resource;
import com.minis.util.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlBeanDefinitionReader {
    private static final String ID = "id";
    private static final String CLASS = "class";
    private static final String TYPE = "type";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String REF = "ref";
    private static final String PROPERTY = "property";
    private static final String CONSTRUCTOR_ARG = "constructor-arg";
    private final AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
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
            List<String> refs = new ArrayList<>();
            for (Element e : propertyElements) {
                String type = e.attributeValue(TYPE);
                String name = e.attributeValue(NAME);
                String val = e.attributeValue(VALUE);
                String ref = e.attributeValue(REF);
                boolean isRef = false;
                if (StringUtils.isNotBlank(ref)) {
                    isRef = true;
                    val = ref;
                    refs.add(ref);
                }
                pvs.addPropertyValue(new PropertyValue(type, name, val, isRef));
            }
            beanDefinition.setPropertyValues(pvs);
            String[] refArr = refs.toArray(new String[0]);
            beanDefinition.setDependsOn(refArr);

            //处理构造器参数
            List<Element> constructorElements = ele.elements(CONSTRUCTOR_ARG);
            ConstructorArgumentValues avs = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String type = e.attributeValue(TYPE);
                String name = e.attributeValue(NAME);
                String val = e.attributeValue(VALUE);
                avs.addArgumentValue(new ConstructorArgumentValue(type, name, val));
            }
            beanDefinition.setConstructorArgumentValues(avs);
            beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
