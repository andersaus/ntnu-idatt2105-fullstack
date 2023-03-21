package com.ntnu.idi.obligfour.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Expression {
    private double numOne;
    private double numTwo;
    private char operator;
    private double result;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(numOne).append(operator).append(numTwo)
                .append("=").append(result);
        return sb.toString();
    }
}
