/**
 * Your implementation of an ArrayList.
 *
 * @author Maddie Ravichandran
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) throws
    java.lang.IllegalArgumentException,
    java.lang.IndexOutOfBoundsException {
        if (data == null) {
            throw new
            java.lang.IllegalArgumentException("Data can't be null type");
        } else if (index < 0 || index > size) {
            throw new
            java.lang.IndexOutOfBoundsException("Invalid Index Option");
        } else if (index == size) {
            backingArray[index] = data;
        } else if (size + 1 <= backingArray.length) { //dont double
            T[] temp1 = (T[]) new Object[backingArray.length];
            for (int x = 0; x < index; x++) {
                temp1[x] = backingArray[x];
            }
            temp1[index] = data;
            for (int i = index; i <= size; i++) {
                temp1[i + 1] = backingArray[i];
            }
            backingArray = temp1;
        } else {
            T[] temp2 = (T[]) new Object[backingArray.length * 2]; //double cap
            for (int x = 0; x < index; x++) {
                temp2[x] = backingArray[x];
            }
            temp2[index] = data;
            for (int i = index; i <= size - 1; i++) {
                temp2[i + 1] = backingArray[i];
            }
            backingArray = temp2;
        }
        size++;
    }

    @Override
    public void addToFront(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        } else if (size == 0) {
            backingArray[0] = data;
        } else if (size + 1 <= backingArray.length) {
            T[] temp1 = (T[]) new Object[backingArray.length];
            temp1[0] = data;
            for (int x = 1; x <= size; x++) {
                temp1[x] = backingArray[x - 1];
            }
            backingArray = temp1;
        } else {
            T[] temp = (T[]) new Object[backingArray.length * 2];
            temp[0] = data;
            for (int x = 0; x < size; x++) {
                temp[x + 1] = backingArray[x];
            }
            backingArray = temp;
        }
        size++;
    }

    @Override
    public void addToBack(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        } else if (size == backingArray.length) {
            T[] temp = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = backingArray[i];
            }
            temp[size] = data;
            backingArray = temp;
        } else {
            backingArray[size] = data;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index)
    throws java.lang.IndexOutOfBoundsException {
        T answer = backingArray[index];
        if (index < 0 || index >= size) {
            throw new
            java.lang.IndexOutOfBoundsException("Invalid index option");
        }
        T[] temp = (T[]) new Object[backingArray.length];
        for (int i = 0; i < index; i++) {
            temp[i] = backingArray[i];
        }
        for (int x = index; x <= size - 1; x++) {
            temp[x] = backingArray[x + 1];
        }
        backingArray = temp;
        size--;
        return answer;
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        }
        T answer = backingArray[0];
        T[] temp = (T[]) new Object[backingArray.length];
        for (int i = 1; i <= size - 1; i++) {
            temp[i - 1] = backingArray[i];
        }
        backingArray = temp;
        size--;
        return answer;
    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        }
        T answer = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return answer;
    }

    @Override
    public T get(int index) throws java.lang.IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new
            java.lang.IndexOutOfBoundsException("Invalid index option");
        }
        return backingArray[index];
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
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }

}
