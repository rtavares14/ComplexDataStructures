package data_structures.graph;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphDijkstraTest {
    private MyGraph<String> graph;

    @BeforeEach
    void setUp() {
        graph = new MyGraph<>();

        MyArrayList<Track> trackList = Track.readFromFile("resources/tracks.csv");
        for (Track track : trackList) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }
    }

    @Test
    void testShortestPathsDijkstraBasic() {

        graph.shortestPathsDijkstra("ASD");

        SaxGraph<String> resultGraph = graph.shortestPathsDijkstra("ASD");
        System.out.println(resultGraph.graphViz());
    }

    @Test
    void testShortestPathsDijkstraNoPath() {
        // Setup a disconnected graph
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("C", "D", 1.0);

        // Run Dijkstra starting from node "A"
        SaxGraph<String> resultGraph = graph.shortestPathsDijkstra("A");

        // Check that there are no edges leading to "D" in the result graph
        assertTrue(resultGraph.getEdges("D").isEmpty());
    }


}