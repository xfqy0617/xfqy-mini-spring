package com.test;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml", false);
        AServiceImpl aService = (AServiceImpl) context.getBean("aService");
        aService.sayHello();
        aService.getRef1().hello();
        aService.getRef1().getBaseBaseService().hello();
        System.out.println(aService);
    }
}
