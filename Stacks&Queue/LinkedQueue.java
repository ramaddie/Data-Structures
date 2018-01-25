/**
 * Your implementation of a linked queue.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() throws java.util.NoSuchElementException {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue's already empty");
        }
        LinkedNode<T> temp = head;
        if (head.getNext() == null) {
            tail = null;
        }
        head = head.getNext();
        size--;
        return temp.getData();
    }

    @Override
    public void enqueue(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        LinkedNode<T> newNode = new LinkedNode(data);
        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        tail.setNext(null);
        size++;
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
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
