package test;

import model.Calculation;
import model.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests calculation results, PEMDAS behavior, and calculation errors.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
class CalculationTest {

    /**
     * The calculation model used by each test.
     */
    private Calculation calculation;

    /**
     * Constructs the calculation test class.
     */
    CalculationTest() {
    }

    /**
     * Creates a calculation model before each test runs.
     */
    @BeforeEach
    void setUp() {
        calculation = new Calculation();
    }

    /**
     * Verifies each supported basic operator.
     */
    @Test
    void calculatesBasicOperators() {
        assertEquals(7.0, calculation.calculate("5 + 2"), 0.000001);
        assertEquals(3.0, calculation.calculate("5 - 2"), 0.000001);
        assertEquals(10.0, calculation.calculate("5 * 2"), 0.000001);
        assertEquals(2.5, calculation.calculate("5 / 2"), 0.000001);
    }

    /**
     * Verifies multiplication before addition.
     */
    @Test
    void followsOperatorPrecedence() {
        assertEquals(14.0, calculation.calculate("2 + 3 * 4"), 0.000001);
    }

    /**
     * Verifies left-to-right evaluation for equal-priority operators.
     */
    @Test
    void followsLeftToRightForEqualPrecedence() {
        assertEquals(8.0, calculation.calculate("20 / 5 * 2"), 0.000001);
        assertEquals(5.0, calculation.calculate("10 - 3 - 2"), 0.000001);
    }

    /**
     * Verifies decimal and negative-number calculations.
     */
    @Test
    void calculatesDecimalsAndNegativeNumbers() {
        assertEquals(-7.0, calculation.calculate("-2.5 * 4 + 3"), 0.000001);
        assertEquals(-4.0, calculation.calculate("8 / -2"), 0.000001);
    }

    /**
     * Verifies that an Expression object can be calculated directly.
     */
    @Test
    void calculatesExpressionObject() {
        Expression expression = new Expression("10 + 6 / 2");

        assertEquals(13.0, calculation.calculate(expression), 0.000001);
    }

    /**
     * Verifies that a single number evaluates to itself.
     */
    @Test
    void calculatesSingleNumber() {
        assertEquals(42.5, calculation.calculate("42.5"), 0.000001);
    }

    /**
     * Verifies that division by zero is rejected.
     */
    @Test
    void divisionByZeroThrowsException() {
        assertThrows(ArithmeticException.class,
                () -> calculation.calculate("10 / 0"));
    }

    /**
     * Verifies that null and malformed expressions are rejected.
     */
    @Test
    void invalidCalculationThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> calculation.calculate((String) null));
        assertThrows(IllegalArgumentException.class,
                () -> calculation.calculate((Expression) null));
        assertThrows(IllegalArgumentException.class,
                () -> calculation.calculate("4 + * 2"));
    }
}
