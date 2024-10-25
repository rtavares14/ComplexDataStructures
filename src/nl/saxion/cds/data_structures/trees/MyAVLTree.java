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

        this.root = balanceTree(this.root);
    }

    private MyBinaryTreeNode<K, V> balanceTree(MyBinaryTreeNode<K, V> node) {
        if (node == null) return null;

        node.updateHeight();

        int balanceFactor = height(node.getLeft()) - height(node.getRight());

        if (balanceFactor > 1) {
            if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
                return rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRight(node);
            }
        }

        if (balanceFactor < -1) {
            if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
                return rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                return rotateLeft(node);
            }
        }

        return node;
    }

    private MyBinaryTreeNode<K, V> rotateLeft(MyBinaryTreeNode<K, V> node) {
        MyBinaryTreeNode<K, V> newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    private MyBinaryTreeNode<K, V> rotateRight(MyBinaryTreeNode<K, V> node) {
        MyBinaryTreeNode<K, V> newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        node.updateHeight();
        newRoot.updateHeight();

        return newRoot;
    }

    private int height(MyBinaryTreeNode<K, V> node) {
        return (node == null) ? -1 : node.getHeight();
    }
}