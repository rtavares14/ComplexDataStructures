package nl.saxion.cds.data_structures.list;

public class MyDLLNode<T>{
    private T value;
    private MyDLLNode<T> next;
    private MyDLLNode<T> previous;

    /**
     * Constructor to initialize a node with a value.
     * Next and previous are initialized to null.
     */
    public MyDLLNode(T value) {
        this.value = value;
    }

    /**
     * Constructor to initialize a node with a value, next and previous node.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the value of the node.
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Sets the value of the node.
     */
    public MyDLLNode<T> getNext() {
        return next;
    }

    /**
     * Returns the next node of the node.
     */
    public void setNext(MyDLLNode<T> next) {
        this.next = next;
    }

    /**
     * Sets the next node of the node.
     */
    public MyDLLNode<T> getPrevious() {
        return previous;
    }

    /**
     * Returns the previous node of the node.
     */
    public void setPrevious(MyDLLNode<T> previous) {
        this.previous = previous;
    }
}
