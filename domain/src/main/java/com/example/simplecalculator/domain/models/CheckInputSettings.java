package com.example.simplecalculator.domain.models;

import java.util.Objects;

public class CheckInputSettings {
    private String currentArithmeticExpression;
    private int cursorPosition;
    char addedCharacter;

    public CheckInputSettings(String currentArithmeticExpression, int cursorPosition, char addedCharacter) {
        this.currentArithmeticExpression = currentArithmeticExpression;
        this.cursorPosition = cursorPosition;
        this.addedCharacter = addedCharacter;
    }

    public String getCurrentArithmeticExpression() {
        return currentArithmeticExpression;
    }

    public void setCurrentArithmeticExpression(String currentArithmeticExpression) {
        this.currentArithmeticExpression = currentArithmeticExpression;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(int cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public char getAddedCharacter() {
        return addedCharacter;
    }

    public void setAddedCharacter(char addedCharacter) {
        this.addedCharacter = addedCharacter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInputSettings that = (CheckInputSettings) o;
        return getCursorPosition() == that.getCursorPosition() && getAddedCharacter() == that.getAddedCharacter() && Objects.equals(getCurrentArithmeticExpression(), that.getCurrentArithmeticExpression());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrentArithmeticExpression(), getCursorPosition(), getAddedCharacter());
    }
}
