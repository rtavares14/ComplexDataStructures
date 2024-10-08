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
            return (left != null) ? left.remove(key, comparator, this) : null;
        } else if (cmp > 0) {
            // Key is larger, go right
            return (right != null) ? right.remove(key, comparator, this) : null;
        } else {
            // Node to be removed found (cmp == 0)
            V removedValue = this.value;

            // Handling removal based on cases
            if (isLeaf()) {
                // Case 1: No children (Leaf Node)
                removeLeafNode(parent);
            } else if (left == null || right == null) {
                // Case 2: One child (either left or right is null)
                removeSingleChildNode(parent);
            } else {
                // Case 3: Two children
                removeNodeWithTwoChildren(comparator);
            }

            return removedValue;
        }
    }

    private void removeLeafNode(MyBinaryTreeNode<K, V> parent) {
        if (parent != null) {
            if (parent.getLeft() == this) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
    }

    private void removeSingleChildNode(MyBinaryTreeNode<K, V> parent) {
        MyBinaryTreeNode<K, V> child = (left != null) ? left : right;
        if (parent != null) {
            if (parent.getLeft() == this) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
    }

    private void removeNodeWithTwoChildren(Comparator<K> comparator) {
        // Find the minimum node in the right subtree (in-order successor)
        MyBinaryTreeNode<K, V> minNode = findMin(right);

        // Replace the current node's key and value with the in-order successor
        this.key = minNode.getKey();
        this.value = minNode.getValue();

        // Recursively remove the in-order successor from the right subtree
        right.remove(minNode.getKey(), comparator, this);
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
        StringBuilder dot = new StringBuilder();
        generateDot(dot); // Call without random to simplify
        return dot.toString();
    }

    private void generateDot(StringBuilder dot) {
        // Start with the current node
        if (left != null) {
            // If left child exists, connect and recurse
            dot.append(value).append(" -> ").append(left.getValue()).append(";\n");
            left.generateDot(dot);
        } else {
            // Create a null left node representation
            String nullLeftNode = "nullL_" + value;
            dot.append(nullLeftNode).append(" [shape=point];\n")
                    .append(value).append(" -> ").append(nullLeftNode).append(";\n");
        }

        if (right != null) {
            // If right child exists, connect and recurse
            dot.append(value).append(" -> ").append(right.getValue()).append(";\n");
            right.generateDot(dot);
        } else {
            // Create a null right node representation
            String nullRightNode = "nullR_" + value;
            dot.append(nullRightNode).append(" [shape=point];\n")
                    .append(value).append(" -> ").append(nullRightNode).append(";\n");
        }
    }
}