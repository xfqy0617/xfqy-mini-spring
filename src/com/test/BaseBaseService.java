package com.test;

public class BaseBaseService {
    private AService as;
    void hello() {
        System.out.println("hello, I'm BaseBaseService");
    }

    public AService getAs() {
        return as;
    }

    public void setAs(AService as) {
        this.as = as;
    }
}
