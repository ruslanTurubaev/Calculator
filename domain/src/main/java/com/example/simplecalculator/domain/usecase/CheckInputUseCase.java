package com.example.simplecalculator.domain.usecase;

import com.example.simplecalculator.domain.models.CheckInputSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckInputUseCase {
    private static final ArrayList DOT_AND_OPERATORS = new ArrayList( Arrays.asList('+', '-', '×', '÷', '^', '√', '.'));

    /*
     * The method checks whether the user's input is correct
     * The method check input from the beginning till the current cursor position
     * If the character to be added is digit, then input is correct
     * If it is operator but is either minus or square root, then input is correct
     * If it the first character and it is math operator other than minus or square root, then the input is not correct
     * Also if it is dot to be added, the method goes through the last number and check whether this number also contains dot
     * if yes, then input is not correct
     */
    public boolean execute(CheckInputSettings settings){
        String arithmeticExpression = settings.getCurrentArithmeticExpression();
        int cursorPosition = settings.getCursorPosition();
        char addedChar = settings.getAddedCharacter();
        CharSequence editableLine = arithmeticExpression.subSequence(0, cursorPosition);

        if(DOT_AND_OPERATORS.contains(addedChar)){
            if(addedChar == '-' || addedChar == '√'){
                return true;
            }
            else {
                if(editableLine.length() > 0) {
                    if (DOT_AND_OPERATORS.contains(editableLine.charAt(editableLine.length() - 1))) {
                        return false;
                    }
                    else {
                        if (addedChar == '.') {
                            boolean isNumberContainsDot = false;

                            for (int i = editableLine.length() - 1; i >= 0; i--) {
                                //check whether the number is already contains dot
                                if (editableLine.charAt(i) == '.') {
                                    isNumberContainsDot = true;
                                    break;
                                }

                                //check whether the end of number
                                if (DOT_AND_OPERATORS.contains(editableLine.charAt(i))) {
                                    break;
                                }
                            }

                            return !isNumberContainsDot;
                        } else return true;
                    }
                }
                else return false;
            }
        }
        else return true;
    }
}
