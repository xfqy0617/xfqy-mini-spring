package com.test;

import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.exception.BeansException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml", false);
        openThread(context);
        AServiceImpl aService = (AServiceImpl) context.getBean("aService");
        aService.sayHello();
        aService.getRef1().hello();
        aService.getRef1().getBaseBaseService().hello();
        System.out.println(aService);
    }

    private static void openThread(ClassPathXmlApplicationContext context) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String beanName = scanner.nextLine();
                Object bean = context.getBean(beanName);
                System.out.println(bean);
            }
        }).start();
    }
}
