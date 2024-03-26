package com.test;

import com.minis.beans.factory.annotation.Autowired;

public class BaseService {
    @Autowired
    private BaseBaseService baseBaseService;
    void hello() {
        System.out.println("hello, I'm BaseService");
    }

    public BaseBaseService getBaseBaseService() {
        return baseBaseService;
    }

    public void setBaseBaseService(BaseBaseService baseBaseService) {
        this.baseBaseService = baseBaseService;
    }
}
