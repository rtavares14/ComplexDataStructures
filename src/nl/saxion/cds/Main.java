package nl.saxion.cds;

import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.data_structures.graph.MyGraph;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.Coordinate;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.data_structures.list.MyArrayList;
import nl.saxion.cds.model.RailNetworkVisualization;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.model.Track;

import java.util.*;

public class Main {

    // Comparator for sorting station codes
    private static final Comparator<String> stationCodeComparator = Comparator.naturalOrder();

    static Scanner scan = new Scanner(System.in);

    // Variable to keep track of whether the graphical representation is open
    private static boolean isGuiOpen = false;

    public static void main(String[] args) throws InterruptedException {
        //My data structures to store the stations and tracks
        MyArrayList<Station> stationList = new MyArrayList<>();
        MyHashMap<String, Station> stationMap = new MyHashMap<>();
        MyBinarySearchTree<String, Station> stationTree = new MyBinarySearchTree<>(stationCodeComparator);

        Station.readFromFileToDataStructures("resources/stations.csv", stationList, stationMap, stationTree);


        MyArrayList<Track> trackList = Track.readFromFile("resources/tracks.csv");

        int option;

        boolean menu = true;

        while (menu) {
            System.out.println("--------------------- MY TRAIN APLICATION MENU ---------------------");
            System.out.println("-------------------------- BETTER THAN NS --------------------------");
            System.out.println("1. Show information of a station based on its station code");
            System.out.println("2. Show information of a station based on its name");
            System.out.println("3. Show all stations of a certain type");
            System.out.println("4. Determine the shortest route between two stations");
            System.out.println("5. Determine the minimum number of rail connections (MCST)");
            System.out.println("6. Show rail network, routes, and MCST (Graphical representation)");
            System.out.println("0. Exit application");
            System.out.println("--------------------------------------------------------------------");
            System.out.print("Enter your choice: ");

            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    // Show information of a station based on its station code
                    showStationInfoByName(stationMap);
                    break;
                case 2:
                    // Show information of a station based on its name
                    showStationInfoByPartialName(stationList);
                    break;
                case 3:
                    // Show all stations of a certain type
                    showStationsByType(stationList);
                    break;
                case 4:
                    // Determine the shortest route between two stations (A* Algorithm) or (Dijkstra's Algorithm)
                    showShortestRoute(stationMap, trackList);
                    break;
                case 5:
                    // Determine the minimum number of rail connections (MCST)
                    determineMCST(stationMap, trackList);
                    break;
                case 6:
                    // Show rail network, routes, and MCST (Graphical representation)
                    if (!isGuiOpen) {
                        launchGraphicalRepresentation(stationMap, trackList);
                        try {
                            Thread.sleep(1200);
                        } catch (InterruptedException e) {
                            System.err.println("Sleep down: " + e.getMessage());
                        }

                        System.out.println("Returning to the menu...");
                    } else {
                        System.out.println("The Saxion Map is already open.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    menu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scan.close();
    }

    //option 1
    /**
     * Show information of a station based on its station code
     * @param stationMap The map of stations
     */
    private static void showStationInfoByName(MyHashMap<String, Station> stationMap) {
        System.out.print("Enter the station code: ");
        String stationCode = scan.nextLine().toUpperCase();

        Station station = stationMap.get(stationCode);

        if (station != null) {
            System.out.println("Station found: " + station);
        } else {
            System.out.println("Station not found.");
        }
    }

    //option 2
    /**
     * Show information of a station based on its name
     * @param stationsList The list of stations
     */
    private static void showStationInfoByPartialName(MyArrayList<Station> stationsList) {
        System.out.print("Enter the beginning of the station name: ");
        String searchQuery = scan.nextLine().toLowerCase();

        MyArrayList<Station> matchingStations = new MyArrayList<>();

        for (Station station : stationsList) {
            if (station.getName().toLowerCase().startsWith(searchQuery)) {
                matchingStations.addLast(station);
            }
        }

        if (matchingStations.size() == 0) {
            System.out.println("No stations found starting with: " + searchQuery);
            return;
        }

        // Display matching stations
        System.out.println("Matching stations:");
        for (int i = 0; i < matchingStations.size(); i++) {
            System.out.println((i + 1) + ". " + matchingStations.get(i).getName() + " (" + matchingStations.get(i).getCode() + ")");
        }

        System.out.print("Enter the number of the station you want to view: ");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > matchingStations.size()) {
            System.out.println("Invalid choice.");
        } else {
            Station selectedStation = matchingStations.get(choice - 1);
            System.out.println("Station found: " + selectedStation);
        }
    }

    //option 3
    /**
     * Show all stations of a certain type
     * @param stationsList The list of stations
     */
    private static void showStationsByType(MyArrayList<Station> stationsList) {
        MyArrayList<String> stationTypesList = new MyArrayList<>();
        for (Station station : stationsList) {
            if (!stationTypesList.contains(station.getType())) {
                stationTypesList.addLast(station.getType());
            }
        }

        System.out.println("Stations type options:");
        for (int i = 0; i < stationTypesList.size(); i++) {
            System.out.println((i + 1) + ". " + stationTypesList.get(i));
        }

        System.out.print("Choose option:");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > stationTypesList.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String selectedType = stationTypesList.get(choice - 1);

        MyArrayList<Station> matchingStations = new MyArrayList<>();
        for (Station station : stationsList) {
            if (station.getType().replace(" ", "").equalsIgnoreCase(selectedType.replace(" ", ""))) {
                matchingStations.addLast(station);
            }
        }

        matchingStations.quickSort(Comparator.comparing(Station::getName));

        if (matchingStations.size() == 0) {
            System.out.println("No stations found of type: " + selectedType);
            return;
        }

        // Display matching stations
        System.out.println("Stations of type " + selectedType + ":");
        for (int i = 0; i < matchingStations.size(); i++) {
            System.out.println((i + 1) + ". " + matchingStations.get(i).getName() + " (" + matchingStations.get(i).getCode() + ")");
        }
    }

    //option 4
    /**
     * Determine the shortest route between two stations
     * @param stationMap The map of stations
     * @param trackList The list of tracks
     */
    private static void showShortestRoute(MyHashMap<String, Station> stationMap, MyArrayList<Track> trackList) {
        System.out.println("Some of my favorite routes are:");
        System.out.println("1. Deventer to Den Haag Centraal - for a day at the beach (DV - GVC)");
        System.out.println("2. Deventer to Schiphol Airport - for a when the weather is bad (DV - SHL)");
        System.out.println("3. Deventer to Rotterdam Centraal - for a nice day out (DV - RTD)");
        System.out.println("4. Deventer to Hurdegaryp - for visiting relatives (DV - HDG)");

        System.out.print("Enter the station code of the starting station: ");
        String startStationCode = scan.nextLine().toUpperCase();

        System.out.print("Enter the station code of the destination station: ");
        String endStationCode = scan.nextLine().toUpperCase();

        MyGraph<String> graph = new MyGraph<>();
        for (Track track : trackList) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }

        System.out.println("Choose the algorithm to find the shortest path:");
        System.out.println("1. A* Algorithm");
        System.out.println("2. Dijkstra's Algorithm");
        System.out.print("Chose your option: ");
        int algorithmChoice = scan.nextInt();

        double totalDistance = 0;
        SaxList<SaxGraph.DirectedEdge<String>> path = null;
        String algorithm = algorithmChoice == 1 ? "A* Algorithm" : "Dijkstra's Algorithm";

        if (algorithmChoice == 1) {
            SaxGraph.Estimator<String> estimator = (current, goal) -> {
                Station currentStation = stationMap.get(current);
                Station goalStation = stationMap.get(goal);
                return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
            };
            path = graph.shortestPathAStar(startStationCode, endStationCode, estimator);

            for (SaxGraph.DirectedEdge<String> edge : path) {
                totalDistance += edge.weight();
            }
        } else if (algorithmChoice == 2) {
            path = graph.getDijkstraPath(startStationCode, endStationCode);

            if (path != null && !path.isEmpty()) {
                totalDistance = path.get(path.size() - 1).weight();
            }
        } else {
            System.out.println("Invalid choice. Please select either 1 or 2.");
            return;
        }

        if (path == null || path.isEmpty()) {
            System.out.println("No path found from " + startStationCode + " to " + endStationCode);
            return;
        }

        String pathString = String.join(" --> ", graph.convertEdgesToNodes(path));
        System.out.println("Shortest path from " + startStationCode + " to " + endStationCode + " using "+algorithm+": " + pathString);
        System.out.println("With a distance of " + String.format("%.2f", totalDistance) + " km.");
    }

    private static void determineMCST(MyHashMap<String, Station> stationMap, MyArrayList<Track> trackList) {
        MyGraph<String> graph = new MyGraph<>();
        for (Track track : trackList) {
            graph.addEdgeBidirectional(track.getCode(), track.getNextCode(), track.getDistanceToNext());
        }

        SaxGraph<String> mst = graph.minimumCostSpanningTree();

        double totalWeight = 0;
        int edgeCount = 0;
        for (String vertex : stationMap.getKeys()) {
            SaxList<SaxGraph.DirectedEdge<String>> edges = mst.getEdges(vertex);
            for (SaxGraph.DirectedEdge<String> edge : edges) {
                totalWeight += edge.weight();
                edgeCount++;
            }
        }


        System.out.println("Minimum Cost Spanning Tree (MCST) contains " + edgeCount + " edges for " + stationMap.size() + " nodes.");
        System.out.println("Total weight of MCST is " + totalWeight + " km.");
    }

    //option 6
    /**
     * Launch the graphical representation of the rail network
     * @param stationMap The map of stations
     * @param trackList The list of tracks
     * @throws InterruptedException
     */
    private static void launchGraphicalRepresentation(MyHashMap<String, Station> stationMap, MyArrayList<Track> trackList) throws InterruptedException {
        Thread guiThread = new Thread(() -> RailNetworkVisualization.main(stationMap, trackList));
        guiThread.start();
        guiThread.sleep(1200);
    }
}