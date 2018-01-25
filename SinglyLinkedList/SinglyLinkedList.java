/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data)
    throws java.lang.IndexOutOfBoundsException,
    java.lang.IllegalArgumentException {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Invalid Index!");
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        LinkedListNode<T> newLink =  new LinkedListNode(data);
        if (isEmpty()) {
            head = newLink;
            tail = newLink;
            tail.setNext(null);
        } else if (index == 0) {
            newLink.setNext(head);
            head = newLink;
        } else if (index == size) {
            tail.setNext(newLink);
            tail = newLink;
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newLink.setNext(current.getNext());
            current.setNext(newLink);
        }
        size++;
    }

    @Override
    public void addToFront(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        LinkedListNode<T> newHead =  new LinkedListNode(data);
        if (isEmpty()) {
            head = newHead;
            tail = newHead;
            tail.setNext(null);
        } else {
            newHead.setNext(head);
            head = newHead;
        }
        size++;
    }

    @Override
    public void addToBack(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        LinkedListNode<T> newTail =  new LinkedListNode(data);
        if (isEmpty()) {
            head = newTail;
            tail = newTail;
            tail.setNext(null);
        } else {
            tail.setNext(newTail);
            tail = newTail;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index)
    throws java.lang.IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Invalid Index!");
        }
        T datapoint;
        if (size == 1) {
            datapoint = head.getData();
            head = null;
            tail = null;
        } else if (index == 0) {
            datapoint = head.getData();
            LinkedListNode<T> newHead = head.getNext();
            head = newHead;
        } else {
            LinkedListNode<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            LinkedListNode<T> nextlink = current.getNext();
            datapoint = nextlink.getData();
            if (nextlink.getNext() == null) {
                tail = current;
                current.setNext(null);
            } else {
                current.setNext(nextlink.getNext());
            }
        }
        size--;
        return datapoint;
    }

    @Override
    public T removeFromFront() {
        T datapoint;
        if (isEmpty()) {
            datapoint = null;
        } else if (size == 1) {
            datapoint = head.getData();
            head = null;
            tail = null;
        } else {
            datapoint = head.getData();
            LinkedListNode<T> newHead = head.getNext();
            head = newHead;
        }
        size--;
        return datapoint;

    }

    @Override
    public T removeFromBack() {
        T datapoint;
        if (isEmpty()) {
          datapoint = null;
        } else if (size == 1) {
            datapoint = head.getData();
            head = null;
            tail = null;
            size--;
        } else {
            LinkedListNode<T> previous = head;
            LinkedListNode<T> current = head.getNext();
            while (current.getNext() != null){
                previous = current;
                current = current.getNext();
            }
            datapoint = current.getData();
            tail = previous;
            previous.setNext(null);
            size--;
        }
        return datapoint;
    }

    @Override
    public T removeFirstOccurrence(T data)
    throws java.lang.IllegalArgumentException,
    java.util.NoSuchElementException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        }
        T datapoint;
        if (isEmpty()) {
            datapoint = null;
        } else if (size == 1 && head.getData() == data) {
            datapoint = head.getData();
            head = null;
            tail = null;
            size--;
        } else if (head.getData() == data) {
            datapoint = head.getData();
            LinkedListNode<T> newHead = head.getNext();
            head = newHead;
            size--;
        } else {
            LinkedListNode<T> previous = head;
            LinkedListNode<T> current = head.getNext();
            while (current.getData() != data){
                previous = current;
                current = current.getNext();
            }
            datapoint = current.getData();
            if (current.getNext() == null) {
                tail = previous;
                previous.setNext(null);
                size--;
            } else {
                LinkedListNode<T> newLink = current.getNext();
                previous.setNext(newLink);
                size--;
            }
        }
        if (datapoint == null) {
            throw new java.util.NoSuchElementException("Can't find data");
        }
        return datapoint;
    }

    @Override
    public T get(int index) throws java.lang.IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Invalid Index");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size) {
            return tail.getData();
        }
        LinkedListNode<T> current = head;
        T entry = current.getData();
        for (int i = 0; i < index; i++) {
            current = current.getNext();
            entry = current.getData();
        }
        return entry;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        LinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        this.size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
