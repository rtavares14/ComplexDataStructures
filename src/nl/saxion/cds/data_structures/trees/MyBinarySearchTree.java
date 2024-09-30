package nl.saxion.cds.data_structures.trees;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxBinaryTree;
import nl.saxion.cds.collection.SaxList;

public class MyBinarySearchTree<K extends Comparable<K>, V> implements SaxBinaryTree<K, V> {



    /**
     * Check if the key is part of this map.
     * Uses K.equals() to check for equality.
     *
     * @param key key to search for
     * @return if the key is in this collection
     */
    @Override
    public boolean contains(K key) {
        return false;
    }

    /**
     * Get a value which is mapped to the key.
     *
     * @param key key which is mapped to value to be found
     * @return the value mapped to the key or null if the key is not found
     */
    @Override
    public V get(K key) {
        return null;
    }

    /**
     * Add the value which will be mapped to the key.
     * A duplicate key will throw a DuplicateKeyException.
     *
     * @param key   key which is mapped to value
     * @param value the value to add
     * @throws DuplicateKeyException if the key is already part of the collection
     */
    @Override
    public void add(K key, V value) throws DuplicateKeyException {

    }

    /**
     * Remove the value which is mapped with the key from the collection
     *
     * @param key key which is mapped to value
     * @return the value which is removed from the collection
     * @throws KeyNotFoundException if the key is not part oif the collection
     */
    @Override
    public V remove(K key) throws KeyNotFoundException {
        return null;
    }

    @Override
    public SaxList<K> getKeys() {
        return null;
    }

    /**
     * Determines if the collection has no elements
     *
     * @return if the collection has no elements
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Determines the number of elements in this collection
     *
     * @return size of this collection
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Create a String representation of the data in GraphViz (see <a href="https://graphviz.org">GraphViz</a>)
     * format, which you can print-copy-paste on the site see <a href="https://dreampuf.github.io/GraphvizOnline">GraphViz online</a>.
     *
     * @param name name of the produced graph
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz(String name) {
        return null;
    }
}
