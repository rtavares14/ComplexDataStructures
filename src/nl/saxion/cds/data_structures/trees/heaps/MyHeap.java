package nl.saxion.cds.data_structures.trees.heaps;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxHeap;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MyHeap<T extends Comparable<T>> implements SaxHeap {
    private final ArrayList<T> heap;
    private final boolean isMinHeap;

    // Constructor
    public MyHeap(boolean isMinHeap) {
        this.heap = new ArrayList<>();
        this.isMinHeap = isMinHeap;
    }

    /**
     * Determines if the collection has no elements
     *
     * @return if the collection has no elements
     */
    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Determines the number of elements in this collection
     *
     * @return size of this collection
     */
    @Override
    public int size() {
        return heap.size();
    }

    /**
     * Create a String representation of the data in GraphViz format.
     *
     * @param name name of the produced graph
     * @return a GraphViz string representation of this collection
     */
    @Override
    public String graphViz(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph ").append(name).append(" {\n");
        for (int i = 0; i < heap.size(); i++) {
            int leftChildIndex = 2 * i + 1;
            int rightChildIndex = 2 * i + 2;
            if (leftChildIndex < heap.size()) {
                sb.append("    ").append(heap.get(i)).append(" -> ").append(heap.get(leftChildIndex)).append(";\n");
            }
            if (rightChildIndex < heap.size()) {
                sb.append("    ").append(heap.get(i)).append(" -> ").append(heap.get(rightChildIndex)).append(";\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Add the value to the heap.
     *
     * @param value the value to push
     */
    @Override
    public void enqueue(Object value) {
        heap.add((T) value);
        bubbleUp(heap.size() - 1);
    }

    /**
     * Remove the value from the heap.
     *
     * @return the popped value
     */
    public Object dequeue() throws EmptyCollectionException{
        if (heap.isEmpty()) {
            throw new EmptyCollectionException();
        }

        // Store the root value to return
        Object rootValue = heap.get(0);

        // Move the last element to the root
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1); // Remove the last element

        // Restore the heap property by bubbling down
        bubbleDown(0);

        return rootValue; // Return the original root value
    }

    /**
     * Return the value of the heap, without removing it.
     *
     * @return the value or null if the heap is empty
     */
    @Override
    public Object peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException();
        }
        return heap.get(0);
    }

    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;
        if (index > 0 && compare(heap.get(index), heap.get(parentIndex))) {
            swap(index, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    private void bubbleDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int largestIndex = index;

        if (leftChildIndex < heap.size() && compare(heap.get(leftChildIndex), heap.get(largestIndex))) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && compare(heap.get(rightChildIndex), heap.get(largestIndex))) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != index) {
            swap(index, largestIndex);
            bubbleDown(largestIndex);
        }
    }

    private boolean compare(T a, T b) {
        return isMinHeap ? a.compareTo(b) < 0 : a.compareTo(b) > 0;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
