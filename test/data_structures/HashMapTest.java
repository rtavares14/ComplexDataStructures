package data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.data_structures.MyHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMyHashMap {
    private MyHashMap<Integer, String> hashMap;

    @BeforeEach
    void setUp() {
        hashMap = new MyHashMap<>();
        hashMap.add(101, "Pikachu");
        hashMap.add(202, "Charizard");
        hashMap.add(303, "Bulbasaur");
        hashMap.add(404, "Squirtle");
    }

    @Test
    void GivenEmptyHashMap_WhenCallingIsEmpty_ThenReturnTrue() {
        MyHashMap<Object, Object> emptyMap = new MyHashMap<>();
        assertTrue(emptyMap.isEmpty());
    }

    @Test
    void GivenNonEmptyHashMap_WhenCallingIsEmpty_ThenReturnFalse() {
        assertFalse(hashMap.isEmpty());
    }

    @Test
    void GivenHashMap_WhenAddingNewElements_ThenSizeIncreases() throws DuplicateKeyException {
        assertEquals(4, hashMap.size());
        hashMap.add(505, "Jigglypuff");
        assertEquals(5, hashMap.size());
    }

    @Test
    void GivenHashMap_WhenAddingDuplicateKey_ThenDuplicateKeyExceptionIsThrown() {
        assertThrows(DuplicateKeyException.class, () -> hashMap.add(101, "PikachuDuplicate"));
    }

    @Test
    void GivenHashMap_WhenRemovingExistingElement_ThenSizeDecreases() throws KeyNotFoundException {
        hashMap.remove(303);
        assertEquals(3, hashMap.size());
        assertNull(hashMap.get(303));
    }

    @Test
    void GivenHashMap_WhenRemovingNonExistingElement_ThenKeyNotFoundExceptionIsThrown() {
        assertThrows(KeyNotFoundException.class, () -> hashMap.remove(999));
    }

    @Test
    void GivenHashMap_WhenCheckingContainsKey_ThenReturnCorrectResults() {
        assertTrue(hashMap.contains(202));
        assertFalse(hashMap.contains(999));
    }

    @Test
    void GivenHashMap_WhenFetchingExistingKey_ThenReturnCorrectValue() {
        assertEquals("Pikachu", hashMap.get(101));
        assertEquals("Squirtle", hashMap.get(404));
    }

    @Test
    void GivenHashMap_WhenFetchingNonExistingKey_ThenReturnNull() {
        assertNull(hashMap.get(999));
    }

    @Test
    void GivenHashMap_WhenFetchingAllKeys_ThenReturnCorrectList() {
        var keys = hashMap.getKeys();
        assertEquals(4, keys.size());
        assertTrue(keys.contains(101));
        assertTrue(keys.contains(202));
        assertTrue(keys.contains(303));
        assertTrue(keys.contains(404));
    }

    @Test
    void GivenHashMap_WhenGraphVizIsCalled_ThenReturnFormattedGraph() {
        String graphVizOutput = hashMap.graphViz("testHashMap");
        System.out.println(graphVizOutput);

        assertTrue(graphVizOutput.contains("digraph testHashMap {"));
        assertTrue(graphVizOutput.contains("101 -> Pikachu;"));
        assertTrue(graphVizOutput.contains("202 -> Charizard;"));
        assertTrue(graphVizOutput.contains("303 -> Bulbasaur;"));
        assertTrue(graphVizOutput.contains("404 -> Squirtle;"));
    }

    @Test
    void GivenEmptyHashMap_WhenRemovingFromEmptyMap_ThenKeyNotFoundExceptionIsThrown() {
        MyHashMap<Integer, String> emptyMap = new MyHashMap<>();
        assertThrows(KeyNotFoundException.class, () -> emptyMap.remove(999));
    }

    @Test
    void GivenHashMap_WhenAddingNullKey_ThenHandleCorrectly() throws DuplicateKeyException, KeyNotFoundException {
        MyHashMap<Integer, String> nullKeyMap = new MyHashMap<>();
        nullKeyMap.add(null, "Mewtwo");
        assertEquals("Mewtwo", nullKeyMap.get(null));

        nullKeyMap.remove(null);
        assertFalse(nullKeyMap.contains(null));
    }

    @Test
    void GivenHashMapWithNullKey_WhenRemovingNullKey_ThenReturnValueAndSizeDecreases() throws DuplicateKeyException, KeyNotFoundException {
        MyHashMap<Integer, String> nullKeyMap = new MyHashMap<>();
        nullKeyMap.add(null, "Mew");
        String removedValue = nullKeyMap.remove(null);

        assertEquals("Mew", removedValue);
        assertFalse(nullKeyMap.contains(null));
    }

    @Test
    void GivenHashMap_WhenRemovingNullKey_ThenKeyNotFoundExceptionIsThrown() {
        assertThrows(KeyNotFoundException.class, () -> hashMap.remove(null));
    }

    @Test
    void GivenHashMap_WhenAddingDuplicateNullKey_ThenDuplicateKeyExceptionIsThrown() {
        assertThrows(DuplicateKeyException.class, () -> {
            hashMap.add(null, "MewtwoDuplicate");
            hashMap.add(null, "MewDuplicate");
        });
    }

    @Test
    void GivenHashMap_WhenGraphVizCalledWithNullKey_ThenIncludeNullInGraph() throws DuplicateKeyException {
        hashMap.add(null, "Snorlax"); // Add null key
        String graphVizOutput = hashMap.graphViz("testHashMap");
        assertTrue(graphVizOutput.contains("null -> Snorlax;"), "GraphViz should include the null key entry");
    }

    @Test
    void GivenHashMapWithMultipleEntries_whenResizingOccurs_thenAllEntriesAreRetained() {
        for (int i = 0; i < 10; i++) {
            hashMap.add(505 + i, "Pokemon" + i);
        }
        for (int i = 0; i < 10; i++) {
            assertEquals("Pokemon" + i, hashMap.get(505 + i));
        }
    }

    @Test
    void GivenHashMapWithNullKey_whenResizingOccurs_thenNullKeyEntryIsRetained() throws DuplicateKeyException {
        hashMap.add(null, "Eevee");
        hashMap.add(505, "Gengar");
        for (int i = 0; i < 10; i++) {
            hashMap.add(606 + i, "Charizard" + i);
        }

        assertEquals("Eevee", hashMap.get(null));
        assertEquals("Gengar", hashMap.get(505));
    }

    @Test
    void GivenHashMap_WhenGettingKeysAndNullKeyIsPresent_ThenKeysListContainsNull() throws DuplicateKeyException {
        hashMap.add(null, "Mewtwo"); // Add null key
        var keys = hashMap.getKeys();

        assertEquals(5, keys.size());
        assertTrue(keys.contains(null)); // Check  null is present in keys
    }

    @Test
    void GivenHashMap_WhenAddingValueWithNullKey_ThenNullKeyEntryShouldBePresent() throws DuplicateKeyException {
        assertFalse(hashMap.isEmpty());
        hashMap.add(null, "NullValue");

        assertEquals(6, hashMap.size());
        assertEquals("NullValue", hashMap.get(null));
    }
}
