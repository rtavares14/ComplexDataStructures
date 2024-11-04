package data_structures.graph;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.model.Track;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyGraphMSTTest {

    @Test
    void givenSingleNodeGraph_whenMinimumCostSpanningTree_thenTreeIsEmpty() {
        MyGraph<String> graph = new MyGraph<>();
        graph.addEdge("A", "A", 0); // A single node

        SaxGraph<String> mst = graph.minimumCostSpanningTree();

        assertTrue(mst.getEdges("A").isEmpty(), "MST of a single node should have no edges.");
    }

    @Test
    void givenTwoConnectedNodes_whenMinimumCostSpanningTree_thenTreeHasOneEdge() {
        MyGraph<String> graph = new MyGraph<>();
        graph.addEdge("A", "B", 10);

        SaxGraph<String> mst = graph.minimumCostSpanningTree();

        SaxList<SaxGraph.DirectedEdge<String>> edges = mst.getEdges("A");
        assertEquals(1, edges.size(), "MST of two connected nodes should have one edge.");
        assertEquals(10, edges.get(0).weight(), "Edge weight in MST should match the graph.");
    }

    @Test
    void graphOfSize9_FindingMinimumCostSpanningTree_ResultsInCorrectTree() {
        MyGraph<String> graph = new MyGraph<>();
        graph.addEdgeBidirectional("a", "b", 4);
        graph.addEdgeBidirectional("a", "h", 8);
        graph.addEdgeBidirectional("b", "c", 8);
        graph.addEdgeBidirectional("b", "h", 11);
        graph.addEdgeBidirectional("c", "d", 7);
        graph.addEdgeBidirectional("c", "f", 4);
        graph.addEdgeBidirectional("c", "i", 2);
        graph.addEdgeBidirectional("d", "e", 9);
        graph.addEdgeBidirectional("d", "f", 14);
        graph.addEdgeBidirectional("e", "f", 10);
        graph.addEdgeBidirectional("f", "g", 2);
        graph.addEdgeBidirectional("g", "h", 1);
        graph.addEdgeBidirectional("g", "i", 6);
        graph.addEdgeBidirectional("h", "i", 7);

        System.out.println(graph.graphViz("Before"));
        System.out.println(graph.minimumCostSpanningTree().graphViz("MCST"));

        assertEquals(37, graph.minimumCostSpanningTree().getTotalWeight());
    }

    @Test
    void aa(){
        MyGraph<String> graph = new MyGraph<>();

        MyArrayList<Track> trackList = Track.readFromFile("resources/tracks.csv");
        for (Track track : trackList) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }

        System.out.println(graph.graphViz("Before"));
        SaxGraph<String> mst = graph.minimumCostSpanningTree();
        System.out.println(mst.graphViz("MCST"));

        System.out.println("Total weight of MST: " + mst.getTotalWeight());
        System.out.println("Number of nodes in MST: " + mst.size());

        assertEquals(2026.1, mst.getTotalWeight(), 0.1);
        assertEquals(396, mst.size());
    }

}
