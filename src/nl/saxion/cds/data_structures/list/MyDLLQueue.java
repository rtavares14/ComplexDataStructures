package nl.saxion.cds.data_structures.list;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxQueue;

public class MyDLLQueue<T> extends DoublyLinkedList<T> implements SaxQueue<T> {

    /**
     * Adds the value to the front of the list.
     *
     * @param value the value to enqueue
     */
    @Override
    public void enqueue(T value) {
        super.addFirst(value);
    }

    /**
     * Removes the value from the back of the list (FIFO).
     *
     * @return the dequeued value
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        return super.removeLast();
    }

    /**
     * Returns the value at the back of the list without removing it.
     *
     * @return the value or null if the list is empty
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return super.peekLast();
    }
}
