package nl.saxion.cds.data_structures.trees;

import java.util.Comparator;

public class MyAVLTree<K extends Comparable<K>, V> extends MyBinarySearchTree<K, V> {

    /**
     * Constructor that initializes the tree with a comparator.
     *
     * @param comparator
     */
    public MyAVLTree(Comparator<K> comparator) {
        super(comparator);
    }

    /**
     * Adds a key-value pair to the tree using the base class method, and balances the tree afterward.
     *
     * @param key   The key to add.
     * @param value The value associated with the key.
     */
    @Override
    public void add(K key, V value) {
        super.add(key, value);

        balanceTree(getRoot());
    }

    /**
     * Balances the tree from the current node.
     *
     * @param node The current root of the subtree.
     */
    private void balanceTree(MyBinaryTreeNode<K, V> node) {
        if (node == null) return;

        balanceSubtree(node.getLeft());
        balanceSubtree(node.getRight());

        node.updateHeight();

        int balanceFactor = height(node.getLeft()) - height(node.getRight());

        if (balanceFactor > 1) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                rotateLeft(node);
            }
        }
    }

    /**
     * Rotate the subtree rooted at node to the left.
     *
     * @param node The root of the subtree.
     * @return The new root after rotation.
     */
    private MyBinaryTreeNode<K, V> rotateLeft(MyBinaryTreeNode<K, V> node) {
        MyBinaryTreeNode<K, V> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    /**
     * Rotate the subtree rooted at node to the right.
     *
     * @param node The root of the subtree.
     * @return The new root after rotation.
     */
    private MyBinaryTreeNode<K, V> rotateRight(MyBinaryTreeNode<K, V> node) {
        MyBinaryTreeNode<K, V> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    /**
     * Calculates the height of a node in the tree.
     *
     * @param node The node whose height is being calculated.
     * @return The height of the node.
     */
    private int height(MyBinaryTreeNode<K, V> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    /**
     * Balances a subtree rooted at the given node.
     *
     * @param node The root of the subtree to balance.
     * @return The new root after balancing.
     */
    private MyBinaryTreeNode<K, V> balanceSubtree(MyBinaryTreeNode<K, V> node) {
        if (node == null) return null;

        int balanceFactor = height(node.getLeft()) - height(node.getRight());

        if (balanceFactor > 1) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                node = rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                node = rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }

        return node;
    }
}
