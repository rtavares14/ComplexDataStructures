package nl.saxion.cds.data_structures.trees;

import java.util.Comparator;
import java.util.Random;

public class MyBinaryTreeNode<K extends Comparable<K>, V> {

    private V value;
    private K key;
    private MyBinaryTreeNode<K, V> left;
    private MyBinaryTreeNode<K, V> right;


    /**
     * Constructor to initialize a node with a value.
     * Left, right, and parent are initialized to null.
     */
    public MyBinaryTreeNode(V value, K key) {
        this.value = value;
        this.key = key;
        this.left = null;
        this.right = null;
    }

    /**
     * Returns the left child of the node.
     */
    public MyBinaryTreeNode<K, V> getLeft() {
        return left;
    }

    public void setLeft(MyBinaryTreeNode<K, V> left) {
        this.left = left;
    }

    /**
     * Returns the right child of the node.
     */
    public MyBinaryTreeNode<K, V> getRight() {
        return right;
    }

    public void setRight(MyBinaryTreeNode<K, V> right) {
        this.right = right;
    }

    /**
     * Returns the value of the node.
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets a new value for the node.
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Returns the key of the node.
     */
    public K getKey() {
        return key;
    }

    /**
     * Sets a new key for the node.
     */
    public void setKey(K key) {
        this.key = key;
    }

    public void add(MyBinaryTreeNode<K, V> node, Comparator<K> comparator) {
        int cmp = comparator.compare(node.getKey(), this.getKey());
        if (cmp < 0) {
            if (this.getLeft() == null) {
                this.setLeft(node);
            } else {
                this.getLeft().add(node, comparator);
            }
        } else if (cmp > 0) {
            if (this.getRight() == null) {
                this.setRight(node);
            } else {
                this.getRight().add(node, comparator);
            }
        }
    }

    public V remove(K key, Comparator<K> comparator, MyBinaryTreeNode<K, V> parent) {
        int cmp = comparator.compare(key, this.getKey());

        if (cmp < 0) {
            // Key is smaller, go left
            if (left != null) {
                return left.remove(key, comparator, this);
            } else {
                return null; // Key not found
            }
        } else if (cmp > 0) {
            // Key is larger, go right
            if (right != null) {
                return right.remove(key, comparator, this);
            } else {
                return null; // Key not found
            }
        } else {
            // Node found (cmp == 0)
            V removedValue = this.value;

            if (isLeaf()) {
                // Case 1: No children (Leaf Node)
                if (parent != null) {
                    if (parent.getLeft() == this) parent.setLeft(null);
                    else parent.setRight(null);
                }
            } else if (left == null) {
                // Case 2: One child (right)
                if (parent != null) {
                    if (parent.getLeft() == this) parent.setLeft(right);
                    else parent.setRight(right);
                }
            } else if (right == null) {
                // Case 2: One child (left)
                if (parent != null) {
                    if (parent.getLeft() == this) parent.setLeft(left);
                    else parent.setRight(left);
                }
            } else {
                // Case 3: Two children (replace with inorder successor)
                MyBinaryTreeNode<K, V> minNode = findMin(right);
                this.key = minNode.getKey();
                this.value = minNode.getValue();
                right.remove(minNode.getKey(), comparator, this); // Remove the in-order successor
            }

            return removedValue; // Return the value of the removed node
        }
    }

    public V get(K key, Comparator<K> comparator) {
        int cmp = comparator.compare(key, this.getKey());

        if (cmp < 0) {
            // Key is smaller, search left
            return (left != null) ? left.get(key, comparator) : null;
        } else if (cmp > 0) {
            // Key is larger, search right
            return (right != null) ? right.get(key, comparator) : null;
        } else {
            // Key matches, return the value
            return this.value;
        }
    }


    /**
     * Checks if the node is a leaf.
     * A node is a leaf if it has no children (both left and right are null).
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public MyBinaryTreeNode<K, V> findMin(MyBinaryTreeNode<K, V> node) {
        if (node == null) return null;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public String toDot() {
        Random random = new Random();
        String dot = "";
        if (left != null) {
            dot += value + " -> " + left.getValue() + " ";
            dot += left.toDot();
        } else {
            String nullNode = "lfg" + getValue() + random.nextInt(1000);
            dot += nullNode + " [shape=point];\n" + getValue() + " -> " + nullNode + " ";
        }
        if (right != null) {
            dot += getValue() + " -> " + right.getValue() + " ";
            dot += right.toDot();
        }
        else {
            String nullNode = "rg" + getValue() + random.nextInt(1000);
            dot += nullNode + " [shape=point];\n" + getValue() + " -> " + nullNode + " ";
        }
        return dot;
    }
}