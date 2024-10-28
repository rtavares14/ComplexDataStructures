package nl.saxion.cds.data_structures.list;

public class MyDLLNode<T>{
    private T value; //should it be final ?
    private MyDLLNode<T> next;
    private MyDLLNode<T> previous;

    public MyDLLNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {  // added setter for value
        this.value = value;
    }

    public MyDLLNode<T> getNext() {
        return next;
    }

    public void setNext(MyDLLNode<T> next) {
        this.next = next;
    }

    public MyDLLNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(MyDLLNode<T> previous) {
        this.previous = previous;
    }
}
