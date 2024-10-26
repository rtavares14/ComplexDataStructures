package data_structures.trees;

import nl.saxion.cds.collection.DuplicateKeyException;
import nl.saxion.cds.collection.KeyNotFoundException;
import nl.saxion.cds.data_structures.list.MyDoublyLinkedList;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.data_structures.trees.MyBinaryTreeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyBinarySearchTreeTest {

    private MyBinarySearchTree<String, String> bst;
    private MyBinarySearchTree<Integer, String> tree;

    @BeforeEach
    public void setUp() {
        bst = new MyBinarySearchTree<>(String::compareTo);
    }

    @Test
    public void GivenTreeWithUniqueKeys_WhenAddingKeys_ThenSizeIncreases() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");

        assertEquals(3, bst.size());
    }

    @Test
    public void GivenTree_WhenAddingMultipleNodes_ThenTreeMaintainsCorrectStructure() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        assertEquals(4, bst.size());
        assertEquals("Electric Pokémon", bst.get("Pikachu"));
        assertEquals("Grass Pokémon", bst.get("Bulbasaur"));
        assertEquals("Fire Pokémon", bst.get("Charmander"));
        assertEquals("Water Pokémon", bst.get("Squirtle"));

        bst.inorder();
    }

    @Test
    public void GivenTree_WhenAddingNullKey_ThenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            bst.add(null, "Null Pokémon");
        });
    }

    @Test
    public void GivenTree_WhenAddingDuplicateKey_ThenThrowsDuplicateKeyException() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertThrows(DuplicateKeyException.class, () -> {
            bst.add("Pikachu", "Electric Pokémon");
        });
    }

    @Test
    public void GivenTree_WhenAddingMultipleKeys_ThenTreeStructureIsValidAndKeysAreInOrder() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");
        bst.add("Jigglypuff22", "Normal Pokémon");

        assertEquals(5, bst.size());
        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Charmander"));
        assertTrue(bst.contains("Squirtle"));
        assertTrue(bst.contains("Jigglypuff22"));

       bst.inorder();
    }

    @Test
    public void GivenTree_WhenRemovingLeafNode_ThenCorrectlyRemovesLeaf() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.remove("Charmander");

        assertEquals(2, bst.size());
        assertFalse(bst.contains("Charmander"));

        bst.inorder();
    }

    @Test
    public void GivenTreeWithOneNode_WhenRemovingRoot_ThenTreeIsEmpty() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.remove("Pikachu");

        assertEquals(0, bst.size());
        assertTrue(bst.isEmpty());
        assertFalse(bst.contains("Pikachu"));
    }

    @Test
    public void GivenTree_WhenRemovingNodeWithOneChild_ThenCorrectlyReplacesNode() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.remove("Pikachu");

        assertEquals(1, bst.size());
        assertTrue(bst.contains("Bulbasaur"));
        assertFalse(bst.contains("Pikachu"));
    }

    @Test
    public void GivenTree_WhenRemovingNodeWithOnlyRightChild_ThenCorrectlyReplacesNode() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.remove("Pikachu");

        assertEquals(1, bst.size());
        assertTrue(bst.contains("Charmander"));
        assertFalse(bst.contains("Pikachu"));
    }

    @Test
    public void GivenTree_WhenRemovingInOrderSuccessor_ThenSuccessorIsCorrectlyReplaced() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");
        bst.remove("Pikachu");

        assertTrue(bst.contains("Squirtle"));
        assertFalse(bst.contains("Pikachu"));
    }

    @Test
    public void GivenTree_WhenRemovingRightmostNode_ThenRightmostNodeIsRemoved() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");
        bst.remove("Squirtle");

        assertFalse(bst.contains("Squirtle"));
        assertEquals(2, bst.size());
    }

    @Test
    public void GivenEmptyTree_WhenRemovingFromEmptyTree_ThenThrowsKeyNotFoundException() {
        assertThrows(KeyNotFoundException.class, () -> {
            bst.remove("NonExistent");
        });
    }

    @Test
    public void GivenTree_WhenRemovingNonExistentKey_ThenThrowsKeyNotFoundException() throws DuplicateKeyException {
        bst.add("Bulbasaur", "Grass Pokémon");

        assertThrows(KeyNotFoundException.class, () -> {
            bst.remove("NonExistentKey");
        });
    }

    @Test
    public void GivenTree_WhenRemovingRootNodeWithTwoChildren_ThenCorrectlyRemovesRoot() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.remove("Pikachu");

        assertFalse(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Charmander"));
        assertEquals(2, bst.size());
    }

    @Test
    public void GivenTreeWithTwoChildren_WhenRemovingNodeWithTwoChildren_ThenCorrectNodeIsRemovedAndSizeUpdates() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Squirtle", "Water Pokémon");
        bst.remove("Charmander");

        assertFalse(bst.contains("Charmander"));
        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Bulbasaur"));
        assertTrue(bst.contains("Squirtle"));
        assertEquals(3, bst.size());
    }


    @Test
    public void GivenTree_WhenGettingKey_ThenReturnsCorrectValue() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");

        assertEquals("Electric Pokémon", bst.get("Pikachu"));
        assertEquals("Fire Pokémon", bst.get("Charmander"));
    }

    @Test
    public void GivenTree_WhenGettingNonExistentKey_ThenReturnsNull() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertNull(bst.get("Charmander"));
    }


    @Test
    public void GivenTree_WhenCheckingContains_ThenReturnsTrueForExistingKeys() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");

        assertTrue(bst.contains("Pikachu"));
        assertTrue(bst.contains("Charmander"));
    }

    @Test
    public void GivenTree_WhenCheckingContains_ThenReturnsFalseForNonExistentKeys() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertFalse(bst.contains("Charmander"));
    }

    @Test
    public void GivenEmptyTree_WhenCheckingContains_ThenReturnsFalse() {
        assertFalse(bst.contains("NonExistent"));
    }


    @Test
    public void GivenEmptyTree_WhenCheckingSize_ThenReturnsZero() {
        assertEquals(0, bst.size());
    }

    @Test
    public void GivenTreeWithOneNode_WhenCheckingSize_ThenReturnsOne() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertEquals(1, bst.size());
    }

    @Test
    public void GivenTreeWithMultipleNodes_WhenCheckingSize_ThenReturnsCorrectSize() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");

        assertEquals(3, bst.size());
    }

    @Test
    public void GivenEmptyTree_WhenCheckingIfEmpty_ThenReturnsTrue() {
        assertTrue(bst.isEmpty());
    }

    @Test
    public void GivenTreeWithNodes_WhenCheckingIfEmpty_ThenReturnsFalse() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");

        assertFalse(bst.isEmpty());
    }

    @Test
    public void GivenTree_WhenGettingKeys_ThenReturnsKeysInSortedOrder() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        MyDoublyLinkedList<String> keys = bst.getKeys();

        assertEquals(4, keys.size());
        assertEquals("Bulbasaur", keys.get(0));
        assertEquals("Charmander", keys.get(1));
        assertEquals("Pikachu", keys.get(2));
        assertEquals("Squirtle", keys.get(3));
    }

    @Test
    public void GivenEmptyTree_WhenGettingKeys_ThenReturnsEmptyList() {
        MyDoublyLinkedList<String> keys = bst.getKeys();
        assertTrue(keys.isEmpty());
    }

    @Test
    public void GivenTree_WhenGeneratingGraphViz_ThenProducesCorrectString() throws DuplicateKeyException {
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Squirtle", "Water Pokémon");

        String expectedGraphViz = "digraph MyBinarySearchTree {\n" +
                "\"Bulbasaur\";\n" +
                "\"Bulbasaur\" -> \"Charmander\";\n" +
                "\"Charmander\";\n" +
                "\"Charmander\" -> \"Pikachu\";\n" +
                "\"Pikachu\";\n" +
                "\"Pikachu\" -> \"Squirtle\";\n" +
                "\"Squirtle\";\n" +
                "}";

        String graphVizOutput = bst.graphViz();
        assertEquals(expectedGraphViz, graphVizOutput);
    }

    @Test
    public void GivenTreeWithOnlyRightChild_WhenRemovingRoot_ThenRootIsUpdatedToRightChild() throws DuplicateKeyException, KeyNotFoundException {
        bst.add("Bulbasaur", "Grass Pokémon");
        bst.add("Charmander", "Fire Pokémon");
        String removedValue = bst.remove("Bulbasaur");
        assertEquals("Grass Pokémon", removedValue);

        MyBinaryTreeNode<String, String> newRoot = bst.getRoot();
        assertEquals("Charmander", newRoot.getKey());
        assertNull(newRoot.getLeft());
        assertNull(newRoot.getRight());
    }

    @Test
    public void GivenTreeWithLeftChild_WhenGeneratingGraphViz_ThenContainsLeftChildEdge() throws DuplicateKeyException {
        bst.add("Pikachu", "Electric Pokémon");
        bst.add("Bulbasaur", "Grass Pokémon");
        String graphVizOutput = bst.graphViz();
        String expectedEdge = "\"Pikachu\" -> \"Bulbasaur\";\n";
        assertTrue(graphVizOutput.contains(expectedEdge), "GraphViz output should contain the edge for left child.");
    }

    @Test
    public void GivenEmptyTree_WhenGeneratingGraphViz_ThenProducesCorrectString() {
        String expectedGraphViz = "digraph MyBinarySearchTree {\n" +
                "}";

        String graphVizOutput = bst.graphViz();
        assertEquals(expectedGraphViz, graphVizOutput);
    }

    @Test
    public void givenComplexTree_whenToDot_thenCorrectDotFormat() {
        MyBinaryTreeNode<String, String> root = new MyBinaryTreeNode<>("Root", "Root Value");
        MyBinaryTreeNode<String, String> leftChild = new MyBinaryTreeNode<>("LeftChild", "Left Child Value");
        MyBinaryTreeNode<String, String> rightChild = new MyBinaryTreeNode<>("RightChild", "Right Child Value");
        MyBinaryTreeNode<String, String> leftLeftChild = new MyBinaryTreeNode<>("LeftLeftChild", "Left Left Child Value");

        root.setLeft(leftChild);
        root.setRight(rightChild);
        leftChild.setLeft(leftLeftChild);

        String dotOutput = root.toDot();

        assertTrue(dotOutput.contains("Root -> LeftChild;"));
        assertTrue(dotOutput.contains("Root -> RightChild;"));
        assertTrue(dotOutput.contains("LeftChild -> LeftLeftChild;"));
        assertTrue(dotOutput.contains("LeftChild -> nullR_LeftChild;"));
        assertTrue(dotOutput.contains("RightChild -> nullL_RightChild;"));
    }

    @Test
    void GivenTreeWithTwoChildNode_WhenDeletingNonRootNodeWithTwoChildren_ThenSuccessorReplacesNode() throws KeyNotFoundException {
        tree = new MyBinarySearchTree<>(Integer::compareTo);

            tree.add(10, "Pikachu");
            tree.add(5, "Charmander");
            tree.add(15, "Squirtle");
            tree.add(3, "Bulbasaur");
            tree.add(7, "Jigglypuff");
            tree.add(12, "Meowth");
            tree.add(17, "Psyduck");


        tree.remove(5);

        assertFalse(tree.contains(5), "Node with key 5 should be removed.");
        assertEquals("Jigglypuff", tree.get(7), "In-order successor (7) should replace the deleted node.");
        assertEquals("Bulbasaur", tree.get(3), "Left subtree should remain consistent.");
    }

    @Test
    void GivenNodeWithOnlyRightChild_WhenRemovingNode_ThenParentRightChildIsUpdated() throws KeyNotFoundException {
        tree = new MyBinarySearchTree<>(Integer::compareTo);

        tree.add(50, "Pikachu");
        tree.add(30, "Bulbasaur");
        tree.add(70, "Charmander");
        tree.add(80, "Squirtle");


        tree.remove(70);

        // Assert: Check that 50's right child is now 80 (replacing the removed node 70)
        assertEquals("Squirtle", tree.getRoot().getRight().getValue());
        assertNull(tree.getRoot().getRight().getRight());
    }

    @Test
    void GivenTreeWithLeftSubtree_WhenFindingMin_ThenLeftmostNodeIsReturned() {
        tree = new MyBinarySearchTree<>(Integer::compareTo);
        tree.add(50, "Pikachu");
        tree.add(30, "Bulbasaur");
        tree.add(20, "Charmander");
        tree.add(10, "Squirtle");

        MyBinaryTreeNode<Integer, String> minNode = tree.getRoot().findMin(tree.getRoot());

        assertNotNull(minNode);
        assertEquals(10, minNode.getKey());
        assertEquals("Squirtle", minNode.getValue());
    }
}