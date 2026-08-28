package dataStructures;

/**
 * This class represents a dynamically sized array list.
 *
 * @param <Type> the comparable type of items stored in the list
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public class MyArrayList<Type extends Comparable<Type>> {

    /**
     * The default capacity of the array list.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * The array used to store the elements.
     */
    protected Type[] list;

    /**
     * The current capacity of the array.
     */
    protected int capacity;

    /**
     * The number of elements stored in the array.
     */
    protected int size;

    /**
     * The number of comparisons made while searching the list.
     */
    public long comparisons;

    /**
     * Constructs an empty array list.
     */
    public MyArrayList() {
        capacity = DEFAULT_CAPACITY;
        list = (Type[]) new Comparable[capacity];
        size = 0;
        comparisons = 0;
    }

    /**
     * Inserts an item at the specified index.
     *
     * @param item the item to insert
     * @param index the position to insert the item
     * @throws IllegalArgumentException if the item is null
     */
    public void insert(final Type item, final int index) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null!");
        }

        // Do nothing if the index is invalid.
        if (index < 0 || index > size) {
            return;
        }

        // Increase the capacity if the array is full.
        if (size == capacity) {
            resize();
        }

        // Shift elements to make room for the new item.
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }

        list[index] = item;
        size++;
    }

    /**
     * Removes and returns the item at the specified index.
     *
     * @param index the position of the item to remove
     * @return the removed item, or null if the index is invalid
     */
    public Type remove(final int index) {

        // Do nothing if the index is invalid.
        if (index < 0 || index >= size) {
            return null;
        }

        Type item = list[index];

        // Shift remaining elements to fill the gap.
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }

        list[size - 1] = null;
        size--;

        return item;
    }

    /**
     * Searches the list for the specified item.
     *
     * @param item the item to search for
     * @return true if the item is found, false otherwise
     * @throws IllegalArgumentException if the item is null
     */
    public boolean contains(final Type item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null!");
        }

        // Search each element in the list.
        for (int i = 0; i < size; i++) {
            comparisons++;
            if (list[i].compareTo(item) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Searches the list for the specified item.
     *
     * @param item the item to search for
     * @return the index of the item, or -1 if it is not found
     * @throws IllegalArgumentException if the item is null
     */
    public int indexOf(final Type item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null!");
        }

        // Search each element in the list.
        for (int i = 0; i < size; i++) {
            comparisons++;
            if (list[i].compareTo(item) == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns the item at the specified index.
     *
     * @param index the position of the item
     * @return the item at the specified index, or null if the index is invalid
     */
    public Type get(final int index) {

        // Return null if the index is invalid.
        if (index < 0 || index >= size) {
            return null;
        }

        return list[index];
    }

    /**
     * Updates the item at the specified index.
     *
     * @param index the position of the item
     * @param item the new item to store
     * @throws IllegalArgumentException if the item is null
     */
    public void set(final int index, final Type item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null!");
        }

        // Do nothing if the index is invalid.
        if (index < 0 || index >= size) {
            return;
        }

        list[index] = item;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the list is empty.
     *
     * @return true if the list contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Sorts the elements in the list.
     */
    public void sort() {
        if (size < 2) {
            return;
        }

        insertionSort();
    }

    /**
     * Sorts the list using the insertion sort algorithm.
     */
    private void insertionSort() {
        for (int i = 1; i < size; i++) {
            for(int j = i; j > 0 && list[j].compareTo(list[j - 1]) < 0 ; j--) {
                swap(j, j - 1);
            }
        }
    }

    /**
     * Swaps two elements in the list.
     *
     * @param j the index of the first element
     * @param j2 the index of the second element
     */
    private void swap(final int j, final int j2) {
        Type temp = list[j];
        list[j] = list[j2];
        list[j2] = temp;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return the contents of the list enclosed in square brackets
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");

        // Append each element separated by commas.
        for (int i = 0; i < size; i++) {
            sb.append(list[i]);

            if (i != size - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");

        return sb.toString();
    }

    /**
     * Doubles the capacity of the array.
     */
    protected void resize() {
        capacity *= 2;

        Type[] reSizeArray = (Type[]) new Comparable[capacity];

        // Copy each element into the larger array.
        for (int i = 0; i < size; i++) {
            reSizeArray[i] = list[i];
        }

        list = reSizeArray;
    }

}
