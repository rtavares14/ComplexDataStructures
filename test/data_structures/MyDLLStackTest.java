package data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.data_structures.MyDLLStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyDLLStackTest {

    private MyDLLStack<String> stack;

    @BeforeEach
    public void setUp() {
        stack = new MyDLLStack<>();
    }

    @Test
    public void GivenEmptyStack_WhenPushElement_ThenElementOnTop() {
        assertTrue(stack.isEmpty());
        stack.push("Pikachu");
        assertEquals("Pikachu", stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void GivenStackWithElements_WhenPop_ThenTopElementRemoved() throws EmptyCollectionException {
        stack.push("Pikachu");
        stack.push("Charmander");
        String poppedElement = stack.pop();

        assertEquals("Charmander", poppedElement);
        assertEquals("Pikachu", stack.peek());
    }

    @Test
    public void GivenEmptyStack_WhenPop_ThenThrowEmptyCollectionException() {
        assertTrue(stack.isEmpty());
        assertThrows(EmptyCollectionException.class, () -> {
            stack.pop();
        });
    }

    @Test
    public void GivenEmptyStack_WhenPeek_ThenThrowEmptyCollectionException() {
        assertTrue(stack.isEmpty());
        assertThrows(EmptyCollectionException.class, () -> {
            stack.peek();
        });
    }

    @Test
    public void GivenStackWithElements_WhenPeek_ThenTopElementReturnedWithoutRemoval() throws EmptyCollectionException {
        stack.push("Pikachu");
        stack.push("Squirtle");
        String topElement = stack.peek();
        assertEquals("Squirtle", topElement);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void GivenEmptyStack_WhenPeekLast_ThenReturnNull() {
        assertTrue(stack.isEmpty());
        String lastElement = stack.peekLast();
        assertNull(lastElement);
    }
}
