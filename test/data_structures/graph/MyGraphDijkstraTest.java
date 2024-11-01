package data_structures.graph;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.collection.SaxGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyGraphDijkstraTest {
    private MyGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new MyGraph<>();
    }

    @Test
    void testShortestPathsDijkstra() {
        // Setup the graph with bidirectional edges and weights
        graph.addEdgeBidirectional("a", "b", 1);
        graph.addEdgeBidirectional("a", "c", 4);
        graph.addEdgeBidirectional("b", "c", 2);
        graph.addEdgeBidirectional("b", "d", 3);
        graph.addEdgeBidirectional("b", "e", 10);
        graph.addEdgeBidirectional("c", "d", 6);
        graph.addEdgeBidirectional("c", "g", 3);
        graph.addEdgeBidirectional("e", "d", 5);
        graph.addEdgeBidirectional("e", "g", 2);
        graph.addEdgeBidirectional("e", "f", 7);
        graph.addEdgeBidirectional("g", "d", 1);
        graph.addEdgeBidirectional("g", "f", 5);

        // Execute Dijkstra's algorithm from the start node "a"
        SaxGraph<String> result = (MyGraph<String>) graph.shortestPathsDijkstra("a");

        // Retrieve and verify the shortest path from "f" to "a" as: f -> g -> d -> b -> a
        SaxList<SaxGraph.DirectedEdge<String>> path = ((MyGraph<String>) result).getDijkstraPath("a", "f");

        // Expected path: a -> b -> d -> g -> f, with respective weights
        assertEquals("a", path.get(0).from());
        assertEquals("b", path.get(0).to());
        assertEquals(1, path.get(0).weight(), 0.01);

        assertEquals("b", path.get(1).from());
        assertEquals("d", path.get(1).to());
        assertEquals(3, path.get(1).weight(), 0.01);

        assertEquals("d", path.get(2).from());
        assertEquals("g", path.get(2).to());
        assertEquals(1, path.get(2).weight(), 0.01);

        assertEquals("g", path.get(3).from());
        assertEquals("f", path.get(3).to());
        assertEquals(5, path.get(3).weight(), 0.01);

    }
}