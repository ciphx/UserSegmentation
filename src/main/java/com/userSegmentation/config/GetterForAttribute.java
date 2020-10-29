package com.userSegmentation.config;

import lombok.Getter;

public enum GetterForAttribute {
    gender("getGender"),
    preference_food("getFoodPreference"),
    order_count("getOrderCount");

    @Getter
    private String getter;

    GetterForAttribute(String getter) {
        this.getter = getter;
    }
}
