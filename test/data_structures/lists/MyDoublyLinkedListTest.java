package data_structures.lists;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.list.MyDoublyLinkedList;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
public class MyDoublyLinkedListTest {
    //given when then
    @Test
    public void GivenANewEmptyList_WhenIsCreated_ThenIsEmpty() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertFalse(list.size() != 0);
        assertFalse(list.contains("Lucario"));
    }

    @Test
    public void GivenANewEmptyList_WhenAddedNewInformation_ThenIsNotEmpty() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addFirst("Froakie");
        list.addFirst("Chespin");
        list.addLast("Fennekin");

        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals("[Chespin, Froakie, Fennekin]", list.toString());
    }

    @Test
    public void GivenAnListWithData_WhenSearchForData_ThenDataWillBeGiven() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addFirst("Litten");
        list.addFirst("Rowlet");
        list.addLast("Popplio");

        assertTrue(list.contains("Popplio"));
    }

    @Test
    public void GivenAnListWithData_WhenSearchForNoData_ThenDataWillBeGiven() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addFirst("Litten");
        list.addFirst("Rowlet");
        list.addLast("Popplio");

        assertFalse(list.contains("Sylveon"));
    }

    @Test
    public void GivenAnEmptyList_WhenSearchingForValue_ThenReturnFalse() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        boolean result = list.contains("Greninja");
        assertFalse(result);  // Covers the empty list case
    }

    @Test
    public void GivenAListWithElements_WhenSearchingForContainedValue_ThenReturnTrue() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Charizard");
        list.addLast("Blaziken");
        list.addLast("Incineroar");

        boolean result = list.contains("Blaziken");
        assertTrue(result);  // Covers the case where value is found
    }

    @Test
    public void GivenAListWithElements_WhenSearchingForNonContainedValue_ThenReturnFalse() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Talonflame");
        list.addLast("Greninja");
        list.addLast("Primarina");

        boolean result = list.contains("Xerneas");
        assertFalse(result);  // Covers the case where value is not found
    }

    @Test
    public void GivenAList_WhenGetFirstElement_ThenReturnCorrectElement() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Solgaleo");
        list.addLast("Lunala");
        list.addLast("Necrozma");

        assertEquals("Solgaleo", list.get(0));
    }

    @Test
    public void GivenAList_WhenGetMiddleElement_ThenReturnCorrectElement() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Cinderace");
        list.addLast("Rillaboom");
        list.addLast("Inteleon");

        assertEquals("Rillaboom", list.get(1));
    }

    @Test
    public void GivenAList_WhenGetLastElement_ThenReturnCorrectElement() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Togekiss");
        list.addLast("Garchomp");
        list.addLast("Sylveon");

        assertEquals("Sylveon", list.get(2));
    }

    @Test
    public void GivenAList_WhenGetWithInvalidIndex_ThenThrowException() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Lucario");
        list.addLast("Greninja");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    public void GivenAList_WhenGetWithNegativeIndex_ThenThrowException() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Zygarde");
        list.addLast("Xerneas");

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }

    @Test
    public void GivenAList_WhenAddAtSpecificIndex_ThenValueIsInserted() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Hoopa");
        list.addLast("Volcanion");
        list.addAt(1, "Diancie");

        assertEquals("[Hoopa, Diancie, Volcanion]", list.toString());
    }

    @Test
    public void GivenAList_WhenSetElementAtIndex_ThenElementIsUpdated() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Yveltal");
        list.addLast("Zygarde");
        list.addLast("Xerneas");

        list.set(1, "Genesect");

        assertEquals("[Yveltal, Genesect, Xerneas]", list.toString());
    }

    @Test
    public void GivenAList_WhenRemoveFirstElement_ThenListUpdatesCorrectly() throws EmptyCollectionException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Dedenne");
        list.addLast("Hawlucha");

        assertEquals("Dedenne", list.removeFirst());
        assertEquals("[Hawlucha]", list.toString());
    }

    @Test
    public void GivenAList_WhenRemoveLastElement_ThenListUpdatesCorrectly() throws EmptyCollectionException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Zeraora");
        list.addLast("Marshadow");

        assertEquals("Marshadow", list.removeLast());
        assertEquals("[Zeraora]", list.toString());
    }

    @Test
    public void GivenAList_WhenRemoveElementAtIndex_ThenElementIsRemoved() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Gardevoir");
        list.addLast("Gallade");
        list.addLast("Gothitelle");

        assertEquals("Gallade", list.removeAt(1));
        assertEquals("[Gardevoir, Gothitelle]", list.toString());
    }

    @Test
    public void GivenAList_WhenRemoveElementByValue_ThenElementIsRemoved() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Dragapult");
        list.addLast("Hatterene");
        list.addLast("Grimmsnarl");

        list.remove("Hatterene");
        assertEquals("[Dragapult, Grimmsnarl]", list.toString());
    }

    @Test
    public void GivenAList_WhenRemoveNonExistingElement_ThenThrowValueNotFoundException() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Noivern");
        list.addLast("Florges");

        assertThrows(ValueNotFoundException.class, () -> list.remove("Pikachu"));
    }

    @Test
    public void GivenAList_WhenIteratingThroughList_ThenAllElementsAreVisited() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Zacian");
        list.addLast("Zamazenta");

        Iterator<String> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("Zacian", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("Zamazenta", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void GivenAListWithElements_WhenRemoveFirstElement_ThenRemoveFirstIsCalled() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Togekiss");
        list.addLast("Goodra");
        list.addLast("Salamence");

        list.remove("Togekiss");

        assertEquals("[Goodra, Salamence]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    public void GivenAListWithElements_WhenRemoveLastElement_ThenRemoveLastIsCalled() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Noivern");
        list.addLast("Talonflame");
        list.addLast("Hawlucha");

        list.remove("Hawlucha");

        assertEquals("[Noivern, Talonflame]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    public void GivenAListWithElements_WhenRemoveAtFirstIndex_ThenRemoveFirstIsCalled() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Espeon");
        list.addLast("Umbreon");
        list.addLast("Sylveon");

        assertEquals("Espeon", list.removeAt(0));
        assertEquals("[Umbreon, Sylveon]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    public void GivenAListWithElements_WhenRemoveAtLastIndex_ThenElementIsRemoved() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Eevee");
        list.addLast("Flareon");
        list.addLast("Vaporeon");

        assertEquals("Vaporeon", list.removeAt(2));
        assertEquals("[Eevee, Flareon]", list.toString());
        assertEquals(2, list.size());
    }

    @Test
    public void GivenAListWithElements_WhenRemoveAtInvalidIndex_ThenIndexOutOfBoundsExceptionIsThrown() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Charmander");

        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(3)); // Size is 2, so index 3 is out of bounds
    }

    @Test
    public void GivenAListWithMoreThanOneElement_WhenAddFirst_ThenNewNodeBecomesHeadAndListSizeIncreases() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();

        list.addFirst("Pikachu");
        list.addFirst("Charmander");
        list.addFirst("Bulbasaur"); // This should trigger the else block

        assertEquals("[Bulbasaur, Charmander, Pikachu]", list.toString()); // Bulbasaur should now be the head
        assertEquals(3, list.size());
    }

    @Test
    public void GivenAList_WhenAddAtZeroIndex_ThenElementIsInsertedAtStartAndListSizeIncreases() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Charmander");

        list.addAt(0, "Bulbasaur");

        assertEquals("[Bulbasaur, Pikachu, Charmander]", list.toString());
        assertEquals(3, list.size());
    }

    @Test
    public void GivenAList_WhenAddAtLastIndex_ThenElementIsAppendedAndListSizeIncreases() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Squirtle");
        list.addLast("Jigglypuff");

        list.addAt(2, "Gengar");

        assertEquals("[Squirtle, Jigglypuff, Gengar]", list.toString());
        assertEquals(3, list.size());
    }

    @Test
    public void GivenAList_WhenAddAtMiddleIndex_ThenNewNodeIsInsertedCorrectlyAndListSizeIncreases() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Charmander");
        list.addLast("Bulbasaur");
        list.addLast("Squirtle");

        list.addAt(1, "Pikachu");

        assertEquals("[Charmander, Pikachu, Bulbasaur, Squirtle]", list.toString());
        assertEquals(4, list.size());
    }

    @Test
    public void GivenAList_WhenAddAtInvalidIndex_ThenIndexOutOfBoundsExceptionIsThrown() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Snorlax");
        list.addLast("Meowth");

        assertThrows(IndexOutOfBoundsException.class, () -> list.addAt(3, "Mewtwo"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.addAt(-1, "Eevee"));
    }

    @Test
    public void GivenAListWithMultipleOccurrences_WhenRemoveLastOccurrence_ThenOnlyLastOccurrenceIsRemoved() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Bulbasaur");
        list.addLast("Pikachu");
        list.addLast("Charmander");
        list.addLast("Pikachu");

        list.removeLastOccurrence("Pikachu");

        assertEquals(4, list.size());
        assertEquals("Pikachu", list.get(0)); // First Pikachu remains
        assertEquals("Pikachu", list.get(2)); // Second Pikachu remains
        assertEquals("Charmander", list.get(3)); // Charmander remains
    }

    @Test
    public void GivenAListWithMultipleOccurrences_WhenRemoveAll_ThenAllOccurrencesAreRemoved() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Squirtle");
        list.addLast("Squirtle");
        list.addLast("Charmander");
        list.addLast("Squirtle");

        list.removeAll("Squirtle");

        assertEquals(1, list.size());
        assertEquals("Charmander", list.get(0)); // Only Charmander remains
    }

    @Test
    public void GivenAList_WhenRemoveLastOccurrenceForNonExistingValue_ThenThrowValueNotFoundException() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Bulbasaur");
        list.addLast("Charmander");

        assertThrows(ValueNotFoundException.class, () -> list.removeLastOccurrence("Pikachu"));
    }

    @Test
    public void GivenAList_WhenRemoveAllForNonExistingValue_ThenThrowValueNotFoundException() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Bulbasaur");
        list.addLast("Charmander");

        assertThrows(ValueNotFoundException.class, () -> list.removeAll("Pikachu"));
    }

    @Test
    public void GivenAList_WhenRemoveAllForMiddleValue_ThenCorrectlyRemoveMiddleNode() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Charmander");
        list.addLast("Bulbasaur");
        list.addLast("Squirtle");

        assertEquals(4, list.size());
        assertEquals("Pikachu", list.get(0));
        assertEquals("Charmander", list.get(1));
        assertEquals("Bulbasaur", list.get(2));
        assertEquals("Squirtle", list.get(3));

        list.removeAll("Charmander");
    }

    @Test
    public void GivenAListWithThreeElements_WhenRemoveLastOccurrenceOfMiddleElement_ThenMiddleElementIsRemoved() throws ValueNotFoundException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Bulbasaur"); // This is the middle element we'll remove
        list.addLast("Charmander");

        assertEquals(3, list.size());
        assertEquals("Pikachu", list.get(0));
        assertEquals("Bulbasaur", list.get(1)); // Middle element
        assertEquals("Charmander", list.get(2));

        list.removeLastOccurrence("Bulbasaur");

        assertEquals(2, list.size());
        assertEquals("Pikachu", list.get(0)); // First element remains
        assertEquals("Charmander", list.get(1)); // Last element remains, and it's now the second
    }

    @Test
    public void testGraphVizSinglePokemon() {
        MyDoublyLinkedList<String> pokemonList = new MyDoublyLinkedList<>();
        pokemonList.addLast("Pikachu");

        String expectedOutput =
                "digraph PokemonList {\n" +
                        "    node0 [label=\"Pikachu\"];\n" +
                        "}\n";

        assertEquals(expectedOutput, pokemonList.graphViz("PokemonList"));
    }

    @Test
    public void GivenAListWithThreeElements_WhenGraphVizIsCalled_ThenCorrectNextAndPreviousEdgesAreGenerated() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");
        list.addLast("Bulbasaur");
        list.addLast("Charmander");

        String graphVizOutput = list.graphViz("PokemonList");

        String expectedNextEdge1 = "    node0 -> node1 [label=\"next\"];\n";
        String expectedPreviousEdge1 = "    node1 -> node0 [label=\"previous\"];\n";
        String expectedNextEdge2 = "    node1 -> node2 [label=\"next\"];\n";
        String expectedPreviousEdge2 = "    node2 -> node1 [label=\"previous\"];\n";

        assertTrue(graphVizOutput.contains(expectedNextEdge1));
        assertTrue(graphVizOutput.contains(expectedPreviousEdge1));
        assertTrue(graphVizOutput.contains(expectedNextEdge2));
        assertTrue(graphVizOutput.contains(expectedPreviousEdge2));
    }

    @Test
    public void GivenAList_WhenSetAtInvalidIndex_ThenIndexOutOfBoundsExceptionIsThrown() {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Bulbasaur");
        list.addLast("Charmander");

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "Pikachu"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, "Pikachu"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(10, "Squirtle"));
    }

    @Test
    public void testRemoveLast_OneElementList() throws EmptyCollectionException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Pikachu");

        String removedValue = list.removeLast();

        assertEquals("Pikachu", removedValue);
    }

    @Test
    public void testRemoveFirst_OneElementList() throws EmptyCollectionException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Charmander");

        String removedValue = list.removeFirst();

        assertEquals("Charmander", removedValue);
    }

    @Test
    public void testRemoveLastOccurrence_RemoveFirst() throws ValueNotFoundException, EmptyCollectionException {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<>();
        list.addLast("Charmander");
        list.addLast("Pikachu");
        list.addLast("Bulbasaur");

        list.removeLastOccurrence("Charmander");

        assertEquals(2, list.size());
    }
}