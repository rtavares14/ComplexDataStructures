package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxStack;

public class MyDLLStack<T> extends DoublyLinkedList<T> implements SaxStack<T> {

    /**
     * Add the value to the stack (on top).
     *
     * @param value the value to push
     */
    @Override
    public void push(T value) {
        super.addLast(value);
    }

    /**
     * Remove the value from the stack (from top).
     *
     * @return the popped value
     * @throws EmptyCollectionException if the stack is empty
     */
    @Override
    public T pop() throws EmptyCollectionException {
        return super.removeLast();
    }

    /**
     * Return the value on top of the stack, without removing it.
     *
     * @return the value or null if the stack is empty
     * @throws EmptyCollectionException if the stack is empty
     */
    @Override
    public T peek() throws EmptyCollectionException {
        return super.peekLast();
    }
}
