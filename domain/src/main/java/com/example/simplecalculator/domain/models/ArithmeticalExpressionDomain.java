package com.example.simplecalculator.domain.models;

import java.util.Objects;

public class ArithmeticalExpressionDomain {

    private String arithmeticExpression;

    public ArithmeticalExpressionDomain(String arithmeticExpression) {
        this.arithmeticExpression = arithmeticExpression;
    }

    public String getArithmeticExpression() {
        return arithmeticExpression;
    }

    public void setArithmeticExpression(String arithmeticExpression) {
        this.arithmeticExpression = arithmeticExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArithmeticalExpressionDomain that = (ArithmeticalExpressionDomain) o;
        return Objects.equals(getArithmeticExpression(), that.getArithmeticExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArithmeticExpression());
    }
}
