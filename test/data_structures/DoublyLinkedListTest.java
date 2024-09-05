package data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.DoublyLinkedList;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
public class DoublyLinkedListTest {
    //given when then
    @Test
    public void GivenANewEmptyList_WhenIsCreated_ThenIsEmpty() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        assertEquals(0,list.size());
        assertTrue(list.isEmpty());
        assertFalse(list.size() != 0);
        assertFalse(list.contains("Zapdos"));
    }

    @Test
    public void GivenANewEmptyList_WhenAddedNewInformation_ThenIsNotEmpty() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("Charmander");
        list.addFirst("Squirtle");
        list.addLast("Bulbasaur");


        assertFalse(list.isEmpty());
        assertEquals(3,list.size());
        assertEquals("[Squirtle, Charmander, Bulbasaur]", list.toString());
    }

    @Test
    public void GivenAnListWithData_WhenSearchForData_ThenDataWillBeGiven (){
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("Charmander");
        list.addFirst("Squirtle");
        list.addLast("Bulbasaur");

        assertTrue(list.contains("Bulbasaur"));
    }

    @Test
    public void GivenAnListWithData_WhenSearchForNoData_ThenDataWillBeGiven (){
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addFirst("Charmander");
        list.addFirst("Squirtle");
        list.addLast("Bulbasaur");

        assertFalse(list.contains("Eevee"));
    }

    @Test
    public void GivenAnEmptyList_WhenSearchingForValue_ThenReturnFalse() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();

        boolean result = list.contains("Pikachu");

        assertFalse(result);  // Covers the empty list case
    }

    @Test
    public void GivenAListWithElements_WhenSearchingForContainedValue_ThenReturnTrue() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Charmander");
        list.addLast("Squirtle");
        list.addLast("Bulbasaur");

        boolean result = list.contains("Squirtle");

        assertTrue(result);  // Covers the case where value is found
    }

    @Test
    public void GivenAListWithElements_WhenSearchingForNonContainedValue_ThenReturnFalse() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Charmander");
        list.addLast("Squirtle");
        list.addLast("Bulbasaur");

        boolean result = list.contains("Pikachu");

        assertFalse(result);  // Covers the case where value is not found
    }

    @Test
    public void GivenAList_WhenGetFirstElement_ThenReturnCorrectElement() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Eevee");
        list.addLast("Charmander");

        assertEquals("Pikachu", list.get(0));
    }

    @Test
    public void GivenAList_WhenGetMiddleElement_ThenReturnCorrectElement() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Snorlax");
        list.addLast("Jigglypuff");
        list.addLast("Bulbasaur");

        assertEquals("Jigglypuff", list.get(1));
    }

    @Test
    public void GivenAList_WhenGetLastElement_ThenReturnCorrectElement() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Meowth");
        list.addLast("Psyduck");
        list.addLast("Gengar");

        assertEquals("Gengar", list.get(2));
    }

    @Test
    public void GivenAList_WhenGetWithInvalidIndex_ThenThrowException() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Magikarp");
        list.addLast("Lapras");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    public void GivenAList_WhenGetWithNegativeIndex_ThenThrowException() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Vaporeon");
        list.addLast("Jolteon");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

}