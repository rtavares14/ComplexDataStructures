package data_structures;

import nl.saxion.cds.data_structures.trees.MyAVLTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyAVLTreeTest {

    MyAVLTree<Integer, String> avlTree = new MyAVLTree<>(Integer::compareTo);

    @Test
    void GivenAVLTree_WhenAddingElements_ThenTreeIsBalanced() {
        avlTree.add(10, "Bulbasaur");
        System.out.println(avlTree.getRoot().getValue());
        avlTree.add(20, "Charmander");
        System.out.println(avlTree.getRoot().getValue());
        avlTree.add(30, "Squirtle"); // This should trigger a left rotation

        System.out.println(avlTree.getRoot().getValue());
        assertEquals("Charmander", avlTree.getRoot().getValue()); // Root should be balanced
        assertEquals("Bulbasaur", avlTree.getRoot().getLeft().getValue());
        assertEquals("Squirtle", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAddingElementsWithLeftRightImbalance_ThenTreeIsBalancedAfterLeftRightRotation() {
        avlTree.add(30, "Pikachu");
        avlTree.add(10, "Jigglypuff");
        avlTree.add(20, "Meowth"); // This should trigger a left-right rotation

        assertEquals("Meowth", avlTree.getRoot().getValue()); // Root should now be 20
        assertEquals("Jigglypuff", avlTree.getRoot().getLeft().getValue());
        assertEquals("Pikachu", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAddingElementsWithRightLeftImbalance_ThenTreeIsBalancedAfterRightLeftRotation() {
        avlTree.add(10, "Eevee");
        avlTree.add(30, "Snorlax");
        avlTree.add(20, "Gengar"); // This should trigger a right-left rotation

        assertEquals("Gengar", avlTree.getRoot().getValue()); // Root should now be 20
        assertEquals("Eevee", avlTree.getRoot().getLeft().getValue());
        assertEquals("Snorlax", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenRemovingElements_ThenTreeRemainsBalanced() {
        avlTree.add(10, "Psyduck");
        avlTree.add(20, "Magikarp");
        avlTree.add(30, "Lapras");
        avlTree.add(40, "Mewtwo"); // Tree should already be balanced

        avlTree.remove(10); // Removing an element and checking balance

        assertEquals("Magikarp", avlTree.getRoot().getValue()); // Root should be balanced
        assertEquals("Lapras", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAccessingRoot_ThenCorrectValueIsReturned() {
        avlTree.add(50, "Charmander");
        avlTree.add(30, "Squirtle");
        avlTree.add(70, "Bulbasaur");

        assertEquals("Charmander", avlTree.getRoot().getValue()); // Check if root is correct
    }

    @Test
    void GivenAVLTree_WhenTreeIsEmpty_ThenRootIsNull() {
        assertNull(avlTree.getRoot());
    }
}
