package com.test;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aService = (AService) context.getBean("aService");
        aService.sayHello();
    }
}
