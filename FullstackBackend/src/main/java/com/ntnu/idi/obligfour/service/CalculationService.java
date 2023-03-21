package com.ntnu.idi.obligfour.service;

import com.ntnu.idi.obligfour.model.Expression;
import org.springframework.stereotype.Service;

@Service
public class CalculationService {
    public static Double compute(Expression expression) {
        return switch (expression.getOperator()) {
            case '+' -> expression.getNumOne() + expression.getNumTwo();
            case '-' -> expression.getNumOne() - expression.getNumTwo();
            case '*' -> expression.getNumOne() * expression.getNumTwo();
            case '/' -> expression.getNumOne() / expression.getNumTwo();
            default -> null;
        };
    }
}
