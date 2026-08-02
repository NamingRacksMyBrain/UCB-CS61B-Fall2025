import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        Node(K key, V value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return this.left == null && this.right == null;
        }
    }

    private Node root;
    private int size;

    BSTMap() {
        this.root = null;
        this.size = 0;
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
        root = put(key, value, root);
    }

    // Put the key-value pair, starting from Node N
    private Node put(K key, V value, Node n) {
        if (n == null) {
            size++;
            return new Node(key, value, null, null);
        }

        if (n.key.equals(key)) {
            n.value = value;
        } else if (n.key.compareTo(key) < 0) {
            n.right = put(key, value, n.right);
        } else {
            n.left = put(key, value, n.left);
        }

        return n;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return get(key, root);
    }

    // Get the value, starting from Node N
    private V get(K key, Node n) {
        if (n == null) {
            return null;
        }
        if (n.key.equals(key)) {
            return n.value;
        } else if (n.key.compareTo(key) < 0) {
            return get(key, n.right);
        } else {
            return get(key, n.left);
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    // Returns whether contains KEY, starting from Node N
    private boolean containsKey(K key, Node n) {
        if (n == null) {
            return false;
        }
        if (n.key.equals(key)) {
            return true;
        } else if (n.key.compareTo(key) < 0) {
            return containsKey(key, n.right);
        } else {
            return containsKey(key, n.left);
        }
    }
    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        for (K key : this) {
            keySet.add(key);
        }
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        return null;
    }

    /**
     * Returns an iterator over the keys, in sorted order.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {

        Deque<K> keys = new LinkedList<>();

        // Add the keys of the tree starts at N to KEYS
        void add(Node n) {
            if (n == null) {
            } else if (n.isLeaf()) {
                keys.addLast(n.key);
            } else {
                add(n.left);
                keys.addLast(n.key);
                add(n.right);
            }
        }

        BSTMapIter() {
            add(root);
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !keys.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return keys.removeFirst();
        }
    }
}
