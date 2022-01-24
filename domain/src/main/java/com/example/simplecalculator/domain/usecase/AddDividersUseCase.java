package com.example.simplecalculator.domain.usecase;

import com.example.simplecalculator.domain.models.ArithmeticalExpressionUI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDividersUseCase {

    /*
     * The method through a loop finds each number in received expression and replaces it with the same but with digit dividers
     */
    public ArithmeticalExpressionUI execute(ArithmeticalExpressionUI arithmeticalExpressionUI){
        String arithmeticalExpression = arithmeticalExpressionUI.getArithmeticExpression();
        arithmeticalExpression = removeDividers(arithmeticalExpression);

        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher foundNumber = pattern.matcher(arithmeticalExpression);

        StringBuffer numberWithDigitDividers = new StringBuffer();

        while (foundNumber.find()){
            foundNumber.appendReplacement(numberWithDigitDividers, addDividersToFoundNumber(foundNumber.group()));
        }

        foundNumber.appendTail(numberWithDigitDividers);
        return new ArithmeticalExpressionUI(numberWithDigitDividers.toString());
    }

    /*
     * the method adds digit dividers into the received number
     */
    private String addDividersToFoundNumber(String number){
        StringBuilder numberWithDigitDividers;
        String tail = "";

        /*
         * The method checks whether the number contains dot
         * if yes, then split it around the dot
         * digits after dot will be added to the result at the end
         * if no, just convert the received number into stringBuilder
         */
        if (number.contains(".")) {
            String[] arrayString = number.split("\\.");
            numberWithDigitDividers = new StringBuilder(arrayString[0]);
            tail = ".";
            if (arrayString.length > 1) {
                tail = tail + arrayString[1];
            }
        } else {
            numberWithDigitDividers = new StringBuilder(number);
        }

        /*
         * The method goes through each character from the end to the begin within the stringBuilder
         * if the current character is not divider and
         * the next character is not divider and
         * it is not the end of char sequence and
         * the remains of the dividing the current count of digit in number on 3 is 0
         * then add digit divider
         */
        if (numberWithDigitDividers.length() > 3) {
            numberWithDigitDividers.reverse();
            int count = 0;
            for (int i = 0; i < numberWithDigitDividers.length(); i++) {
                if (numberWithDigitDividers.charAt(i) != ' ') {
                    count++;
                }
                if (i + 1 == numberWithDigitDividers.length() ||
                        count % 3 != 0 ||
                        numberWithDigitDividers.charAt(i + 1) == ' ' ||
                        numberWithDigitDividers.charAt(i) == ' ') continue;
                numberWithDigitDividers.insert(i + 1, ' ');
            }
            numberWithDigitDividers.reverse();
        }

        numberWithDigitDividers.append(tail);
        return numberWithDigitDividers.toString();
    }

    private String removeDividers (String str){
        return str.replaceAll(" ", "");
    }
}
