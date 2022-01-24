package com.example.simplecalculator.data.engine.models;

import java.util.Objects;

public class ArithmeticalExpressionEngine {

    private String arithmeticalExpression;

    public ArithmeticalExpressionEngine(String arithmeticalExpression) {
        this.arithmeticalExpression = arithmeticalExpression;
    }

    public String getArithmeticalExpression() {
        return arithmeticalExpression;
    }

    public void setArithmeticalExpression(String arithmeticalExpression) {
        this.arithmeticalExpression = arithmeticalExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArithmeticalExpressionEngine that = (ArithmeticalExpressionEngine) o;
        return Objects.equals(getArithmeticalExpression(), that.getArithmeticalExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArithmeticalExpression());
    }
}
