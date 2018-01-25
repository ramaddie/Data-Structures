/**
 * Your implementation of a linked stack.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class LinkedStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public T pop() throws java.util.NoSuchElementException {
        if (size == 0) {
            throw new java.util.NoSuchElementException("LinkedStack is Empty");
        }
        LinkedNode<T> tempNode = head;
        head = head.getNext();
        size--;
        return tempNode.getData();
    }

    @Override
    public void push(T data) throws IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        LinkedNode<T> newNode = new LinkedNode(data);
        if (head != null) {
            newNode.setNext(head);
        }
        head = newNode;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
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
}
