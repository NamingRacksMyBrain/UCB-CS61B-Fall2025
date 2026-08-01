import java.util.Iterator;
import java.util.Set;

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

        private int compareNode(Node o) {
            return this.key.compareTo(o.key);
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
        throw new UnsupportedOperationException();
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
}
