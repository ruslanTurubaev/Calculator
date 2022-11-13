package com.example.simplecalculator.data.engine;

import com.example.simplecalculator.data.engine.models.ArithmeticalExpressionEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final String PATTERN_POWER = "-?\\d+\\.?\\d*\\^-?\\d+\\.?\\d*";
    private static final String PATTERN_SQUARE_ROOT = "√\\d+\\.?\\d*";
    private static final String PATTERN_MULTIPLY_OR_DIVIDE = "-?\\d+\\.?\\d*[×÷]-?\\d+\\.?\\d*";
    private static final String PATTERN_ADDITION_OR_SUBTRACTION = "-?\\d+\\.?\\d*[\\+-]-?\\d+\\.?\\d*";
    private static final String PATTERN_SQUARE_ROOT_AFTER_NUMBER = "\\d+\\.?\\d*\\)?√";
    private static final String PATTERN_BRACKET_AFTER_NUMBER  = "\\d+\\.?\\d*\\)?\\(";
    private static final String PATTERN_EXPRESSION_IN_BRACKETS = "\\(.+\\)";

    public ArithmeticalExpressionEngine execute(ArithmeticalExpressionEngine arithmeticalExpressionEngine){
        String arithmeticExpression = arithmeticalExpressionEngine.getArithmeticalExpression();
        arithmeticExpression = calculate(arithmeticExpression);
        arithmeticalExpressionEngine.setArithmeticalExpression(arithmeticExpression);
        return arithmeticalExpressionEngine;
    }

    private String calculate(String arithmeticExpression){
        /*
         * The method prepare given math expression for calculations, at first
         * the method removes all decimal dividers and
         * add multiply operator in front of open brackets and square root, if needed
         */
        arithmeticExpression = checkOnHiddenMultiply(PATTERN_SQUARE_ROOT_AFTER_NUMBER, arithmeticExpression);
        arithmeticExpression = checkOnHiddenMultiply(PATTERN_BRACKET_AFTER_NUMBER, arithmeticExpression);

        if (isExpressionFound(PATTERN_POWER, arithmeticExpression)) {
            arithmeticExpression = executeExpression(PATTERN_POWER, "\\^", arithmeticExpression);
        }

        if (isExpressionFound(PATTERN_SQUARE_ROOT, arithmeticExpression)) {
            arithmeticExpression = executeExpression(PATTERN_SQUARE_ROOT, "√", arithmeticExpression);
        }

        if(arithmeticExpression.contains("(") || arithmeticExpression.contains(")")){
            if (!isExpressionFound(PATTERN_EXPRESSION_IN_BRACKETS, arithmeticExpression)) {
                return "ERROR";
            }
            arithmeticExpression = arithmeticExpression.replaceFirst(PATTERN_EXPRESSION_IN_BRACKETS, getExpressionInBrackets(arithmeticExpression));
        }

        if (isExpressionFound(PATTERN_MULTIPLY_OR_DIVIDE, arithmeticExpression)) {
            arithmeticExpression = executeExpression(PATTERN_MULTIPLY_OR_DIVIDE, "[×÷]", arithmeticExpression);
        }

        if (isExpressionFound(PATTERN_ADDITION_OR_SUBTRACTION, arithmeticExpression)) {
            arithmeticExpression = executeExpression(PATTERN_ADDITION_OR_SUBTRACTION, "[\\+-]", checkOnDoubleOperator(arithmeticExpression));
        }

        arithmeticExpression = checkResult(arithmeticExpression);
        return arithmeticExpression;
    }

    /*
     * The method add multiply operator, if needed
     * the arguments for method are pattern for regex and operator, in front of which there should be multiplication
     * it could be either open bracket or square root
     */
    private String checkOnHiddenMultiply (String pattern, String line){
        StringBuffer stringBuffer = new StringBuffer();
        Matcher matcher = Pattern.compile(pattern).matcher(line);
        while (matcher.find()){
            if (pattern.contains("√")){
                matcher.appendReplacement(stringBuffer, addMultiply(matcher.group(), "√"));
            }
            else {
                matcher.appendReplacement(stringBuffer, addMultiply(matcher.group(), "\\("));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private String addMultiply(String line, String operator){
        String[] arrayString = line.split(operator);
        return arrayString[0] + "×" + operator;
    }

    private String checkOnDoubleOperator(String line){
        if (line.contains("+-")) {
            line = line.replaceAll("\\+-", "-");
        }
        if (line.contains("--")) {
            line = line.replaceAll("--", "+");
        }
        return line;
    }

    /*
     * the method finds substring between the first open bracket and the last close bracket inclusive
     * the substring splits into a char array
     * going though the array the method finds each separate expression in bracket and gets its result
     * the found expression in brackets will be replaced with the received result
     */
    private String getExpressionInBrackets(String line){
        Pattern pattern = Pattern.compile(PATTERN_EXPRESSION_IN_BRACKETS);
        Matcher matcher = pattern.matcher(line);

        char[] charArray;

        String result = "";
        matcher.find();
        result = matcher.group();
        matcher.reset();
        while (matcher.find()){
            charArray = result.toCharArray();
            int start = 0, end;

            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '(') {
                    start = i;
                }
                if (charArray[i] == ')') {
                    end = i;
                    result = result.replace(result.substring(start, end + 1), calculate(result.substring(start + 1, end)));
                    matcher = pattern.matcher(result);
                    break;
                }
            }

        }

        return result;
    }

    /*
     * The method finds each expression to be in compliance with received patter for regex and replace it with its result
     */
    private String executeExpression(String pattern, String operator, String line) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(line);

        while (matcher.find()){
            line = line.replaceFirst(pattern, getIterationResult(matcher.group(), operator));
            matcher = p.matcher(line);
        }

        return line;
    }

    private String getIterationResult(String line, String operator){
        /*
         * The square root calculation required the separate logic
         * The method check whether we need calculate square root firstly
         */
        if(operator.equals("√")){
            double number = Double.parseDouble(line.substring(1));
            number = Math.sqrt(number);
            return String.valueOf(number);
        }

        /*
         * In order to gain two numbers in math expression the method split the received substring around operator's regex
         * In case it is addition or deduction operator and the first number is negative the method will split around the fist char
         * At the result the fist element in array will be empty and method will throw an exception when it will parse the empty element into digit
         * If this is the case, we remove the first minus and add it further
         */
        boolean isStartWithMinus = operator.equals("[\\+-]") && line.startsWith("-");

        if(isStartWithMinus){
            line = line.substring(1);
        }

        String[] arrayString = line.split(operator, 2);

        if(isStartWithMinus){
            arrayString[0] = "-" + arrayString[0];
        }

        double number1 = Double.parseDouble(arrayString[0]);
        double number2 = Double.parseDouble(arrayString[1]);
        double result;

        switch (operator){
            case "\\^":
                result = Math.pow(number1, number2);
                return String.valueOf(result);
            case "[×÷]":
                if(line.contains("÷")){
                    result = number1 / number2;
                }
                else {
                    result = number1 * number2;
                }
                return String.valueOf(result);
            case "[\\+-]":
                if(line.contains("+")){
                    result = number1 + number2;
                }
                else {
                    result = number1 - number2;
                }
                return String.valueOf(result);
            default:
                return "ERROR";
        }
    }

    private boolean isExpressionFound(String pattern, String line) {
        return Pattern.compile(pattern).matcher(line).find();
    }

    private String checkResult(String line){
        /*
         * If there is any math operator remains except minus in front of the result, then input was incorrect
         * the method will return ERROR
         */

        double d;

        if (line.contains("ERROR") || line.contains("^") || line.contains("√") || line.contains("×") || line.contains("÷") || line.contains("+")){
            return "ERROR";
        }

        if (line.contains("-") && !line.startsWith("-")) {
            return "ERROR";
        }

        try {
            d = Double.parseDouble(line);
        }
        catch (Exception e){
            return "ERROR";
        }

        if(d % 1.0 == 0.0){
            return Integer.toString((int) d);
        }

        return line;
    }
}
