package nl.saxion.cds.data_structures.trees;

public class MyBinaryTreeNode<T> {

    private T value;
    private MyBinaryTreeNode<T> left;
    private MyBinaryTreeNode<T> right;
    private MyBinaryTreeNode<T> parent;

    /**
     * Constructor to initialize a node with a value.
     * Left, right, and parent are initialized to null.
     */
    public MyBinaryTreeNode(T value) {
        this.value = value;
    }

    /**
     * Returns the value of the node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets a new value for the node.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns the left child of the node.
     */
    public MyBinaryTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the left child of the node and updates the child's parent reference.
     */
    public void setLeft(MyBinaryTreeNode<T> left) {
        this.left = left;
        if (left != null) {
            this.left.setParent(this);
        }
    }

    /**
     * Returns the right child of the node.
     */
    public MyBinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets the right child of the node and updates the child's parent reference.
     */
    public void setRight(MyBinaryTreeNode<T> right) {
        this.right = right;
        if (right != null) {
            this.right.setParent(this);
        }
    }

    /**
     * Returns the parent of the node.
     */
    public MyBinaryTreeNode<T> getParent() {
        return parent;
    }

    /**
     * Sets the parent of the node.
     */
    public void setParent(MyBinaryTreeNode<T> parent) {
        this.parent = parent;
    }

    /**
     * Checks if the node is the root of the tree.
     * A node is considered a root if it has no parent.
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * Checks if the node is a leaf.
     * A node is a leaf if it has no children (both left and right are null).
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Calculates and returns the height of the subtree rooted at this node.
     * The height is the number of edges on the longest path from the node to a leaf.
     * If the node is null, it returns -1.
     */
    public int getHeight() {
        if (this == null) return -1;
        return 1 + Math.max(
                (left == null ? -1 : left.getHeight()),
                (right == null ? -1 : right.getHeight())
        );
    }

    /**
     * Calculates and returns the balance factor of this node.
     * The balance factor is defined as the height difference between the right and left subtrees.
     */
    public int getBalance() {
        int leftHeight = left == null ? -1 : left.getHeight();
        int rightHeight = right == null ? -1 : right.getHeight();
        return rightHeight - leftHeight;
    }

    /**
     * Checks whether the subtree rooted at this node is balanced.
     * A node is balanced if the height difference between its left and right subtrees is at most 1.
     */
    public boolean isBalanced() {
        return Math.abs(
                (left == null ? -1 : left.getHeight()) -
                        (right == null ? -1 : right.getHeight())
        ) <= 1;
    }

    /**
     * Calculates and returns the depth of the node in the tree.
     * Depth is the number of edges from the root to this node.
     */
    public int getDepth() {
        int depth = 0;
        MyBinaryTreeNode<T> current = this;
        while (current.parent != null) {
            depth++;
            current = current.parent;
        }
        return depth;
    }

    /**
     * Calculates and returns the size of the subtree rooted at this node.
     * The size is the total number of nodes in the subtree, including the root.
     */
    public int getSize() {
        int result = 1;
        if (getLeft() != null) {
            result += getLeft().getSize();
        }
        if (getRight() != null) {
            result += getRight().getSize();
        }
        return result;
    }
}
