package nl.saxion.cds.data_structures.trees;

import java.util.Comparator;

public class MyBinaryTreeNode<K extends Comparable<K>, V> {

    private V value;
    private K key;
    private MyBinaryTreeNode<K, V> left;
    private MyBinaryTreeNode<K, V> right;
    private int height;

    /**
     * Constructor to initialize a node with a value.
     * Left, right, and parent are initialized to null.
     */
    public MyBinaryTreeNode(V value, K key) {
        this.value = value;
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 0;
    }

    /**
     * Returns the left child of the node.
     */
    public MyBinaryTreeNode<K, V> getLeft() {
        return left;
    }

    /**
     * Sets the left child of the node.
     */
    public void setLeft(MyBinaryTreeNode<K, V> left) {
        this.left = left;
        updateHeight();
    }

    /**
     * Returns the right child of the node.
     */
    public MyBinaryTreeNode<K, V> getRight() {
        return right;
    }

    /**
     * Sets the right child of the node.
     */
    public void setRight(MyBinaryTreeNode<K, V> right) {
        this.right = right;
        updateHeight();
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

    /**
     * Add a node to the tree rooted at this node.
     *
     * @param node       The node to add.
     * @param comparator The comparator to compare keys.
     */
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
        updateHeight();
    }

    /**
     * Remove the node with the given key from the tree rooted at this node.
     *
     * @param key        The key to remove.
     * @param comparator The comparator to compare keys.
     * @param parent     The parent of the current node.
     * @return The value associated with the key, or null if the key is not found.
     */
    public V remove(K key, Comparator<K> comparator, MyBinaryTreeNode<K, V> parent) {
        int cmp = comparator.compare(key, this.getKey());

        if (cmp < 0) {
            return (left != null) ? left.remove(key, comparator, this) : null;
        } else if (cmp > 0) {
            return (right != null) ? right.remove(key, comparator, this) : null;
        } else {
            V removedValue = this.value;

            if (isLeaf()) {
                removeLeafNode(parent);
            } else if (left == null || right == null) {
                removeSingleChildNode(parent);
            } else {
                removeNodeWithTwoChildren(comparator);
            }

            updateHeight();
            return removedValue;
        }
    }

    /**
     * Remove the leaf node.
     * Set the parent's reference to this node to null.
     */
    private void removeLeafNode(MyBinaryTreeNode<K, V> parent) {
        if (parent != null) {
            if (parent.getLeft() == this) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
    }

    /**
     * Remove the node with a single child.
     * Replace the node with its child.
     */
    private void removeSingleChildNode(MyBinaryTreeNode<K, V> parent) {
        MyBinaryTreeNode<K, V> child = (left != null) ? left : right;
        if (parent != null) {
            if (parent.getLeft() == this) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        parent.updateHeight();
    }

    /**
     * Remove the node with two children.
     * Replace the node with the minimum node in the right subtree.
     */
    private void removeNodeWithTwoChildren(Comparator<K> comparator) {
        MyBinaryTreeNode<K, V> minNode = findMin(right);

        this.key = minNode.getKey();
        this.value = minNode.getValue();

        right.remove(minNode.getKey(), comparator, this);
    }

    /**
     * Get the value associated with the given key.
     *
     * @param key The key to search for.
     * @return The value associated with the key, or null if the key is not found.
     */
    public V get(K key, Comparator<K> comparator) {
        int cmp = comparator.compare(key, this.getKey());

        if (cmp < 0) {
            return (left != null) ? left.get(key, comparator) : null;
        } else if (cmp > 0) {
            return (right != null) ? right.get(key, comparator) : null;
        } else {
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

    /**
     * Finds the minimum node in the tree rooted at the given node.
     *
     * @param node The root of the tree to search in.
     * @return The minimum node in the tree.
     */
    public MyBinaryTreeNode<K, V> findMin(MyBinaryTreeNode<K, V> node) {
        if (node == null) return null;
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Returns the dot representation of the tree rooted at this node.
     *
     * @return The dot representation of the tree.
     */
    public String toDot() {
        StringBuilder dot = new StringBuilder();
        generateDot(dot); // Call without random to simplify
        return dot.toString();
    }

    /**
     * Generate the dot representation of the tree rooted at this node.
     *
     * @param dot The StringBuilder to append the dot representation to.
     */
    private void generateDot(StringBuilder dot) {
        if (left != null) {
            dot.append(value).append(" -> ").append(left.getValue()).append(";\n");
            left.generateDot(dot);
        } else {
            String nullLeftNode = "nullL_" + value;
            dot.append(nullLeftNode).append(" [shape=point];\n")
                    .append(value).append(" -> ").append(nullLeftNode).append(";\n");
        }

        if (right != null) {
            dot.append(value).append(" -> ").append(right.getValue()).append(";\n");
            right.generateDot(dot);
        } else {
            String nullRightNode = "nullR_" + value;
            dot.append(nullRightNode).append(" [shape=point];\n")
                    .append(value).append(" -> ").append(nullRightNode).append(";\n");
        }
    }

    /**
     * Get the height of the current node.
     *
     * @return The height of the node.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Update the height of the current node based on its children's heights.
     */
    public void updateHeight() {
        int leftHeight = (left == null) ? -1 : left.getHeight();
        int rightHeight = (right == null) ? -1 : right.getHeight();
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }
}
