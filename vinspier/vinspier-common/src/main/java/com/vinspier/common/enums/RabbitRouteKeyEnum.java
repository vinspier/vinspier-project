package com.vinspier.common.enums;

public enum RabbitRouteKeyEnum {
    INSERT("insert"),
    UPDATE("update"),
    DELETE("delete"),
    ITEM("item."),
    ITEM_INSERT("item.insert"),
    ITEM_UPDATE("item.update"),
    ITEM_DELETE("item.delete");

    private String key;

    RabbitRouteKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
