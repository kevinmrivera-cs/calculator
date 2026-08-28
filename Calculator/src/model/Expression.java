package model;

import dataStructures.MyArrayList;
import dataStructures.MyStackList;

/**
 * This class validates a basic mathematical expression and converts it from
 * infix notation to postfix notation. Supported operators are addition,
 * subtraction, multiplication, and division. Parentheses are not supported.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public class Expression {

    /**
     * The expression entered by the user.
     */
    private final String value;

    /**
     * The validated numbers and operators in their original order.
     */
    private final MyArrayList<String> tokens;

    /**
     * Constructs and validates an expression.
     *
     * @param theValue the expression entered by the user
     * @throws IllegalArgumentException if the expression is null, empty,
     *         contains unsupported characters, or has invalid syntax
     */
    public Expression(final String theValue) {
        if (theValue == null || theValue.isBlank()) {
            throw new IllegalArgumentException("Expression can not be empty!");
        }

        value = theValue.trim();
        tokens = tokenize();
    }

    /**
     * Converts this expression to postfix notation using operator precedence.
     *
     * @return a new list containing the postfix expression
     */
    public MyArrayList<String> toPostfix() {
        MyArrayList<String> postfix = new MyArrayList<>();
        MyStackList<String> operators = new MyStackList<>();

        for (int index = 0; index < tokens.size(); index++) {
            String token = tokens.get(index);

            if (!isOperatorToken(token)) {
                postfix.insert(token, postfix.size());
                continue;
            }

            while (!operators.isEmpty()
                    && precedence(operators.peek()) >= precedence(token)) {
                postfix.insert(operators.pop(), postfix.size());
            }
            operators.push(token);
        }

        while (!operators.isEmpty()) {
            postfix.insert(operators.pop(), postfix.size());
        }

        return postfix;
    }

    /**
     * Checks whether a token is one of the supported operators.
     *
     * @param token the token to inspect
     * @return true if the token is an operator; false otherwise
     */
    static boolean isOperatorToken(final String token) {
        return token != null
                && token.length() == 1
                && isOperator(token.charAt(0));
    }

    /**
     * Splits the expression into validated number and operator tokens.
     *
     * @return the tokens in infix order
     * @throws IllegalArgumentException if the expression syntax is invalid
     */
    private MyArrayList<String> tokenize() {
        MyArrayList<String> result = new MyArrayList<>();
        boolean expectingNumber = true;
        int index = 0;

        while (index < value.length()) {
            index = skipWhitespace(index);
            if (index >= value.length()) {
                break;
            }

            char current = value.charAt(index);
            if (current == '(' || current == ')') {
                throw new IllegalArgumentException(
                        "Parentheses are not supported yet!");
            }

            if (expectingNumber) {
                StringBuilder number = new StringBuilder();

                if (current == '+' || current == '-') {
                    number.append(current);
                    index++;
                }

                boolean foundDigit = false;
                boolean foundDecimal = false;

                while (index < value.length()) {
                    current = value.charAt(index);

                    if (Character.isDigit(current)) {
                        number.append(current);
                        foundDigit = true;
                        index++;
                    } else if (current == '.' && !foundDecimal) {
                        number.append(current);
                        foundDecimal = true;
                        index++;
                    } else {
                        break;
                    }
                }

                if (!foundDigit) {
                    throw new IllegalArgumentException(
                            "Expected a number at position " + index + "!");
                }

                result.insert(number.toString(), result.size());
                expectingNumber = false;
            } else {
                if (!isOperator(current)) {
                    throw new IllegalArgumentException(
                            "Expected an operator at position " + index + "!");
                }

                result.insert(String.valueOf(current), result.size());
                index++;
                expectingNumber = true;
            }
        }

        if (expectingNumber) {
            throw new IllegalArgumentException(
                    "Expression can not end with an operator!");
        }

        return result;
    }

    /**
     * Skips spaces beginning at a specified position.
     *
     * @param start the position at which to begin
     * @return the position of the next non-space character
     */
    private int skipWhitespace(final int start) {
        int index = start;
        while (index < value.length()
                && Character.isWhitespace(value.charAt(index))) {
            index++;
        }
        return index;
    }

    /**
     * Checks whether a character is a supported operator.
     *
     * @param character the character to inspect
     * @return true if the character is an operator; false otherwise
     */
    private static boolean isOperator(final char character) {
        return character == '+' || character == '-'
                || character == '*' || character == '/';
    }

    /**
     * Returns the precedence of an operator.
     *
     * @param operator the operator to inspect
     * @return 2 for multiplication or division; 1 otherwise
     */
    private static int precedence(final String operator) {
        if (operator.equals("*") || operator.equals("/")) {
            return 2;
        }
        return 1;
    }

    /**
     * Returns the original expression entered by the user.
     *
     * @return the expression text
     */
    @Override
    public String toString() {
        return value;
    }
}
