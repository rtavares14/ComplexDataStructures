package nl.saxion.cds.data_structures.map;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxHashMap;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.list.MyDoublyLinkedList;
import nl.saxion.cds.data_structures.list.MyArrayList;

public class MyHashMap<K, V> implements SaxHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 14;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;
    private MyDoublyLinkedList<Entry<K, V>>[] buckets;
    private int size;
    private Entry<K, V> nullKeyEntry;

    private static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }

    /**
     * Constructor to initialize the map with the default capacity.
     */
    public MyHashMap() {
        this.buckets = new MyDoublyLinkedList[INITIAL_CAPACITY];
        this.size = 0;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new MyDoublyLinkedList<>();
        }
    }

    /**
     * Determines if the collection has no elements
     * @return if the collection has no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Determines the number of elements in this collection
     * @return size of this collection
     */
    @Override
    public int size() {
        return size + (nullKeyEntry != null ? 1 : 0);
    }

    /**
     * Create a String representation of the data in GraphViz format.
     */
    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");

        if (nullKeyEntry != null) {
            sb.append("  null -> ").append(nullKeyEntry.value).append(";\n");
        }

        for (MyDoublyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                sb.append("  ").append(entry.key).append(" -> ").append(entry.value).append(";\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    /**
     * Check if the key is part of this map.
     * Uses K.equals() to check for equality.
     */
    @Override
    public boolean contains(Object key) {
        if (key == null) {
            return nullKeyEntry != null;
        }

        int index = getBucketIndex(key);
        MyDoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Get a value which is mapped to the key.
     * @param key key which is mapped to value to be found
     * @return the value mapped to the key or null if the key is not found
     */
    @Override
    public V get(K key) {
        if (key == null) {
            return (nullKeyEntry != null) ? nullKeyEntry.value : null;
        }

        int index = getBucketIndex(key);
        MyDoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Add the value which will be mapped to the key.
     * @param key   key which is mapped to value
     * @param value the value to add
     * @throws DuplicateKeyException if the key is already part of the collection
     */
    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        if (key == null) {
            if (nullKeyEntry != null) {
                throw new DuplicateKeyException("Duplicate key: null");
            }
            nullKeyEntry = new Entry<>(null, value);
            size++;
            return;
        }

        if (size >= buckets.length * LOAD_FACTOR_THRESHOLD) {
            resize();
        }

        int index = getBucketIndex(key);
        MyDoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                throw new DuplicateKeyException("Duplicate key: " + key);
            }
        }

        bucket.addLast(new Entry<>(key, value));
        size++;
    }

    /**
     * Remove the key-value pair from the map.
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws KeyNotFoundException if the key is not found
     */
    @Override
    public V remove(Object key) throws KeyNotFoundException {
        if (key == null) {
            if (nullKeyEntry != null) {
                V value = nullKeyEntry.value;
                nullKeyEntry = null;
                size--;
                return value;
            }
            throw new KeyNotFoundException("Key not found: null");
        }

        int index = getBucketIndex(key);
        MyDoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                bucket.remove(entry);
                size--;
                return entry.value;
            }
        }
        throw new KeyNotFoundException("Key not found: " + key);
    }

    /**
     * Get a list of all the keys in the map.
     * @return a list of all the keys in the map
     */
    @Override
    public SaxList<K> getKeys() {
        MyDoublyLinkedList<K> keysList = new MyDoublyLinkedList<>();

        if (nullKeyEntry != null) {
            keysList.addLast(null);
        }

        for (MyDoublyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keysList.addLast(entry.key);
            }
        }

        return keysList;
    }

    /**
     * Get the index of the bucket for the key.
     */
    private int getBucketIndex(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % buckets.length;
    }

    /**
     * Resize the buckets array when the load factor exceeds the threshold.
     */
    private void resize() {
        int newCapacity = buckets.length * 2;
        MyDoublyLinkedList<Entry<K, V>>[] newBuckets = new MyDoublyLinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new MyDoublyLinkedList<>();
        }

        if (nullKeyEntry != null) {
            newBuckets[0].addLast(nullKeyEntry);
        }

        for (MyDoublyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = entry.key == null ? 0 : Math.abs(entry.key.hashCode()) % newCapacity;
                newBuckets[newIndex].addLast(entry);
            }
        }

        this.buckets = newBuckets;
    }

    /**
     * Get a list of all the values in the map.
     * @return a list of all the values in the map
     */
    public MyArrayList<V> values() {
        MyArrayList<V> valuesList = new MyArrayList<>();

        for (MyDoublyLinkedList<Entry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    valuesList.addLast(entry.getValue());
                }
            }
        }

        return valuesList;
    }
}
