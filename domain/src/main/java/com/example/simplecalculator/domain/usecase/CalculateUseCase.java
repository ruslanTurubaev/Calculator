package com.example.simplecalculator.domain.usecase;

import com.example.simplecalculator.domain.models.ArithmeticalExpressionDomain;
import com.example.simplecalculator.domain.models.ArithmeticalExpressionUI;
import com.example.simplecalculator.domain.provider.ProviderInterface;

public class CalculateUseCase {

    private ProviderInterface provider;

    public CalculateUseCase(ProviderInterface provider) {
        this.provider = provider;
    }

    public ArithmeticalExpressionDomain execute(ArithmeticalExpressionUI arithmeticalExpressionUI){
        ArithmeticalExpressionDomain arithmeticalExpressionDomain = mapToDomain(arithmeticalExpressionUI);

        return provider.calculate(arithmeticalExpressionDomain);
    }

    private ArithmeticalExpressionDomain mapToDomain(ArithmeticalExpressionUI arithmeticalExpressionUI){
        String arithmeticExpression = arithmeticalExpressionUI.getArithmeticExpression();
        arithmeticExpression = arithmeticExpression.replaceAll(" ", "");

        return new ArithmeticalExpressionDomain(arithmeticExpression);
    }


}
