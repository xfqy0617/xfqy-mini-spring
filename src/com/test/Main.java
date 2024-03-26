package com.test;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml", true);
        AServiceImpl aService = (AServiceImpl) context.getBean("aService");
        aService.sayHello();
        aService.getRef1().hello();
        aService.getRef1().getBbs().hello();
        System.out.println(aService);
    }
}
