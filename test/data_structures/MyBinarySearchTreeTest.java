package data_structures;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyBinarySearchTreeTest {

    private MyBinarySearchTree<String, String> bst;

    @BeforeEach
    public void setUp() {
        bst = new MyBinarySearchTree<>(String::compareTo);  // Using natural order for strings
    }

    @Test
    public void givenTreeWithUniqueKeys_whenAddingKeys_thenSizeIncreases() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");

        assertEquals(3, bst.size());
    }

    @Test
    public void givenTreeWithLeafNode_whenRemovingLeafNode_thenTreeIsEmptyAndSizeReduces() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Jigglypuff", "Normal Pokémon");
        assertEquals(1, bst.size());

        bst.remove("Jigglypuff");
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());
    }

    @Test
    public void givenTreeWithOneChild_whenRemovingNodeWithOneChild_thenCorrectlyRemovedAndSizeReduces() throws DuplicateKeyException, KeyNotFoundException {

        bst.add("Vaporeon", "Water Pokémon");
        bst.add("Eevee", "Normal Pokémon");
        bst.add("Eevee1", "Normal Pokémon");
        bst.add("Eevee2", "Normal Pokémon");
        bst.add("Eevee3", "Normal Pokémon");
        bst.add("Eevee4", "Normal Pokémon");
        bst.add("Eevee5", "Normal Pokémon");
        bst.add("Eevee6", "Normal Pokémon");
        bst.add("Eevee7", "Normal Pokémon");

        bst.inorder(); // Debugging print to see the tree structure

        bst.remove("Eevee7");

        assertFalse(bst.contains("Eevee7"));
        assertTrue(bst.contains("Eevee"));
        assertEquals(8, bst.size());
    }

    @Test
    public void givenTreeWithTwoChildren_whenRemovingNodeWithTwoChildren_thenCorrectNodeIsRemovedAndSizeUpdates() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        // Pikachu has two children: Charmander and Squirtle
        bst.remove("Charmander");

        assertFalse(bst.contains("Charmander"));
        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Squirtle"));
        assertEquals(3, bst.size());
    }

    @Test
    public void givenTree_whenRemovingNonExistentKey_thenThrowsKeyNotFoundException() throws DuplicateKeyException {
        bst.add("Bulbasaur", "Grass Pokémon");

        assertThrows(KeyNotFoundException.class, () -> {
            bst.remove("NonExistentKey");
        });
    }

    @Test
    public void givenTree_whenAddingAndRemovingSequence_thenTreeContainsExpectedElements() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Meowth", "Normal Pokémon");
        bst.add("Psyduck", "Water Pokémon");

        System.out.println("Tree before removal:");
        bst.inorder();  // This will print the current state of the tree

        bst.remove("Meowth");

        System.out.println("Tree after removing Meowth:");
        bst.inorder();  // Tree should no longer contain "Meowth"

        bst.add("Snorlax", "Normal Pokémon");

        System.out.println("Tree after adding Snorlax:");
        bst.inorder();  // Tree should now contain "Snorlax" and "Psyduck"

        assertEquals(2, bst.size());
        assertTrue(bst.contains("Snorlax"));
        assertFalse(bst.contains("Meowth"));
    }

    @Test
    public void givenTree_whenGettingKey_thenReturnsCorrectValue() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");

        assertEquals("Electric Pokémon", bst.get("Pikachu"));
        assertEquals("Fire Pokémon", bst.get("Charmander"));
    }

    @Test
    public void givenTree_whenGettingNonExistentKey_thenReturnsNull() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertNull(bst.get("Charmander"));  // Charmander not added yet
    }

    @Test
    public void givenTree_whenCheckingContains_thenReturnsCorrectResult() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertTrue(bst.contains("Pikachu"));
        assertFalse(bst.contains("Charmander"));
    }

    @Test
    public void givenTree_whenRemovingRootNodeWithTwoChildren_thenCorrectlyRemovesRoot() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");

        // Pikachu is root, and it has two children
        bst.remove("Pikachu");

        assertFalse(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Charmander"));
        assertEquals(2, bst.size());
    }

    @Test
    public void givenTree_whenInOrderTraversal_thenPrintsKeysInOrder() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        System.out.println("In-order traversal:");
        bst.inorder();  // Should print keys in sorted order: Bulbasaur, Charmander, Pikachu, Squirtle
    }

    // New tests for increased coverage

    @Test
    public void givenEmptyTree_whenRemovingFromEmptyTree_thenThrowsKeyNotFoundException() {
        assertThrows(KeyNotFoundException.class, () -> {
            bst.remove("NonExistent");
        });
    }

    @Test
    public void givenTree_whenAddingDuplicateKey_thenThrowsDuplicateKeyException() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertThrows(DuplicateKeyException.class, () -> {
            bst.add("Pikachu", "Electric Pokémon");
        });
    }


    @Test
    public void givenTree_whenAddingMultipleKeys_thenTreeStructureIsValidAndKeysAreInOrder() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");
        bst.add("Jigglypuff", "Normal Pokémon");

        assertEquals(5, bst.size());
        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Charmander"));
        assertTrue(bst.contains("Squirtle"));
        assertTrue(bst.contains("Jigglypuff"));

        bst.inorder(); // Should print keys in sorted order
    }

    @Test
    public void givenTreeWithLeftSubtree_whenRemovingLeftSubtreeNode_thenLeftSubtreeIsUpdatedCorrectly() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Vaporeon", "Water Pokémon");
        bst.add("Eevee", "Normal Pokémon");
        bst.add("Eevee1", "Normal Pokémon");

        bst.remove("Eevee1");

        assertFalse(bst.contains("Eevee1"));
        assertTrue(bst.contains("Vaporeon"));
        assertTrue(bst.contains("Eevee"));
    }

    @Test
    public void givenTree_whenRemovingNonRootNodeWithTwoChildren_thenCorrectlyRemovesAndReplacesWithInOrderSuccessor() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        // Remove Charmander which has two children
        bst.remove("Bulbasaur");

        assertFalse(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Charmander"));
        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Squirtle"));
    }
}
