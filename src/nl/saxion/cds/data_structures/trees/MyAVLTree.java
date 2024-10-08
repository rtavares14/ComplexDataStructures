package nl.saxion.cds.data_structures.trees;

import java.util.Comparator;

public class MyAVLTree <K extends Comparable<K>, V> extends MyBinarySearchTree {
    /**
     * Constructor that initializes the tree with a comparator.
     *
     * @param comparator
     */
    public MyAVLTree(Comparator comparator) {
        super(comparator);
    }
}
