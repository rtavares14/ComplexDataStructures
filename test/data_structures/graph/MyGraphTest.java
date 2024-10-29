package data_structures.graph;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.Coordinate;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.model.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.saxion.cds.model.Track;


import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphTest {

    private static final Comparator<String> stationCodeComparator = Comparator.naturalOrder();
    MyArrayList<Station> stationList = new MyArrayList<>();
    MyHashMap<String, Station> stationMap = new MyHashMap<>();
    MyBinarySearchTree<String, Station> stationTree = new MyBinarySearchTree<>(stationCodeComparator);
    private MyGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new MyGraph<>();


        Station.readFromFileToDataStructures("resources/stations.csv", stationList, stationMap, stationTree);

        MyArrayList<Track> trackList = Track.readFromFile("resources/tracks.csv");
        for (Track track : trackList) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }
    }

    @Test
    void GivenAnFullGraph_WhenCheckingTheShortestPath_ThenReturnPath() {
        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
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
