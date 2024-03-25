package com.test;

public class BaseService {
    private BaseBaseService bbs;
    void hello() {
        System.out.println("hello, I'm BaseService");
    }

    public BaseBaseService getBbs() {
        return bbs;
    }

    public void setBbs(BaseBaseService bbs) {
        this.bbs = bbs;
    }
}
