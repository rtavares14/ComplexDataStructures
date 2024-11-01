package nl.saxion.cds;

import nl.saxion.app.SaxionApp;
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

    private static final Comparator<String> stationCodeComparator = Comparator.naturalOrder();
    private static final Comparator<Track> distanceToNext = Comparator.comparingDouble(Track::getDistanceToNext);

    static Scanner scan = new Scanner(System.in);

    private static boolean isGuiOpen = false;
    public static void main(String[] args) throws InterruptedException {
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
            //System.out.println("7. Close rail network window");
            System.out.println("0. Exit application");
            System.out.println("--------------------------------------------------------------------");
            System.out.print("Enter your choice: ");

            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    showStationInfoByName(stationMap);
                    break;
                case 2:
                    showStationInfoByPartialName(stationList);
                    break;
                case 3:
                    showStationsByType(stationList);
                    break;

                case 4:
                    showShortestRoute(stationMap, trackList);
                    break;

                case 5:
                    break;

                case 6:
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
                //case 7:
                //    if (isGuiOpen) {
                //        RailNetworkVisualization.close();
                //        isGuiOpen = false;
                //        System.out.println("Rail network window closed.");
                //    } else {
                //        System.out.println("Rail network window is not open.");
                //    }
                //    break;
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


    //public static void close() {
    //    SaxionApp.quit();
    //    isGuiOpen = false;  // Mark GUI as closed
    //}

    //option 1
    private static void showStationInfoByName(MyHashMap<String, Station> stationMap) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the station code: ");
        String stationCode = scan.nextLine();

        Station station = stationMap.get(stationCode);

        if (station != null) {
            System.out.println("Station found: " + station);
        } else {
            System.out.println("Station not found.");
        }
    }

    //option 2
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
            System.out.println((i + 1) + ". " + matchingStations.get(i).getName() + " ("+matchingStations.get(i).getCode()+")");
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
    private static void showStationsByType(MyArrayList<Station> stationsList) {
        String[] stationTypes = {
                "Stop Trein Station",
                "Facultatief Station",
                "Mega Station",
                "Intercity Station",
                "Knooppunt Intercity Station",
                "Knooppunt Stop Trein Station",
                "Knooppunt Snel Trein Station",
                "Snel Trein Station"
        };

        System.out.println("Stations type options:");
        for (int i = 0; i < stationTypes.length; i++) {
            System.out.println((i + 1) + ". " + stationTypes[i]);
        }

        System.out.print("Chose option:");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice < 1 || choice > stationTypes.length) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String selectedType = stationTypes[choice - 1];

        MyArrayList<Station> matchingStations = new MyArrayList<>();
        for (Station station : stationsList) {
            if (station.getType().replace(" ", "").equalsIgnoreCase(selectedType.replace(" ", ""))) {
                matchingStations.addLast(station);
            }
        }

        if (matchingStations.size() == 0) {
            System.out.println("No stations found of type: " + selectedType);
            return;
        }

        // Display matching stations
        System.out.println("Stations of type " + selectedType + ":");
        for (int i = 0; i < matchingStations.size(); i++) {
            System.out.println((i + 1) + ". " + matchingStations.get(i).getName() + " ("+matchingStations.get(i).getCode()+")");
        }
    }

    //option 4
    private static void showShortestRoute(MyHashMap<String, Station> stationMap, MyArrayList<Track> trackList) throws InterruptedException {
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

        SaxGraph.Estimator<String> estimator = (current, goal) -> {
            Station currentStation = stationMap.get(current);
            Station goalStation = stationMap.get(goal);

            return Coordinate.haversineDistance(currentStation.getCoordinate(), goalStation.getCoordinate());
        };

        SaxList<SaxGraph.DirectedEdge<String>> path = graph.shortestPathAStar(startStationCode, endStationCode, estimator);
        double totalDistance = 0;
        for (SaxGraph.DirectedEdge<String> edge : path) {
            totalDistance += edge.weight();
        }

        if (path == null || path.isEmpty()) {
            System.out.println("No path found from " + startStationCode + " to " + endStationCode);
            return;
        }

        String pathString = String.join(" --> ", graph.convertEdgesToNodes(path));

        System.out.println("Shortest path from " + startStationCode + " to " + endStationCode + ": " + pathString);
        System.out.println("With a distance of " + String.format("%.2f", totalDistance) + " km.");

    }

    //option 6
    private static void launchGraphicalRepresentation(MyHashMap<String,Station> stationMap, MyArrayList<Track> trackList) throws InterruptedException {
        Thread guiThread = new Thread(() -> RailNetworkVisualization.main(stationMap, trackList));
        guiThread.start();
        guiThread.sleep(1200);
    }
}