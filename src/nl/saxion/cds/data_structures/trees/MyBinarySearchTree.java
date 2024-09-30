package nl.saxion.cds.data_structures.trees;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxBinaryTree;
import nl.saxion.cds.data_structures.list.DoublyLinkedList;

public class MyBinarySearchTree<K extends Comparable<K>, V> implements SaxBinaryTree<K, V> {

    // trees don't grow without a root.
    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    /**
     * We all have to start somewhere, even this tree.
     */
    public MyBinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * A tree with no leaves isn't much of a tree at all.
     *
     * @return true if the collection has no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * How big is this thing, really?
     *
     * @return size of this collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Uses K.equals() to check for equality.
     *
     * @param key key to search for
     * @return true if the key is in this tree, false otherwise
     */
    @Override
    public boolean contains(K key) {
        return containsRecursive(root, key);
    }

    private boolean containsRecursive(Node node, K key) {
        if (node == null) return false;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return containsRecursive(node.left, key);
        if (cmp > 0) return containsRecursive(node.right, key);
        return true;
    }

    /**
     * Get a value which is mapped to the key.
     *
     * @param key key which is mapped to value to be found
     * @return the value mapped to the key or null if the key is not found
     */
    @Override
    public V get(K key) {
        return getRecursive(root, key);
    }

    private V getRecursive(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return getRecursive(node.left, key);
        if (cmp > 0) return getRecursive(node.right, key);
        return node.value;
    }

    /**
     * Add the value which will be mapped to the key.
     *
     * @param key   key which is mapped to value
     * @param value the value to add
     * @throws DuplicateKeyException if the key is already part of the collection
     */
    @Override
    public void add(K key, V value) throws DuplicateKeyException {
        root = addRecursive(root, key, value);
    }

    private Node addRecursive(Node node, K key, V value) throws DuplicateKeyException {
        if (node == null) {
            size++;
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = addRecursive(node.left, key, value);
        } else if (cmp > 0) {
            node.right = addRecursive(node.right, key, value);
        } else {
            throw new DuplicateKeyException("Key already exists: " + key);
        }

        return node;
    }


    @Override
    public V remove(K key) throws KeyNotFoundException {

        return null;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Get all the keys in the tree as a doubly linked list.
     *
     * @return a DoublyLinkedList of all the keys in order
     */
    public DoublyLinkedList<K> getKeys() {
        DoublyLinkedList<K> keysList = new DoublyLinkedList<>();
        addKeysToList(root, keysList);
        return keysList;
    }

    /**
     * Recursively add keys to the provided DoublyLinkedList in sorted order.
     *
     * @param node the current node being visited
     * @param list the doubly linked list to add keys to
     */
    private void addKeysToList(Node node, DoublyLinkedList<K> list) {
        if (node != null) {
            addKeysToList(node.left, list);
            list.addLast(node.key);
            addKeysToList(node.right, list);
        }
    }

    /**
     * Create a String representation of the data in GraphViz format.
     *
     * @param name name of the produced graph
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz(String name) {
        return name;
    }
}
