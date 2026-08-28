package test;

import dataStructures.MyQueueList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the public operations of {@link MyQueueList}, including FIFO order,
 * empty-queue behavior, item lookup, null handling, and clearing the queue.
 *
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
class MyQueueListTest {

    /**
     * The queue used by each test.
     */
    private MyQueueList<Integer> queue;

    /**
     * Constructs the queue test class.
     */
    MyQueueListTest() {
    }

    /**
     * Creates an empty queue before each test runs.
     */
    @BeforeEach
    void setUp() {
        queue = new MyQueueList<>();
    }

    /**
     * Verifies that a newly constructed queue is empty.
     */
    @Test
    void newQueueStartsEmpty() {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
    }

    /**
     * Verifies that enqueue adds items and updates the queue size.
     */
    @Test
    void enqueueAddsItemsAndUpdatesSize() {
        queue.enqueue(10);
        queue.enqueue(20);

        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());
    }

    /**
     * Verifies that dequeue removes items in first-in, first-out order.
     */
    @Test
    void dequeueReturnsItemsInFirstInFirstOutOrder() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals(10, queue.dequeue());
        assertEquals(20, queue.dequeue());
        assertEquals(30, queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    /**
     * Verifies that peek returns the front item without removing it.
     */
    @Test
    void peekReturnsFrontWithoutRemovingIt() {
        queue.enqueue(10);
        queue.enqueue(20);

        assertEquals(10, queue.peek());
        assertEquals(10, queue.peek());
        assertEquals(2, queue.size());
    }

    /**
     * Verifies that contains identifies only items currently in the queue.
     */
    @Test
    void containsFindsOnlyQueuedItems() {
        queue.enqueue(10);
        queue.enqueue(20);

        assertTrue(queue.contains(10));
        assertTrue(queue.contains(20));
        assertFalse(queue.contains(30));
        assertFalse(queue.contains(null));
    }

    /**
     * Verifies that enqueue ignores null items.
     */
    @Test
    void enqueueIgnoresNullItems() {
        queue.enqueue(null);

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    /**
     * Verifies that clear removes every queued item and resets the size.
     */
    @Test
    void clearRemovesEveryItem() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        queue.clear();

        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertNull(queue.dequeue());
        assertFalse(queue.contains(10));
    }

    /**
     * Verifies that the string representation lists items from front to rear.
     */
    @Test
    void toStringListsItemsInQueueOrder() {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        assertEquals("[10, 20, 30]", queue.toString());
    }
}
