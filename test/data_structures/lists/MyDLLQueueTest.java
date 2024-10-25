package data_structures.lists;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.data_structures.list.MyDLLQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDLLQueueTest {

    private MyDLLQueue<String> queue;

    @BeforeEach
    public void setUp() {
        queue = new MyDLLQueue<>();
    }

    @Test
    public void GivenEmptyQueue_WhenEnqueueElement_ThenElementAtFront() {
        assertTrue(queue.isEmpty());
        queue.enqueue("Pikachu");
        assertEquals("Pikachu", queue.peek());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void GivenQueueWithElements_WhenDequeue_ThenFirstElementRemoved() throws EmptyCollectionException {
        queue.enqueue("Pikachu");
        queue.enqueue("Charmander");
        String dequeuedElement = queue.dequeue();
        assertEquals("Pikachu", dequeuedElement);
        assertEquals("Charmander", queue.peek());
    }

    @Test
    public void GivenEmptyQueue_WhenDequeue_ThenThrowEmptyCollectionException() {
        assertTrue(queue.isEmpty());
        assertThrows(EmptyCollectionException.class, () -> {
            queue.dequeue();
        });
    }

    @Test
    public void GivenEmptyQueue_WhenPeek_ThenThrowEmptyCollectionException() {
        assertTrue(queue.isEmpty());
        assertThrows(EmptyCollectionException.class, () -> {
            queue.peek();
        });
    }

    @Test
    public void GivenQueueWithElements_WhenPeek_WhenFirstElementReturnedWithoutRemoval() throws EmptyCollectionException {
        queue.enqueue("Pikachu");
        queue.enqueue("Squirtle");
        String frontElement = queue.peek();
        assertEquals("Pikachu", frontElement);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void GivenEmptyQueue_WhenPeekLast_ThenReturnNull() {
        assertTrue(queue.isEmpty());
        String lastElement = queue.peekLast();
        assertNull(lastElement);
    }
}
