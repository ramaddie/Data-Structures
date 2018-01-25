/**
 * Your implementation of an array-backed queue.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.back = 0;
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you must not
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() throws java.util.NoSuchElementException {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty");
        }
        T first = backingArray[front];
        backingArray[front] = null;
        front++;
        if (front == backingArray.length) {
            front = 0;
        }
        size--;
        return first;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity. If a regrow is necessary, you should copy
     * elements to the front of the new array and reset front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null!");
        } else if (size + 1 > backingArray.length) {
            int number = (int) ((backingArray.length * 2) + 1);
            T[] temp = (T[]) new Object[number];
            for (int i = 0; i < size; i++) {
                temp[i] = backingArray[front % size];
                front = ((front + 1) % size);
            }
            backingArray = temp;
            back = size;
            front = 0;
        }
        backingArray[(front + size) % backingArray.length] = data;
        size++;
        back = ((back + 1) % backingArray.length);
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY!
        return backingArray;
    }
}
