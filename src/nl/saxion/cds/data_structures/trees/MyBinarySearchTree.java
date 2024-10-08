package nl.saxion.cds.data_structures.trees;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.collection.SaxBinaryTree;
import nl.saxion.cds.data_structures.list.DoublyLinkedList;

import java.util.Comparator;

public class MyBinarySearchTree<K extends Comparable<K>, V> implements SaxBinaryTree<K, V> {

    private MyBinaryTreeNode<K, V> root;
    private int size;
    private Comparator<K> comparator;

    /**
     * Constructor that initializes the tree with a comparator.
     */
    public MyBinarySearchTree(Comparator<K> comparator) {
        this.root = null;
        this.size = 0;
        this.comparator = comparator;
    }

    public MyBinaryTreeNode<K, V> getRoot() {
        return root; // Ensure you have a getter to access root
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
     * How big is this tree, really?
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
        return get(key) != null;
    }

    /**
     * Get a value which is mapped to the key.
     *
     * @param key key which is mapped to value to be found
     * @return the value mapped to the key or null if the key is not found
     */
    @Override
    public V get(K key) {
        if (root == null) {
            return null;
        }
        return root.get(key, comparator);
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
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }
        MyBinaryTreeNode<K, V> newNode = new MyBinaryTreeNode<>(value, key);
        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        if (contains(key)) {
            throw new DuplicateKeyException("Duplicate key: " + key);
        }

        root.add(newNode, comparator);
        size++;
    }


    /**
     * Remove the value which is mapped to the key from the collection.
     *
     * @param key key which is mapped to the value
     * @return the value which is removed from the collection
     * @throws KeyNotFoundException if the key is not part of the collection
     */
    @Override
    public V remove(K key) throws KeyNotFoundException {
        if (root == null) {
            throw new KeyNotFoundException("Key not found: " + key);
        }

        // Check if the root itself is the node to remove
        if (comparator.compare(key, root.getKey()) == 0) {
            // Special case: Removing the root node
            V removedValue = root.getValue();

            // Handle different cases of root removal
            if (root.isLeaf()) {
                root = null;  // Root is a leaf, so tree becomes empty
            } else if (root.getLeft() == null) {
                root = root.getRight();  // Root has only a right child
            } else if (root.getRight() == null) {
                root = root.getLeft();  // Root has only a left child
            } else {
                // Root has two children, find the in-order successor
                MyBinaryTreeNode<K, V> minNode = root.findMin(root.getRight());
                root.setKey(minNode.getKey());
                root.setValue(minNode.getValue());
                // Remove the in-order successor from the right subtree
                root.getRight().remove(minNode.getKey(), comparator, root);
            }

            size--;
            return removedValue;

        } else {
            // Normal case: Delegating to node's remove method
            V removedValue = root.remove(key, comparator, null);
            if (removedValue == null) {
                throw new KeyNotFoundException("Key not found: " + key);
            }
            size--;
            return removedValue;
        }
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
    private void addKeysToList(MyBinaryTreeNode<K, V> node, DoublyLinkedList<K> list) {
        if (node != null) {
            addKeysToList(node.getLeft(), list);
            list.addLast(node.getKey());
            addKeysToList(node.getRight(), list);
        }
    }

    /**
     * Another GraphViz method.
     *
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz() {
        return graphViz("MyBinarySearchTree");
    }

    /**
     * Create a String representation of the data in GraphViz format.
     *
     * @param name name of the produced graph
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");
        graphVizHelper(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private void graphVizHelper(MyBinaryTreeNode<K, V> node, StringBuilder sb) {
        if (node != null) {
            // Add the node even if it doesn't have any children (single node case)
            sb.append("\"").append(node.getKey()).append("\";\n");

            // If the node has a left child, create the edge
            if (node.getLeft() != null) {
                sb.append("\"").append(node.getKey()).append("\" -> \"").append(node.getLeft().getKey()).append("\";\n");
            }

            // If the node has a right child, create the edge
            if (node.getRight() != null) {
                sb.append("\"").append(node.getKey()).append("\" -> \"").append(node.getRight().getKey()).append("\";\n");
            }

            // Recurse on the left and right children
            graphVizHelper(node.getLeft(), sb);
            graphVizHelper(node.getRight(), sb);
        }
    }

    /**
     * Print an in-order traversal of the tree.
     */
    public void inorder() {
        inorderRecursive(root);
    }

    private void inorderRecursive(MyBinaryTreeNode<K, V> node) {
        if (node != null) {
            inorderRecursive(node.getLeft());
            System.out.println(node.getKey() + ": " + node.getValue());
            inorderRecursive(node.getRight());
        }
    }
}
