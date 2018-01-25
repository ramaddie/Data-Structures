/**
 * Your implementation of an array-backed stack.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Pop from the stack.
     *
     * Do not shrink the backing array.
     *
     * @see StackInterface#pop()
     */
    @Override
    public T pop() throws java.util.NoSuchElementException {
        if (size == 0) {
            throw new java.util.NoSuchElementException("ArrayStack is empty!");
        }
        T data = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return data;
    }

    /**
     * Push the given data onto the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity.
     *
     * @see StackInterface#push(T)
     */
    @Override
    public void push(T data) throws java.lang.IllegalArgumentException {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data can't be null");
        } else if (size + 1 > backingArray.length) {
            int number = (int) ((backingArray.length * 2) + 1); //regrow 2n+1
            T[] temp = (T[]) new Object[number];
            for (int i = 0; i < size; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[size] = data;
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this stack.
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
