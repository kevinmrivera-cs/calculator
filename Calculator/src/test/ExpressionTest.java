package test;

import model.Expression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests expression validation and conversion from infix to postfix notation.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
class ExpressionTest {

    /**
     * Constructs the expression test class.
     */
    ExpressionTest() {
    }

    /**
     * Verifies that multiplication has priority over addition.
     */
    @Test
    void postfixFollowsOperatorPrecedence() {
        Expression expression = new Expression("2 + 3 * 4");

        assertEquals("[2, 3, 4, *, +]", expression.toPostfix().toString());
    }

    /**
     * Verifies that equal-priority operators remain left associative.
     */
    @Test
    void postfixPreservesLeftToRightOrderForEqualPrecedence() {
        Expression expression = new Expression("20 / 5 * 2");

        assertEquals("[20, 5, /, 2, *]", expression.toPostfix().toString());
    }

    /**
     * Verifies that decimal and negative numbers are valid tokens.
     */
    @Test
    void postfixSupportsDecimalsAndNegativeNumbers() {
        Expression expression = new Expression("-2.5 * 4 + 3");

        assertEquals("[-2.5, 4, *, 3, +]", expression.toPostfix().toString());
    }

    /**
     * Verifies that a negative number can follow an operator.
     */
    @Test
    void postfixSupportsNegativeRightOperand() {
        Expression expression = new Expression("8 / -2");

        assertEquals("[8, -2, /]", expression.toPostfix().toString());
    }

    /**
     * Verifies that toString returns the trimmed original expression.
     */
    @Test
    void toStringReturnsOriginalExpression() {
        Expression expression = new Expression("  12.5 + 7  ");

        assertEquals("12.5 + 7", expression.toString());
    }

    /**
     * Verifies that invalid expressions are rejected.
     */
    @Test
    void invalidExpressionsAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Expression(""));
        assertThrows(IllegalArgumentException.class,
                () -> new Expression("2 +"));
        assertThrows(IllegalArgumentException.class,
                () -> new Expression("2 3"));
        assertThrows(IllegalArgumentException.class,
                () -> new Expression("1..2 + 3"));
        assertThrows(IllegalArgumentException.class,
                () -> new Expression("2 ^ 3"));
    }

    /**
     * Verifies that parentheses are rejected in the current version.
     */
    @Test
    void parenthesesAreNotSupportedYet() {
        assertThrows(IllegalArgumentException.class,
                () -> new Expression("(2 + 3) * 4"));
    }
}
