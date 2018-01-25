import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
/**
 * Your implementation of HashMap.
 *
 * @author Maddie Ravichandran
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
    * Hash function to find index
    *
    * @param key of map entry
    * @return index for table
    */
    private int hashValue(K key) {
        return (Math.abs(key.hashCode()) % table.length);
    }

    /**
    * Hash function to find index for quadratic probing
    * @param key of map entry
    * @param i index we are currently probing from
    * @return index for table
    */
    private int quadraticProbingValue(K key, int i) {
        return (Math.abs(key.hashCode() + (i * i)) % table.length);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;

    }

    @Override
    public V put(K key, V value) throws java.lang.IllegalArgumentException {
        if (key == null || value == null) {
            throw new
            java.lang.IllegalArgumentException("K and V can't be null");
        }
        V answer = null;
        //check if we need to double
        double addingOne = ((double) size + 1) / table.length;
        if (addingOne > MAX_LOAD_FACTOR) {
            int newLength = (2 * table.length) + 1;
            resizeBackingTable(newLength);
        }
        //we have resized if needed and called put, we come here
        int index = hashValue(key);
        boolean added = false;
        MapEntry<K, V> entry = new MapEntry<>(key, value);
        if (table[index] == null) { //nothing there? add it
            table[index] = entry;
            size++;
        } else if (table[index].getKey().equals(key)
            && table[index].isRemoved()) {
            table[index].setValue(value);
            size++;
            table[index].setRemoved(false);
        } else if (table[index] != null) {
            if (table[index].getKey().equals(key)) {
                MapEntry<K, V> oldEntry = table[index];
                answer = oldEntry.getValue();
                table[index].setValue(value);
                return answer;
            } else {
                int count = 0;
                while (!added && count < table.length) {
                    int qIndex = quadraticProbingValue(key, count);
                    if (table[qIndex] == null) {
                        table[qIndex] = entry;
                        size++;
                        added = true;
                    } else if (table[qIndex].getKey().equals(key)
                        && table[qIndex].isRemoved()) {
                        table[qIndex].setRemoved(false);
                        table[qIndex].setValue(value);
                        size++;
                        added = true;
                    } else if (table[qIndex] != null) {
                        if (table[qIndex].getKey().equals(key)) {
                            MapEntry<K, V> oldEnt = table[qIndex];
                            answer = oldEnt.getValue();
                            table[qIndex].setValue(value);
                            return answer;
                        }
                    }
                    count++;
                }
                if (!added) {
                    int regrow = (2 * table.length) + 1;
                    resizeBackingTable(regrow);
                    put(key, value);
                }
            }
        }
        return answer;
    }

    @Override
    public V remove(K key) throws java.lang.IllegalArgumentException,
    java.util.NoSuchElementException {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("key cant be null");
        }
        V answer = null;
        boolean removed = false;
        int index = hashValue(key);

        if (table[index] != null) {
            if (table[index].getKey().equals(key) && table[index].isRemoved()) {
                return answer;
            }
            if (table[index].getKey().equals(key)
                && !table[index].isRemoved()) {
                answer = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return answer;
            } else { //something there; not what we want; Q.P.
                int count = 0;
                while (!removed && count < table.length) {
                    int qIndex = quadraticProbingValue(key, count);
                    if (table[qIndex] != null && !table[qIndex].isRemoved()) {
                        if (table[qIndex].getKey().equals(key)) {
                            answer = table[qIndex].getValue();
                            table[qIndex].setRemoved(true);
                            size--;
                            removed = true;
                            return answer;
                        }
                    }
                    count++;
                }
                if (count == table.length) {
                    throw new java.util.NoSuchElementException("Key not found");
                }
            }
        } else {
            throw new java.util.NoSuchElementException("Key not found");
        }
        return answer;
    }

    @Override
    public V get(K key) throws java.lang.IllegalArgumentException,
    java.util.NoSuchElementException {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("key cant be null");
        }
        boolean notFound = true;
        V answer = null;
        int index = hashValue(key);
        if (table[index] != null) {
            if (table[index].getKey().equals(key) && table[index].isRemoved()) {
                throw new java.util.NoSuchElementException("Can't get key");
            } else if (table[index].getKey().equals(key)
                && !table[index].isRemoved()) {
                notFound = false;
                return table[index].getValue();
            } else {
                int count = 0;
                while (notFound && count < table.length) {
                    int qIndex = quadraticProbingValue(key, count);
                    if (table[qIndex] != null) {
                        if (table[qIndex].getKey().equals(key)) {
                            notFound = false;
                            return table[qIndex].getValue();
                        }
                    }
                    count++;
                }
                if (count == table.length) {
                    throw new java.util.NoSuchElementException("Key not found");
                }
            }
        } else {
            throw new java.util.NoSuchElementException("key not found!");
        }
        return answer;
    }

    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("key cant be null");
        }
        boolean answer = false;
        int index = hashValue(key);
        if (table[index] != null) {
            if (table[index].getKey().equals(key) && table[index].isRemoved()) {
                answer = false;
                return answer;
            } else if (table[index].getKey().equals(key)
                && !table[index].isRemoved()) {
                answer = true;
                return answer;
            } else {
                int count = 0;
                while (!answer && count < table.length) {
                    int qIndex = quadraticProbingValue(key, count);
                    if (table[qIndex] != null) {
                        if (table[qIndex].getKey().equals(key)
                            && !table[qIndex].isRemoved()) {
                            answer = true;
                            return answer;
                        }
                    }
                    count++;
                }
            }
        }
        return answer;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            if (entry != null && !entry.isRemoved()) {
                set.add(entry.getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> array = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> entry = table[i];
            if (entry != null && !entry.isRemoved()) {
                array.add(entry.getValue());
            }
        }
        return array;
    }

    @Override
    public void resizeBackingTable(int length) throws IllegalArgumentException {
        if (length < size || length < 0) {
            throw new
            java.lang.IllegalArgumentException("length must be > 0 or > size");
        }
        int oldLength = table.length;
        MapEntry<K, V>[] oldTable = table;
        MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[length];

        table = newTable;
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            MapEntry<K, V> entry = oldTable[i];
            if (entry != null && !entry.isRemoved()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
