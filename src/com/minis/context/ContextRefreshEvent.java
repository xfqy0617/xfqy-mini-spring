package com.minis.context;

public class ContextRefreshEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param obj
     */
    public ContextRefreshEvent(Object obj) {
        super(obj);
    }
}
