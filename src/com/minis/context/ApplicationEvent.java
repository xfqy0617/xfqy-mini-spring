package com.minis.context;

import java.util.EventObject;

public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    protected String msg = null;

    /**
     * Constructs a prototypical Event.
     */
    public ApplicationEvent(Object obj) {
        super(obj);
        this.msg = obj.toString();
    }
}
