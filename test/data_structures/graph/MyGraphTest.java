package data_structures.graph;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.Coordinate;
import nl.saxion.cds.model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphTest {

    private MyGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new MyGraph<>();
    }

    @Test
    void testAStarAlgorithm() {
        // Create a simple graph
        graph.addEdgeBidirectional("A", "B", 1.0);
        graph.addEdgeBidirectional("A", "C", 4.0);
        graph.addEdgeBidirectional("B", "C", 2.0);
        graph.addEdgeBidirectional("B", "D", 5.0);
        graph.addEdgeBidirectional("C", "D", 1.0);

        // Define a simple estimator for the A* algorithm
        SaxGraph.Estimator<String> estimator = new SaxGraph.Estimator<>() {
            @Override
            public double estimate(String from, String to) {
                // Simple heuristic: return 0 for testing purposes
                return 0;
            }
        };

        // Run the A* algorithm to find the shortest path from A to D
        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar("A", "D", estimator);

        // Verify that the path is found
        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");

        // Convert the list of edges to a list of nodes
        SaxList<String> nodes = graph.convertEdgesToNodes(path);

        // Verify the nodes list
        assertNotNull(nodes, "Nodes list should not be null");
        assertFalse(nodes.isEmpty(), "Nodes list should not be empty");

        // Check the nodes list
        assertEquals(4, nodes.size(), "Nodes list should have 4 nodes");
        assertEquals("A", nodes.get(0), "First node should be A");
        assertEquals("B", nodes.get(1), "Second node should be B");
        assertEquals("C", nodes.get(2), "Third node should be C");
        assertEquals("D", nodes.get(3), "Fourth node should be D");

        System.out.println("Shortest path from A to D: " + nodes);
    }

    @Test
    void testAddEdgesFromCSVAndFindShortestPath() {
        String tracksCsvFilePath = "resources/tracks.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(tracksCsvFilePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                String fromStation = values[0].trim();
                String toStation = values[1].trim();
                double weight = Double.parseDouble(values[3].trim());

                graph.addEdgeBidirectional(fromStation, toStation, weight);
            }
        } catch (IOException e) {
            fail("Error reading tracks CSV file: " + e.getMessage());
        }

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            return 0;
        };

        String startStation = "DV";
        String endStation = "ES";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStation, endStation, estimator);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");

        SaxList<String> nodes = graph.convertEdgesToNodes(path);

        assertNotNull(nodes, "Nodes list should not be null");
        assertFalse(nodes.isEmpty(), "Nodes list should not be empty");

        assertEquals(startStation, nodes.get(0), "First node should be the start station");
        assertEquals(endStation, nodes.get(nodes.size() - 1), "Last node should be the end station");

        System.out.println("Shortest path from " + startStation + " to " + endStation + ": " + nodes);
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
    void GivenGraph_WhenGeneratingGraphViz_ThenReturnCorrectFormat() {
        graph.addEdge("Pikachu", "Bulbasaur", 1.5);
        graph.addEdge("Charmander", "Squirtle", 2.0);
        graph.addEdge("Bulbasaur", "Charmander", 3.0);

        String graphViz = graph.graphViz("PokemonGraph");
        String expected = """
                graph PokemonGraph {
                  Pikachu -- Bulbasaur [label=1.5];
                  Charmander -- Squirtle [label=2.0];
                  Bulbasaur -- Charmander [label=3.0];
                }""";

        String[] actualLines = graphViz.trim().split("\n");
        String[] expectedLines = expected.trim().split("\n");

        Arrays.sort(actualLines);
        Arrays.sort(expectedLines);

        assertArrayEquals(expectedLines, actualLines);
    }
}
