package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxHashMap;
import nl.saxion.cds.collection.SaxList;

public class MyHashMap<K, V> implements SaxHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 14;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;
    private DoublyLinkedList<Entry<K, V>>[] buckets;
    private int size;
    private Entry<K, V> nullKeyEntry;

    private static class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
        this.buckets = new DoublyLinkedList[INITIAL_CAPACITY];
        this.size = 0;
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new DoublyLinkedList<>();
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

        for (DoublyLinkedList<Entry<K, V>> bucket : buckets) {
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
            return nullKeyEntry != null; // Check if the null key entry exists
        }

        int index = getBucketIndex(key);
        DoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return true; // Key found
            }
        }
        return false; // Key not found
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
        DoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null; // Key not found
    }

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
            resize(); // Resize if exceeds threshold
        }

        int index = getBucketIndex(key);
        DoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                throw new DuplicateKeyException("Duplicate key: " + key);
            }
        }

        bucket.addLast(new Entry<>(key, value)); // Add to the end
        size++;
    }

    @Override
    public V remove(Object key) throws KeyNotFoundException {
        if (key == null) {
            if (nullKeyEntry != null) {
                V value = nullKeyEntry.value;
                nullKeyEntry = null; // Remove null key entry
                size--;
                return value;
            }
            throw new KeyNotFoundException("Key not found: null");
        }

        int index = getBucketIndex(key);
        DoublyLinkedList<Entry<K, V>> bucket = buckets[index];

        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                bucket.remove(entry); // Remove from bucket
                size--;
                return entry.value; // Return removed value
            }
        }
        throw new KeyNotFoundException("Key not found: " + key);
    }

    @Override
    public SaxList<K> getKeys() {
        DoublyLinkedList<K> keysList = new DoublyLinkedList<>();

        if (nullKeyEntry != null) {
            keysList.addLast(null);
        }

        for (DoublyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                keysList.addLast(entry.key);
            }
        }

        return keysList;
    }

    private int getBucketIndex(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % buckets.length; // Calculate index
    }

    private void resize() {
        int newCapacity = buckets.length * 2;
        DoublyLinkedList<Entry<K, V>>[] newBuckets = new DoublyLinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new DoublyLinkedList<>();
        }

        // If there's an entry for the null key, place it in the new buckets
        if (nullKeyEntry != null) {
            newBuckets[0].addLast(nullKeyEntry);
        }

        // Rehash all entries the new buckets
        for (DoublyLinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int newIndex = entry.key == null ? 0 : Math.abs(entry.key.hashCode()) % newCapacity;
                newBuckets[newIndex].addLast(entry); // Ensure rehashing is correct
            }
        }

        this.buckets = newBuckets; // Update  buckets reference
    }
}
