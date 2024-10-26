package data_structures.trees;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.data_structures.trees.heaps.MyHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyHeapTest {

    private MyHeap<Integer> minHeap;
    private MyHeap<Integer> maxHeap;

    @BeforeEach
    public void setUp() {
        // Initializing both a min-heap and a max-heap
        minHeap = new MyHeap<>(true); // true means it's a min-heap
        maxHeap = new MyHeap<>(false); // false means it's a max-heap
    }

    @Test
    public void givenEmptyHeap_whenEnqueue_thenHeapIsNotEmpty() {
        minHeap.enqueue(10);
        assertFalse(minHeap.isEmpty(), "Heap should not be empty after enqueuing an element");
    }

    @Test
    public void givenMinHeap_whenEnqueue_thenHeapMaintainsMinHeapProperty() {
        minHeap.enqueue(10);
        minHeap.enqueue(5);
        minHeap.enqueue(20);

        assertDoesNotThrow(() -> {
            assertEquals(5, minHeap.peek(), "Min-heap should have the smallest element as root");
        });
    }

    @Test
    public void givenMaxHeap_whenEnqueue_thenHeapMaintainsMaxHeapProperty() {
        maxHeap.enqueue(10);
        maxHeap.enqueue(5);
        maxHeap.enqueue(20);

        assertDoesNotThrow(() -> {
            assertEquals(20, maxHeap.peek(), "Max-heap should have the largest element as root");
        });
    }

    @Test
    public void givenHeap_whenDequeue_thenHeapMaintainsOrder() throws EmptyCollectionException {
        minHeap.enqueue(10);
        minHeap.enqueue(5);
        minHeap.enqueue(20);

        assertEquals(5, minHeap.dequeue(), "Min-heap should remove the smallest element first");
        assertEquals(10, minHeap.dequeue(), "Min-heap should remove the next smallest element");
        assertEquals(20, minHeap.dequeue(), "Min-heap should remove the largest element last");
    }

    @Test
    public void givenMaxHeap_whenDequeue_thenHeapMaintainsMaxOrder() throws EmptyCollectionException {
        maxHeap.enqueue(10);
        maxHeap.enqueue(5);
        maxHeap.enqueue(20);

        assertEquals(20, maxHeap.dequeue(), "Max-heap should remove the largest element first");
        assertEquals(10, maxHeap.dequeue(), "Max-heap should remove the next largest element");
        assertEquals(5, maxHeap.dequeue(), "Max-heap should remove the smallest element last");
    }

    @Test
    public void givenHeap_whenPeek_thenDoesNotRemoveElement() throws EmptyCollectionException {
        minHeap.enqueue(10);
        minHeap.enqueue(5);

        assertEquals(5, minHeap.peek(), "Peek should return the smallest element in a min-heap");
        assertEquals(2, minHeap.size(), "Peek should not remove the element");
    }

    @Test
    public void givenEmptyHeap_whenPeek_thenThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> {
            minHeap.peek();
        }, "Peeking into an empty heap should throw EmptyCollectionException");
    }

    @Test
    public void givenEmptyHeap_whenDequeue_thenThrowsEmptyCollectionException() {
        assertThrows(EmptyCollectionException.class, () -> {
            minHeap.dequeue();
        }, "Dequeuing an empty heap should throw EmptyCollectionException");
    }

    @Test
    public void givenHeap_whenGraphVizCalled_thenProducesGraph() {
        minHeap.enqueue(10);
        minHeap.enqueue(5);
        minHeap.enqueue(20);

        String graphVizOutput = minHeap.graphViz("TestHeap");

        assertTrue(graphVizOutput.contains("digraph TestHeap {"), "GraphViz output should start with the graph name");

        // Check that nodes and edges are present but don't specify exact child-parent relationships
        assertTrue(graphVizOutput.contains("10"), "GraphViz output should contain node 10");
        assertTrue(graphVizOutput.contains("5"), "GraphViz output should contain node 5");
        assertTrue(graphVizOutput.contains("20"), "GraphViz output should contain node 20");
    }

    @Test
    public void givenHeap_whenRightChildIsLarger_thenLargestIndexIsRightChild() {
        MyHeap<Integer> maxHeap = new MyHeap<>(false);
        maxHeap.enqueue(30);
        maxHeap.enqueue(20);
        maxHeap.enqueue(40);
        maxHeap.enqueue(10);
        maxHeap.enqueue(50);

        maxHeap.dequeue(); // Removes 50, root should now be 40
        assertEquals(40, maxHeap.peek(), "Root should be 40 after dequeue.");

        maxHeap.enqueue(25); // Add a new element
        assertEquals(40, maxHeap.dequeue(), "Max-heap should remove the largest element (40) first.");
        assertEquals(30, maxHeap.peek(), "Root should be 30 after removing 40.");
    }
}