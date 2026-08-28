package dataStructures;

/**
 * This class represents a bounded last-in, first-out (LIFO) stack backed by
 * a {@link MyArrayList}.
 *
 * @param <Type> the comparable type of items stored in the stack
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public class MyStackList<Type extends Comparable<Type>> {

    /**
     * The maximum number of items that the stack can store.
     */
    public static final int MAX_ITEMS = 100;

    /**
     * The list that stores the stack items from bottom to top.
     */
    private MyArrayList<Type> stack;

    /**
     * The number of items currently stored in the stack.
     */
    private int size;

    /**
     * Constructs an empty stack.
     */
    public MyStackList() {
        stack = new MyArrayList<>();
        size = 0;
    }

    /**
     * Adds an item to the top of the stack.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if the item is null
     * @throws IllegalStateException if the stack is full
     */
    public void push(final Type item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null!");
        }
        if (isFull()) {
            throw new IllegalStateException("Stack can not contain more than "
                    + MAX_ITEMS + " items!");
        }

        stack.insert(item, size);
        size++;
    }

    /**
     * Removes and returns the item at the top of the stack.
     *
     * @return the newest stack item, or null if the stack is empty
     */
    public Type pop() {
        if (isEmpty()) {
            return null;
        }

        Type item = stack.remove(size - 1);
        size--;
        return item;
    }

    /**
     * Returns the item at the top without removing it.
     *
     * @return the newest stack item, or null if the stack is empty
     */
    public Type peek() {
        if (isEmpty()) {
            return null;
        }

        return stack.get(size - 1);
    }

    /**
     * Checks whether the stack contains a specified item.
     *
     * @param item the item to find
     * @return true if the item is in the stack; false otherwise
     */
    public boolean contains(final Type item) {
        if (isEmpty() || item == null) {
            return false;
        }

        return stack.contains(item);
    }

    /**
     * Checks whether the stack contains no items.
     *
     * @return true if the stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks whether the stack has reached its maximum size.
     *
     * @return true if the stack contains {@link #MAX_ITEMS} items;
     *         false otherwise
     */
    public boolean isFull() {
        return size == MAX_ITEMS;
    }

    /**
     * Returns the number of items stored in the stack.
     *
     * @return the stack size
     */
    public int size() {
        return size;
    }

    /**
     * Removes every item from the stack.
     */
    public void clear() {
        stack = new MyArrayList<>();
        size = 0;
    }

    /**
     * Returns a string containing the stack items from bottom to top.
     *
     * @return the stack contents enclosed in square brackets
     */
    @Override
    public String toString() {
        return stack.toString();
    }
}
