package com.userSegmentation.config;


import lombok.Getter;

public enum Operator {
    AND("&&"),
    OR("||"),
    EQ("=="),
    NEQ("!="),
    LT("<"),
    GT(">"),
    GE(">="),
    LE("<=");


    @Getter
    private String operatorValue;

    Operator(String operatorValue){
        this.operatorValue = operatorValue;
    }
}
