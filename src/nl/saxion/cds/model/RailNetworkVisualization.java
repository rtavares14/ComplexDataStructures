package nl.saxion.cds.model;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.data_structures.solution.Coordinate;

import java.awt.*;

public class RailNetworkVisualization implements Runnable {
    private static final int mapWidth = 1620 / 2;
    private static final int mapHeight = (1920 / 2) + 30;
    private static final double MIN_LAT = 50.66;
    private static final double MAX_LAT = 53.56;
    private static final double MIN_LON = 3.31;
    private static final double MAX_LON = 7.25;

    private MyHashMap<String, Station> stations;
    private MyArrayList<Track> tracks;

    public RailNetworkVisualization(MyHashMap<String, Station> stationMap, MyArrayList<Track> tracks) {
        this.stations = stationMap;
        this.tracks = tracks;
    }

    public static int[] geoToPixel(double latitude, double longitude) {
        int pixelX = (int) ((longitude - MIN_LON) / (MAX_LON - MIN_LON) * mapWidth);
        int pixelY = (int) ((MAX_LAT - latitude) / (MAX_LAT - MIN_LAT) * mapHeight);
        return new int[]{pixelX, pixelY};
    }

    public static void main(MyHashMap<String, Station> stationMap, MyArrayList<Track> tracks) {
        SaxionApp.start(new RailNetworkVisualization(stationMap, tracks), mapWidth, mapHeight);
    }

    public static void clearDrawing() {
        SaxionApp.setBackgroundColor(Color.black);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight - 30);
    }

    @Override
    public void run() {
        displayMenu();
    }

    private void displayMenu() {
        while (true) {
            SaxionApp.printLine("----------------- MY TRAIN APLICATION MENU -----------------");
            SaxionApp.printLine("---------------------- BETTER THAN NS ----------------------");
            SaxionApp.printLine("1. See the shortest path between two stations");
            SaxionApp.printLine("2. Determine the minimum number of rail connections (MCST)");
            SaxionApp.print("Enter your choice: ");
            int choice = SaxionApp.readInt();

            switch (choice) {
                case 1:
                    showShortestPath();
                    break;
                case 2:
                    showMCST();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showShortestPath() {
        String startStationCode;
        String endStationCode;

        while (true) {
            SaxionApp.print("Enter the station code of the starting station: ");
            startStationCode = SaxionApp.readString().toUpperCase();
            if (stations.contains(startStationCode)) {
                break;
            } else {
                SaxionApp.printLine("Invalid starting station code. Please try again.");
            }
        }

        while (true) {
            SaxionApp.print("Enter the station code of the destination station: ");
            endStationCode = SaxionApp.readString().toUpperCase();
            if (stations.contains(endStationCode)) {
                break;
            } else {
                SaxionApp.printLine("Invalid destination station code. Please try again.");
            }
        }

        SaxionApp.clear();

        MyGraph<String> graph = new MyGraph<>();
        for (Track track : tracks) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stations.get(current);
            Station goalStation = stations.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStationCode, endStationCode, estimator);
        double totalDistance = 0;
        for (SaxGraph.DirectedEdge<String> edge : path) {
            totalDistance += edge.weight();
        }

        if (path == null || path.isEmpty()) {
            SaxionApp.printLine("No path found from " + startStationCode + " to " + endStationCode);
            return;
        }

        clearDrawing();

        // Draw the graph
        drawGraph(graph);

        // Draw the path
        drawPath(path);

        SaxionApp.setTextDrawingColor(Color.white);
        SaxionApp.drawText("Shortest path from " + startStationCode + " to " + endStationCode+":", 10, 10,18);
        SaxionApp.drawText("With a distance of " + totalDistance + " km.", 10, 30,16);
        SaxionApp.pause();
        SaxionApp.clear();
    }

    private void drawGraph(MyGraph<String> graph) {
        MyHashMap<String, Station> stationMap = new MyHashMap<>();

        // Filter stations in NL
        for (Station station : stations.values()) {
            if (station.getCountry().equals("NL")) {
                stationMap.add(station.getCode(), station);
            }
        }

        // Draw tracks between stations in NL
        for (Track track : tracks) {
            Station fromStation = stationMap.get(track.getCode());
            Station toStation = stationMap.get(track.getNextCode());

            if (fromStation != null && toStation != null) {
                int[] fromCoords = geoToPixel(fromStation.getLatitude(), fromStation.getLongitude());
                int[] toCoords = geoToPixel(toStation.getLatitude(), toStation.getLongitude());

                SaxionApp.setBorderColor(Color.darkGray);
                SaxionApp.drawLine(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);
            }
        }

        // Draw stations in NL
        for (Station station : stationMap.values()) {
            double latitude = station.getLatitude();
            double longitude = station.getLongitude();
            int[] pixelCoords = geoToPixel(latitude, longitude);

            SaxionApp.setBorderColor(Color.red);
            SaxionApp.setFill(Color.red);
            SaxionApp.drawCircle(pixelCoords[0], pixelCoords[1], 4);
        }
    }

    private void drawPath(SaxList<SaxGraph.DirectedEdge<String>> path) {
        SaxionApp.setBorderColor(Color.yellow);
        SaxionApp.setFill(Color.yellow);
        for (SaxGraph.DirectedEdge<String> edge : path) {
            Station fromStation = stations.get(edge.from());
            Station toStation = stations.get(edge.to());

            if (fromStation != null && toStation != null) {
                int[] fromCoords = geoToPixel(fromStation.getLatitude(), fromStation.getLongitude());
                int[] toCoords = geoToPixel(toStation.getLatitude(), toStation.getLongitude());
                SaxionApp.drawLine(fromCoords[0], fromCoords[1], toCoords[0], toCoords[1]);
            }

            // Highlight the starting station
            Station startStation = stations.get(path.get(0).from());
            int[] startCoords = geoToPixel(startStation.getLatitude(), startStation.getLongitude());
            SaxionApp.drawCircle(startCoords[0], startCoords[1], 6);


            // Highlight the ending station
            Station endStation = stations.get(path.get(path.size()-1).to());
            int[] endCoords = geoToPixel(endStation.getLatitude(), endStation.getLongitude());
            SaxionApp.drawCircle(endCoords[0], endCoords[1], 6);

        }
    }

    private void showMCST() {
        // Implement the logic to determine the minimum number of rail connections (MCST)
        // and update the visualization accordingly.
        System.out.println("MCST functionality is not yet implemented.");
    }
}