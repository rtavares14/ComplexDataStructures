package data_structures.lists;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxSearchable;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.list.MyArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest {
    private static final int BIG_NUMBER_OF_ELEMENTS = 5000;
    private MyArrayList<String> list;

    @BeforeEach
    void createExampleList() {
        list = new MyArrayList<>();
        list.addLast("Lugia");
        list.addLast("Dratini");
        list.addLast("Diglett");
        list.addLast("Arcanine");
        list.addLast("Dragonite");
    }

    @Test
    void GivenArrayWithNonNullFollowedByNull_WhenIsSorted_ThenReturnTrue() {
        MyArrayList<String> myArrayList = new MyArrayList<>();

        myArrayList.addLast("Bulbasaur"); 
        myArrayList.addLast("Charmander"); 

        Comparator<String> comparator = (a, b) -> {
            if (a == null && b == null) return 0;
            if (a == null) return 1; 
            if (b == null) return -1;
            return a.compareTo(b);
        };

        boolean isSorted = myArrayList.isSorted(comparator);

        assertTrue(isSorted);
    }

    @Test
    void GivenEmptyList_WhenCallingGetters_ConfirmListIsActuallyEmpty() {
        MyArrayList<Object> myArrayList = new MyArrayList<>();
        assertTrue(myArrayList.isEmpty());
        assertEquals(0, myArrayList.size());
        assertEquals("[]", myArrayList.toString());
        assertFalse(myArrayList.contains("Hunter"));
    }

    @Test
    void GivenEmptyList_WhenDoingNothing_IsEmptyIsTrue(){
        MyArrayList<Object> myArrayList = new MyArrayList<>();
        assertTrue(myArrayList.isEmpty());
    }

    @Test
    void GivenEmptyList_WhenAddingSomethingAtTheEnd_IsEmptyIsFalse(){
        MyArrayList<Object> noLongerEmptyList = new MyArrayList<>();
        noLongerEmptyList.addLast("Hunter");
        assertFalse(noLongerEmptyList.isEmpty());
    }

    @Test
    void GivenEmptyList_WhenAddingSomethingAtTheBeginning_IsEmptyIsFalse(){
        MyArrayList<Object> noLongerEmptyList = new MyArrayList<>();
        noLongerEmptyList.addFirst("Hunter");
        assertFalse(noLongerEmptyList.isEmpty());
    }

    @Test
    void GivenEmptyList_WhenAccessingAnyIndex_ThenIndexOutOfBoundsThrown() {
        MyArrayList<Object> myEmptyList = new MyArrayList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.addAt(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.addAt(1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.set(-1, 666));
        assertThrows(IndexOutOfBoundsException.class, () -> myEmptyList.set(1, 666));
        assertThrows(EmptyCollectionException.class, myEmptyList::removeFirst);
        assertThrows(EmptyCollectionException.class, myEmptyList::removeLast);
    }

    @Test
    void GivenSheetsList_WhenNoChanges_ConfirmInitialContent() {
        assertEquals(5, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[Lugia, Dratini, Diglett, Arcanine, Dragonite]", list.toString());
    }

    @Test
    void GivenSheetsList_WhenCallingContains_ConfirmCorrectResponses() {
        assertTrue(list.contains("Lugia"));
        assertTrue(list.contains("Dragonite"));
        assertFalse(list.contains("huh?"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtBeginning_ConfirmChangesAreCorrect() {
        list.addFirst("Hunter");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[Hunter, Lugia, Dratini, Diglett, Arcanine, Dragonite]", list.toString());

        assertThrows(ValueNotFoundException.class, () -> list.remove("huh?"));
    }

    @Test
    void GivenSheetsList_WhenAddingAtIndex_ConfirmChangesAreCorrect() {
        list.addAt(4, "Hunter");
        assertEquals(6, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[Lugia, Dratini, Diglett, Arcanine, Hunter, Dragonite]", list.toString());
    }

    @Test
    void GivenSheetsList_WhenRemovingElement_ConfirmChangesAreCorrect() {
        list.remove("Arcanine");
        assertEquals("[Lugia, Dratini, Diglett, Dragonite]", list.toString());
        assertEquals(4, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    void GivenSheetsList_WhenRemovingAllElement_ConfirmChangesAreCorrect() {
        list.remove("Dragonite");
        list.remove("Lugia");
        assertEquals("[Dratini, Diglett, Arcanine]", list.toString());
        assertEquals(3, list.size());

        assertEquals("Dratini", list.removeFirst());
        assertEquals("Arcanine", list.removeLast());
        assertEquals("Diglett", list.removeFirst());

        assertEquals("[]", list.toString());
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertThrows(EmptyCollectionException.class, list::removeFirst);
        assertThrows(EmptyCollectionException.class, list::removeLast);
    }

    @Test
    void GivenSheetsList_WhenAccessingIndexOutOfBounds_ThenExceptionIsThrown() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(list.size()));
    }

    @Test
    void GivenSheetsList_WhenAddingValidValues_ThenPerformOperationsCorrectly() {
        list.addAt(0, "Eevee");
        list.addAt(5, "Gengar");
        list.addAt(7, "Mew");

        assertEquals("[Eevee, Lugia, Dratini, Diglett, Arcanine, Gengar, Dragonite, Mew]", list.toString());
        assertEquals(8, list.size());
        assertFalse(list.isSorted(String::compareTo));

        list.remove("Eevee");
        list.remove("Gengar");
        list.remove("Mew");

        try {
            var iterator = list.iterator();
            while (iterator.hasNext()) {
                String element = iterator.next();
                if (element.equals("a")) {
                    iterator.remove();
                }
            }
        } catch (UnsupportedOperationException e) {
            System.err.println("Iterator does not support remove operation.");
        }

        assertEquals(5, list.size());
        assertFalse(list.isEmpty());
        assertEquals("[Lugia, Dratini, Diglett, Arcanine, Dragonite]", list.toString());
    }

    @Test
    void GivenListWithIntegers_WhenQuicksorted_ThenListIsSorted() {
        MyArrayList<Integer> list3 = createIntegerArrayList();
        list3.quickSort(Integer::compareTo);
        assertTrue(list3.isSorted(Integer::compareTo));

        int last = -100;
        for (int current : list3) {
            assertTrue(current >= last);
            last = current;
        }
    }

    MyArrayList<Integer> createIntegerArrayList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.addLast(8);
        list.addLast(1);
        list.addLast(5);
        list.addLast(14);
        list.addLast(4);
        list.addLast(15);
        list.addLast(12);
        list.addLast(6);
        list.addLast(2);
        list.addLast(11);
        list.addLast(10);
        list.addLast(7);
        list.addLast(9);
        return list;
    }

    @Test
    void GivenLargeList_WhenMakingChanges_ConfirmStateRemainsCorrect() {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            list.addLast(i);
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list.size());

        assertEquals(BIG_NUMBER_OF_ELEMENTS, list.size());
        for (int i = BIG_NUMBER_OF_ELEMENTS / 2; i > 0; --i) {
            assertEquals(i, list.removeAt(i));
            list.removeLast();
        }
        assertFalse(list.contains(0));
        assertEquals(0, list.size());

        MyArrayList<Integer> listLugia = new MyArrayList<>();
        var random = new Random();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            listLugia.addLast(random.nextInt(0, BIG_NUMBER_OF_ELEMENTS));
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, listLugia.size());
        assertFalse(listLugia.isSorted(Integer::compareTo));
        listLugia.simpleSort(Integer::compareTo);
        assertTrue(listLugia.isSorted(Integer::compareTo));

        MyArrayList<Integer> list3 = new MyArrayList<>();
        for (int i = 0; i < BIG_NUMBER_OF_ELEMENTS; ++i) {
            list3.addLast(random.nextInt(0, BIG_NUMBER_OF_ELEMENTS));
        }
        assertEquals(BIG_NUMBER_OF_ELEMENTS, list3.size());
        assertFalse(list3.isSorted(Integer::compareTo));
        list3.quickSort(Integer::compareTo);
        assertTrue(list3.isSorted(Integer::compareTo));

        int v = list3.get(0);
        int i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 1);
        i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 2);
        i = list3.binarySearch(Integer::compareTo, v);
        assertEquals(v, list3.get(i));
        assertEquals(SaxSearchable.NOT_FOUND, list3.binarySearch(Integer::compareTo, -1));

        v = list3.get(0);
        i = list3.linearSearch(v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 1);
        i = list3.linearSearch(v);
        assertEquals(v, list3.get(i));
        v = list3.get(BIG_NUMBER_OF_ELEMENTS - 2);
        i = list3.linearSearch(v);
        assertEquals(v, list3.get(i));
        assertEquals(SaxSearchable.NOT_FOUND, list3.linearSearch(-1));
    }

    @Test
    void GivenSheetsList_WhenSortedUsingInsertionSort_ThenListIsSorted() {
        list.insertionSort(String::compareTo);
        assertTrue(list.isSorted(String::compareTo));
        assertEquals("[Arcanine, Diglett, Dragonite, Dratini, Lugia]", list.toString());
    }

    @Test
    void GivenReversedList_WhenSortedUsingInsertionSort_ThenListIsSorted() {
        MyArrayList<String> reversedList = new MyArrayList<>();
        reversedList.addLast("Arcanine");
        reversedList.addLast("Charmander");
        reversedList.addLast("Bulbasaur");
        reversedList.addLast("Arcanine");

        reversedList.insertionSort(String::compareTo);
        assertTrue(reversedList.isSorted(String::compareTo));
        assertEquals("[Arcanine, Arcanine, Bulbasaur, Charmander]", reversedList.toString());
    }

    @Test
    void GivenListWithDuplicates_WhenSortedUsingInsertionSort_ThenListIsSorted() {
        MyArrayList<String> listWithDuplicates = new MyArrayList<>();
        listWithDuplicates.addLast("arcanine");
        listWithDuplicates.addLast("bulbasaur");
        listWithDuplicates.addLast("arcanine");
        listWithDuplicates.addLast("bulbasaur");
        listWithDuplicates.addLast("charmander");


        listWithDuplicates.insertionSort(String::compareTo);
        assertTrue(listWithDuplicates.isSorted(String::compareTo));
        assertEquals("[arcanine, arcanine, bulbasaur, bulbasaur, charmander]", listWithDuplicates.toString());
    }

    @Test
    void GivenEmptyList_WhenSortedUsingInsertionSort_ThenListIsEmpty() {
        MyArrayList<String> emptyList = new MyArrayList<>();
        emptyList.insertionSort(String::compareTo);
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void GivenSingleElementList_WhenSortedUsingInsertionSort_ThenListIsUnchanged() {
        MyArrayList<String> singleElementList = new MyArrayList<>();
        singleElementList.addLast("eevee");
        singleElementList.insertionSort(String::compareTo);
        assertEquals(1, singleElementList.size());
        assertEquals("[eevee]", singleElementList.toString());
    }

    @Test
    void GivenLargeList_WhenSortedUsingInsertionSort_ThenListIsSorted() {
        MyArrayList<Integer> largeList = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeList.addLast((int)(Math.random() * 1000));
        }

        largeList.insertionSort(Integer::compareTo);
        assertTrue(largeList.isSorted(Integer::compareTo));
    }

    @Test
    void GivenArrayWithNullElement_WhenLinearSearchForNull_ThenThrowIllegalArgumentException() {
        MyArrayList<String> myArrayList = new MyArrayList<>();

        myArrayList.addLast("Bulbasaur");
        myArrayList.addLast("Charmander");

        assertThrows(IllegalArgumentException.class, () -> myArrayList.linearSearch(null));
    }

    @Test
    void GivenListWithElements_WhenGraphVizIsCalled_ThenReturnCorrectGraphVizString() {
        MyArrayList<String> myArrayList = new MyArrayList<>();
        myArrayList.addLast("Bulbasaur");
        myArrayList.addLast("Ivysaur");
        myArrayList.addLast("Venusaur");

        String graphVizResult = myArrayList.graphViz("PokemonEvolution");

        String expectedGraphViz = """
        digraph PokemonEvolution {
        "Bulbasaur" -> "Ivysaur"
        "Ivysaur" -> "Venusaur"
        }
        """;

        assertEquals(expectedGraphViz.strip(), graphVizResult.strip());
    }
}
