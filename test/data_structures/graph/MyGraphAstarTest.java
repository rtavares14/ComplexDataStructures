package data_structures.graph;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.Coordinate;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyGraphAstarTest {
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
    void GivenGraph_WhenFindingShortestPathFromDVToASD_ThenReturnPath() {
        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        String startStation = "DV";
        String endStation = "ASD";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStation, endStation, estimator);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");

        SaxList<String> nodes = graph.convertEdgesToNodes(path);

        assertNotNull(nodes, "Nodes list should not be null");
        assertFalse(nodes.isEmpty(), "Nodes list should not be empty");

        assertEquals(startStation, nodes.get(0), "First node should be the start station");
        assertEquals(endStation, nodes.get(nodes.size() - 1), "Last node should be the end station");

        String expectedNodes = "[ DV TWL APDO APD HVL AMF BRN HVS HVSM BSMZ NDB WP DMN ASSP ASDM ASD ]";
        assertEquals(expectedNodes, nodes.toString());
    }

    @Test
    void GivenGraph_WhenFindingShortestPathFromDVToES_ThenReturnPath() {
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

        String expectedNodes = "[ DV DVC HON RSN WDN AML AMRI BN HGL ESK ES ]";
        assertEquals(expectedNodes, nodes.toString());
    }

    @Test
    void GivenGraph_WhenFindingShortestPathFromWWToVS_ThenReturnPath() {
        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        String startStation = "WW";
        String endStation = "VS";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStation, endStation, estimator);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");

        SaxList<String> nodes = graph.convertEdgesToNodes(path);

        assertNotNull(nodes, "Nodes list should not be null");
        assertFalse(nodes.isEmpty(), "Nodes list should not be empty");

        assertEquals(startStation, nodes.get(0), "First node should be the start station");
        assertEquals(endStation, nodes.get(nodes.size() - 1), "Last node should be the end station");

        String expectedNodes = "[ WW ATN VSV TBG GDR DTC DTCH WL DID ZV DVN WTV AHP AH AHZ EST NML NM NMGO NMD WC RVS O OW RS HTO HT TB TBU TBR GZ BD ETN RSD BGN RB KBD KRG BZL GS ARN MDB VSS VS ]";
        assertEquals(expectedNodes, nodes.toString());
    }

    @Test
    void GivenGraph_WhenFindingShortestPathFromUnconnectedNodes_ThenReturnEmptyPath() {
        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        String startStation = "DV";
        String endStation = "MINDEN"; // Assuming "XYZ" is not connected to "DV"

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStation, endStation, estimator);

        assertNotNull(path, "Path should not be null");
        assertTrue(path.isEmpty(), "Path should be empty");
    }

    @Test
    void GivenGraph_WhenFindingShortestPathFromDVToDVC_ThenReturnPath() {
        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        String startStation = "DV";
        String endStation = "DVC";

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStation, endStation, estimator);

        assertNotNull(path, "Path should not be null");
        assertFalse(path.isEmpty(), "Path should not be empty");

        SaxList<String> nodes = graph.convertEdgesToNodes(path);

        assertNotNull(nodes, "Nodes list should not be null");
        assertFalse(nodes.isEmpty(), "Nodes list should not be empty");

        assertEquals(startStation, nodes.get(0), "First node should be the start station");
        assertEquals(endStation, nodes.get(nodes.size() - 1), "Last node should be the end station");

        String expectedNodes = "[ DV DVC ]";
        assertEquals(expectedNodes, nodes.toString());
    }
}