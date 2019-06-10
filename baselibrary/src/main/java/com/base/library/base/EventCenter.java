package com.base.library.base;

public class EventCenter<T> {
    private String type;
    private T object;

    public EventCenter(String type, T object) {
        this.type = type;
        this.object = object;
    }

    public EventCenter(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "EventCenter{" +
                "type='" + type + '\'' +
                ", object=" + object +
                '}';
    }
}
