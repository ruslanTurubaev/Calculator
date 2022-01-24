package com.example.simplecalculator.domain.provider;

import com.example.simplecalculator.domain.models.ArithmeticalExpressionDomain;

public interface ProviderInterface {

    ArithmeticalExpressionDomain calculate(ArithmeticalExpressionDomain arithmeticalExpressionDomain);
}
