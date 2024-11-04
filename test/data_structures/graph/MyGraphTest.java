package data_structures.graph;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.data_structures.solution.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphTest {

    private MyGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new MyGraph<>();
    }

    @Test
    void GivenEmptyGraph_WhenCheckingIsEmpty_ThenReturnTrue() {
        assertTrue(graph.isEmpty());
    }

    @Test
    void GivenEmptyGraph_WhenAddingEdge_ThenGraphShouldNotBeEmpty() {
        graph.addEdge("Pikachu", "Bulbasaur", 5.0);
        assertFalse(graph.isEmpty());
    }

    @Test
    void GivenGraphWithEdges_WhenCheckingSize_ThenReturnCorrectSize() {
        graph.addEdge("Pikachu", "Bulbasaur", 5.0);
        graph.addEdge("Charmander", "Squirtle", 7.0);
        assertEquals(2, graph.size());
    }

    @Test
    void GivenGraphWithOneEdge_WhenGettingTotalWeight_ThenReturnEdgeWeight() {
        graph.addEdge("Pikachu", "Charmander", 2.5);
        assertEquals(2.5, graph.getTotalWeight(), 0.01);
    }

    @Test
    void GivenGraphWithMultipleEdges_WhenCalculatingTotalWeight_ThenReturnSumOfWeights() {
        graph.addEdge("Pikachu", "Bulbasaur", 4.0);
        graph.addEdge("Charmander", "Squirtle", 6.5);
        graph.addEdge("Bulbasaur", "Charmander", 3.0);
        assertEquals(13.5, graph.getTotalWeight(), 0.01);
    }

    @Test
    void GivenGraph_WhenAddingBidirectionalEdge_ThenBothDirectionsArePresent() {
        graph.addEdgeBidirectional("Pikachu", "Squirtle", 3.0);

        SaxList<MyGraph.DirectedEdge<String>> edgesFromPikachu = graph.getEdges("Pikachu");
        SaxList<MyGraph.DirectedEdge<String>> edgesFromSquirtle = graph.getEdges("Squirtle");

        assertEquals("Squirtle", edgesFromPikachu.get(0).to());
        assertEquals("Pikachu", edgesFromSquirtle.get(0).to());
    }

    @Test
    void GivenGraphWithEdges_WhenRetrievingEdges_ThenReturnCorrectEdges() {
        graph.addEdge("Bulbasaur", "Charmander", 4.0);
        graph.addEdge("Bulbasaur", "Squirtle", 6.0);

        SaxList<MyGraph.DirectedEdge<String>> edgesFromBulbasaur = graph.getEdges("Bulbasaur");

        assertEquals(2, edgesFromBulbasaur.size());
        assertEquals("Charmander", edgesFromBulbasaur.get(0).to());
        assertEquals("Squirtle", edgesFromBulbasaur.get(1).to());
    }


    @Test
    void GivenIteratorExhausted_WhenCallingNext_ThenThrowNoSuchElementException() {
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("A", "C", 1.0);

        Iterator<String> iterator = graph.iterator();

        while (iterator.hasNext()) {
            iterator.next();
        }

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void GivenAStationFormGraph_WhenAskingForCoordinates_ThenReturnCorrectCoordinates() {
        //RAT,Raalte,NL,stoptreinstation,52.391666412354,6.2775001525879

        Coordinate RaalteCords = new Coordinate(52.391666412354, 6.2775001525879);
        String expected = "(52.391666412354:6.2775001525879)";
        assertEquals(expected, RaalteCords.toString(), "The toString method should return the correct string representation.");
    }

    @Test
    void GivenTwoStationsFromGraph_WhenComparing_ThenReturnCorrectComparison() {
        Coordinate RaalteCords = new Coordinate(52.391666412354, 6.2775001525879);
        Coordinate ZwolleCords = new Coordinate(52.512500762939, 6.0949997901917);
        Coordinate coord1 = new Coordinate(52.370216, 4.895168);
        Coordinate coord2 = new Coordinate(52.370216, 3.895168);

        assertEquals(1, coord1.compareTo(coord2));
        assertTrue(RaalteCords.compareTo(ZwolleCords) < 0);
        assertTrue(ZwolleCords.compareTo(RaalteCords) > 0);
        assertEquals(0, RaalteCords.compareTo(RaalteCords));
    }

    @Test
    void testCompareToEqualLatitudeGreaterLongitude() {
        Coordinate coord1 = new Coordinate(52.370216, 4.895168);
        Coordinate coord2 = new Coordinate(52.370216, 3.895168);
        assertEquals(1, coord1.compareTo(coord2), "The compareTo method should return 1 when the latitudes are equal and the first coordinate has a greater longitude.");
    }
}
