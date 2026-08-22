package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int itemsCount = 0;
    private int bucketsCount;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.bucketsCount = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n != null) {
            n.value = value;
            return;
        }

        buckets[bucketIndex(key.hashCode())].add(new Node(key, value));
        itemsCount++;

        if ((double) itemsCount / (double) bucketsCount > loadFactor) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        Node n = getNode(key);
        if (n == null) {
            return null;
        }
        return n.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return itemsCount;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        buckets = new Collection[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            buckets[i] = createBucket();
        }
        itemsCount = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node getNode(K key) {
        for (Node n : buckets[bucketIndex(key.hashCode())]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private int bucketIndex(int num) {
        return Math.floorMod(num, bucketsCount);
    }

    private void resize() {
        int resizeFactor = 3;
        bucketsCount *= resizeFactor;
        Collection<Node>[] newBuckets = new Collection[bucketsCount];
        for (int i = 0; i < bucketsCount; i++) {
            newBuckets[i] = createBucket();
        }

        for (Collection<Node> bucket : buckets) {
            for (Node n : bucket) {
                newBuckets[bucketIndex(n.key.hashCode())].add(n);
            }
        }

        buckets = newBuckets;
    }
}
