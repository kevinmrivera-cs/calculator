package test;

import dataStructures.MyStackList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the public operations and maximum size of {@link MyStackList}.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
class MyStackListTest {

    /**
     * The stack used by each test.
     */
    private MyStackList<Integer> stack;

    /**
     * Constructs the stack test class.
     */
    MyStackListTest() {
    }

    /**
     * Creates an empty stack before each test runs.
     */
    @BeforeEach
    void setUp() {
        stack = new MyStackList<>();
    }

    /**
     * Verifies that a newly constructed stack is empty.
     */
    @Test
    void newStackStartsEmpty() {
        assertTrue(stack.isEmpty());
        assertFalse(stack.isFull());
        assertEquals(0, stack.size());
        assertNull(stack.peek());
        assertNull(stack.pop());
    }

    /**
     * Verifies that push adds items and updates the stack size.
     */
    @Test
    void pushAddsItemsAndUpdatesSize() {
        stack.push(10);
        stack.push(20);

        assertFalse(stack.isEmpty());
        assertEquals(2, stack.size());
    }

    /**
     * Verifies that pop removes items in last-in, first-out order.
     */
    @Test
    void popReturnsItemsInLastInFirstOutOrder() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    /**
     * Verifies that peek returns the top item without removing it.
     */
    @Test
    void peekReturnsTopWithoutRemovingIt() {
        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek());
        assertEquals(20, stack.peek());
        assertEquals(2, stack.size());
    }

    /**
     * Verifies that contains identifies only items in the stack.
     */
    @Test
    void containsFindsOnlyStackItems() {
        stack.push(10);
        stack.push(20);

        assertTrue(stack.contains(10));
        assertTrue(stack.contains(20));
        assertFalse(stack.contains(30));
        assertFalse(stack.contains(null));
    }

    /**
     * Verifies that push rejects a null item.
     */
    @Test
    void pushRejectsNullItems() {
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
        assertTrue(stack.isEmpty());
    }

    /**
     * Verifies that the stack enforces its maximum number of items.
     */
    @Test
    void stackEnforcesMaximumSize() {
        for (int item = 0; item < MyStackList.MAX_ITEMS; item++) {
            stack.push(item);
        }

        assertTrue(stack.isFull());
        assertEquals(MyStackList.MAX_ITEMS, stack.size());
        assertThrows(IllegalStateException.class,
                () -> stack.push(MyStackList.MAX_ITEMS));
    }

    /**
     * Verifies that clear removes every item and resets the stack.
     */
    @Test
    void clearRemovesEveryItem() {
        stack.push(10);
        stack.push(20);
        stack.clear();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
        assertNull(stack.peek());
    }

    /**
     * Verifies that the string representation lists bottom to top.
     */
    @Test
    void toStringListsItemsFromBottomToTop() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals("[10, 20, 30]", stack.toString());
    }
}
