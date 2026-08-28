package model;

import dataStructures.MyArrayList;
import dataStructures.MyStackList;

/**
 * This class calculates validated mathematical expressions by evaluating
 * their postfix tokens with a stack.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public class Calculation {

    /**
     * Constructs a calculation model.
     */
    public Calculation() {
    }

    /**
     * Calculates an expression entered as text.
     *
     * @param value the expression to calculate
     * @return the calculated result
     * @throws IllegalArgumentException if the expression is invalid
     * @throws ArithmeticException if division by zero or a non-finite result
     *         occurs
     */
    public double calculate(final String value) {
        return calculate(new Expression(value));
    }

    /**
     * Calculates a validated expression.
     *
     * @param expression the expression to calculate
     * @return the calculated result
     * @throws IllegalArgumentException if the expression is null or invalid
     * @throws ArithmeticException if division by zero or a non-finite result
     *         occurs
     */
    public double calculate(final Expression expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression can not be null!");
        }

        MyArrayList<String> postfix = expression.toPostfix();
        MyStackList<Double> values = new MyStackList<>();

        for (int index = 0; index < postfix.size(); index++) {
            String token = postfix.get(index);

            if (!Expression.isOperatorToken(token)) {
                values.push(Double.valueOf(token));
                continue;
            }

            if (values.size() < 2) {
                throw new IllegalArgumentException("Expression is incomplete!");
            }

            double right = values.pop();
            double left = values.pop();
            values.push(applyOperator(left, right, token));
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException("Expression is invalid!");
        }

        return values.pop();
    }

    /**
     * Applies an operator to two operands.
     *
     * @param left the operand on the left of the operator
     * @param right the operand on the right of the operator
     * @param operator the operator to apply
     * @return the result of the operation
     * @throws ArithmeticException if division by zero or a non-finite result
     *         occurs
     */
    private double applyOperator(final double left, final double right,
                                 final String operator) {
        double result;

        switch (operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right == 0.0) {
                    throw new ArithmeticException("Can not divide by zero!");
                }
                result = left / right;
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported operator: " + operator);
        }

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Result is outside the supported range!");
        }
        return result;
    }
}
