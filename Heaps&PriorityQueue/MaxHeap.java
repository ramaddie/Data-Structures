/**
 * Your implementation of a max heap.
 *
 * @author MADDIE RAVICHANDRAN
 * @version 1.0
 */
public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial length of {@code INITIAL_CAPACITY} for the
     * backing array.
     *
     * Use the constant field in the interface. Do not use magic numbers!
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;

    }

    @Override
    public void add(T item) throws IllegalArgumentException {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("data cant be null");
        }
        if (size + 1 >= backingArray.length) {
            int newSize = (int) ((backingArray.length * 2) + 1);
            T[] temp = (T[]) new Comparable[newSize];
            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        int index = size;
        backingArray[index] = item;
        bubbleUp();
    }
    /**
    * Helper Method that places a newly inserted element in right spot
    * so parent is > children always for max heap
    */
    private void bubbleUp() {
        int index = size;
        while (hasParent(index)
            && parentValue(index).compareTo(backingArray[index]) < 0) {
            swap(index, parentIndex(index));
            index = parentIndex(index);
        }
    }

    /**
    * Helper Method that swaps the items in the two given index
    * @param index1 index of first item to switch
    * @param index2 index of second item to switch to
    */
    private void swap(int index1, int index2) {
        T tmp = backingArray[index1];
        backingArray[index1] = backingArray[index2];
        backingArray[index2] = tmp;
    }

    /**
    * Helper Method that returns if current index has parent
    * @param i index to be checked
    * @return true if it has a parent
    */
    private boolean hasParent(int i) {
        return i > 1;
    }

    /**
    * Helper Method that returns parents left-child index
    * @param i index to be checked
    * @return index of the left child
    */
    private int leftIndex(int i) {
        return i * 2;
    }

    /**
    * Helper Method that returns parents right-child index
    * @param i index to be checked
    * @return index of the right child
    */
    private int rightIndex(int i) {
        return i * 2 + 1;
    }

    /**
    * Helper Method that checks if a left child exists
    * @param i index to be checked
    * @return true if left child exists
    */
    private boolean hasLeftChild(int i) {
        return leftIndex(i) <= size;
    }

    /**
    * Helper Method that checks if a right child exists
    * @param i index to be checked
    * @return true if right child exists
    */
    private boolean hasRightChild(int i) {
        return rightIndex(i) <= size;
    }

    /**
    * Helper Method that returns parent's value of index put in
    * @param i index to be checked
    * @return value of parent of i
    */
    private T parentValue(int i) {
        return backingArray[parentIndex(i)];
    }

    /**
    * Helper Method that returns parent's index
    * @param i index to be checked
    * @return index of the parent
    */
    private int parentIndex(int i) {
        return i / 2;
    }

    @Override
    public T remove() throws java.util.NoSuchElementException {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Can't remove if empty");
        }
        T answer = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        bubbleDown();
        return answer;
    }
    /**
    * Helper Method that places a root in right spot
    * so parent is > children always for max heap
    */
    private void bubbleDown() {
        int index = 1;
        boolean foo = true;
        while (hasLeftChild(index) && foo) {
            int largerChild = leftIndex(index); //assume L is larger than parent
            if (hasRightChild(index)
                && backingArray[leftIndex(index)].compareTo(
                backingArray[rightIndex(index)]) < 0) { //check if right bigger
                largerChild = rightIndex(index);
            }
            if (backingArray[index].compareTo(backingArray[largerChild]) < 0) {
                swap(index, largerChild);
            } else {
                foo = false;
            }
            index = largerChild;
        }
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
        T[] temp = (T[]) new Comparable[INITIAL_CAPACITY];
        backingArray = temp;
        this.size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        // DO NOT CHANGE THIS METHOD!
        return backingArray;
    }

}
