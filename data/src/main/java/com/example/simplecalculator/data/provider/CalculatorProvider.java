package com.example.simplecalculator.data.provider;

import com.example.simplecalculator.data.engine.Calculator;
import com.example.simplecalculator.data.engine.models.ArithmeticalExpressionEngine;
import com.example.simplecalculator.domain.models.ArithmeticalExpressionDomain;
import com.example.simplecalculator.domain.provider.ProviderInterface;

public class CalculatorProvider implements ProviderInterface {
    private Calculator calculator;

    public CalculatorProvider(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public ArithmeticalExpressionDomain calculate(ArithmeticalExpressionDomain arithmeticalExpressionDomain) {
        ArithmeticalExpressionEngine arithmeticalExpressionEngine = mapToEngine(arithmeticalExpressionDomain);
        arithmeticalExpressionEngine = calculator.execute(arithmeticalExpressionEngine);
        return mapToDomain(arithmeticalExpressionEngine);
    }

    private ArithmeticalExpressionEngine mapToEngine(ArithmeticalExpressionDomain arithmeticalExpressionDomain){
        String arithmeticalExpression = arithmeticalExpressionDomain.getArithmeticExpression();
        return new ArithmeticalExpressionEngine(arithmeticalExpression);
    }

    private ArithmeticalExpressionDomain mapToDomain(ArithmeticalExpressionEngine arithmeticalExpressionEngine){
        String arithmeticalExpression = arithmeticalExpressionEngine.getArithmeticalExpression();
        return new ArithmeticalExpressionDomain(arithmeticalExpression);
    }
}
