package data_structures.trees;

import nl.saxion.cds.data_structures.trees.MyAVLTree;
import nl.saxion.cds.data_structures.trees.MyBinaryTreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyAVLTreeTest {

    MyAVLTree<Integer, String> avlTree = new MyAVLTree<>(Integer::compareTo);

    @Test
    void GivenAVLTree_WhenAddingElements_ThenTreeIsBalanced() {
        avlTree.add(10, "Bulbasaur");
        avlTree.add(20, "Charmander");
        avlTree.add(30, "Squirtle");

        assertEquals("Charmander", avlTree.getRoot().getValue());
        assertEquals("Bulbasaur", avlTree.getRoot().getLeft().getValue());
        assertEquals("Squirtle", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAddingElementsWithLeftRightImbalance_ThenTreeIsBalancedAfterLeftRightRotation() {
        avlTree.add(30, "Pikachu");
        avlTree.add(10, "Jigglypuff");
        avlTree.add(20, "Meowth");

        assertEquals("Meowth", avlTree.getRoot().getValue());
        assertEquals("Jigglypuff", avlTree.getRoot().getLeft().getValue());
        assertEquals("Pikachu", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAddingElementsWithRightLeftImbalance_ThenTreeIsBalancedAfterRightLeftRotation() {
        avlTree.add(10, "Eevee");
        avlTree.add(30, "Snorlax");
        avlTree.add(20, "Gengar");

        assertEquals("Gengar", avlTree.getRoot().getValue());
        assertEquals("Eevee", avlTree.getRoot().getLeft().getValue());
        assertEquals("Snorlax", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenRemovingElements_ThenTreeRemainsBalanced() {
        avlTree.add(10, "Psyduck");
        avlTree.add(20, "Magikarp");
        avlTree.add(30, "Lapras");
        avlTree.add(40, "Mewtwo");

        avlTree.remove(10);

        assertEquals("Magikarp", avlTree.getRoot().getValue());
        assertEquals("Lapras", avlTree.getRoot().getRight().getValue());
    }

    @Test
    void GivenAVLTree_WhenAccessingRoot_ThenCorrectValueIsReturned() {
        avlTree.add(50, "Charmander");
        avlTree.add(30, "Squirtle");
        avlTree.add(70, "Bulbasaur");

        assertEquals("Charmander", avlTree.getRoot().getValue());
    }

    @Test
    void GivenAVLTree_WhenAddingElementsWithLeftLeftImbalance_ThenTreeIsBalancedAfterRightRotation() {
        avlTree.add(30, "Pikachu");
        avlTree.add(20, "Jigglypuff");
        avlTree.add(10, "Meowth");

        assertEquals("Jigglypuff", avlTree.getRoot().getValue());
        assertEquals("Pikachu", avlTree.getRoot().getRight().getValue());
        assertEquals("Meowth", avlTree.getRoot().getLeft().getValue());
    }

    @Test
    void GivenAVLTree_WhenTreeIsEmpty_ThenRootIsNull() {
        assertNull(avlTree.getRoot());
    }
}
