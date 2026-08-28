package dataStructures;

/**
 * Represents a first-in, first-out (FIFO) queue backed by a
 * {@link MyArrayList}. Null items are ignored when they are enqueued.
 *
 * @param <Type> the comparable type of items stored in the queue
 * @author Kevin Munoz-Rivera
 * @version Summer 2026
 */
public class MyQueueList <Type extends Comparable<Type>>{

    /**
     * The list that stores the queued items.
     */
    private MyArrayList<Type> queue;

    /**
     * The number of items currently stored in the queue.
     */
    private int size;

    /**
     * Constructs an empty queue.
     */
    public MyQueueList(){
        queue = new MyArrayList<>();
        size = 0;
    }

    /**
     * Adds an item to the rear of the queue. A null item is ignored.
     *
     * @param item the item to add
     */
    public void enqueue(Type item){
        if(item == null){
            return;
        }
        queue.insert(item, size);
        size++;
    }

    /**
     * Removes and returns the item at the front of the queue.
     *
     * @return the oldest queued item, or null if the queue is empty
     */
    public Type dequeue(){
        if(size == 0){
            return null;
        }
        Type item = queue.remove(0);
        size--;
        return item;
    }

    /**
     * Returns the item at the front without removing it.
     *
     * @return the oldest queued item, or null if the queue is empty
     */
    public Type peek(){
        if(size == 0){
            return null;
        }
        return queue.get(0);
    }

    /**
     * Checks whether the queue contains a specified item.
     *
     * @param item the item to find
     * @return true if the item is in the queue; false otherwise
     */
    public boolean contains(Type item){
        if(size == 0 || item == null){
            return false;
        }
        return queue.contains(item);
    }

    /**
     * Checks whether the queue contains no items.
     *
     * @return true if the queue is empty; false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Returns the number of items stored in the queue.
     *
     * @return the queue size
     */
    public int size(){
        return size;
    }

    /**
     * Removes every item from the queue.
     */
    public void clear() {
        queue = new MyArrayList<>();
        size = 0;
    }

    /**
     * Returns a string containing the queued items from front to rear.
     *
     * @return the queue contents enclosed in square brackets
     */
    @Override
    public String toString(){
        return queue.toString();
    }
}
