package data_structures.graph;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        //System.out.println(resultGraph.graphViz());
    }

    @Test
    void given_startAndEndStationsWithPath_when_getDijkstraPath_then_returnExpectedPath() {
        String startStation = "DV";
        String endStation = "ASD";
        String expectedNodes = "[DV, TWL, APDO, APD, HVL, AMF, BRN, HVS, HVSM, BSMZ, NDB, WP, DMN, ASSP, ASDM, ASD]";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.getDijkstraPath(startStation, endStation);
        SaxList<String> pathNodes = graph.convertEdgesToNodes(path);

        assertEquals(expectedNodes, pathNodes.toString(), "Path nodes do not match the expected sequence.");
    }

    @Test
    void given_startAndEndStationsAnotherPath_when_getDijkstraPath_then_returnExpectedPath() {
        String startStation = "DV";
        String endStation = "ES";
        String expectedNodes = "[DV, DVC, HON, RSN, WDN, AML, AMRI, BN, HGL, ESK, ES]";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.getDijkstraPath(startStation, endStation);
        SaxList<String> pathNodes = graph.convertEdgesToNodes(path);

        assertEquals(expectedNodes, pathNodes.toString(), "Path nodes do not match the expected sequence.");
    }

    @Test
    void given_disconnectedGraph_when_getDijkstraPath_then_returnEmptyPath() {
        // Create a disconnected graph scenario
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("C", "D", 1.0);

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.getDijkstraPath("A", "D");

        assertTrue(path.isEmpty(), "Expected an empty path due to disconnected nodes.");
    }

    @Test
    void testShortestPathsDijkstraNoPath() {
        graph.addEdge("A", "B", 1.0);
        graph.addEdge("C", "D", 1.0);

        SaxGraph<String> resultGraph = graph.shortestPathsDijkstra("A");

        assertTrue(resultGraph.getEdges("D").isEmpty());
    }
}