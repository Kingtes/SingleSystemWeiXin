package com.chujiu.json;

/**
 * Created by tianci on 2017/6/1.
 */
public class ModelMsgData {
    private String value;
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ModelMsgData(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
