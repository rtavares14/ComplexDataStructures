package nl.saxion.cds;

import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.model.RailNetworkVisualization;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.model.Track;
import nl.saxion.cds.solution.MyArrayList;

import java.util.*;

public class Main {

    private static final Comparator<String> stationCodeComparator = Comparator.naturalOrder();
        private static final Comparator<Track> distanceToNext = Comparator.comparingDouble(Track::getDistanceToNext);

        static Scanner scan = new Scanner(System.in);
        //private static boolean isGuiOpen = false;
    public static void main(String[] args) {
        MyArrayList<Station> stationList = new MyArrayList<>();
        MyHashMap<String, Station> stationMap = new MyHashMap<>();
        MyBinarySearchTree<String, Station> stationTree = new MyBinarySearchTree<>(stationCodeComparator);

        Station.readFromFileToHash("resources/stations.csv", stationList, stationMap, stationTree);


            MyArrayList<Track> tracks = Track.readFromFile("resources/tracks.csv");

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

                switch (option){
                    case 1:
                        showStationInfoByName(stationMap);
                        break;
                    case 2:
                        showStationInfoByPartialName(stationList);
                        break;
                    case 3:
                        //stoptreinstation-373
                        //facultatiefStation-2
                        //megastation-20
                        //intercitystation-53
                        //knooppuntIntercitystation-59
                        //knooppuntStoptreinstation-51
                        //knooppuntSneltreinstation-6
                        //sneltreinstation-14
                        showStationsByType(stationList);
                        break;

                    case 4:
                        break;

                    case 5:
                        break;

                    case 6:
                        launchGraphicalRepresentation();
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

    private static void showStationInfoByName(MyHashMap<String, Station> stationMap) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the station code: ");
        String stationCode = scan.nextLine();

        Station station =  stationMap.get(stationCode);

        if (station != null) {
            System.out.println("Station found: " + station);
        } else {
            System.out.println("Station not found.");
        }
    }

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
            System.out.println((i + 1) + ". " + matchingStations.get(i).getName());
        }

        System.out.print("Enter the number of the station you want to view: ");
        int choice = scan.nextInt();
        scan.nextLine();  // Consume newline

        if (choice < 1 || choice > matchingStations.size()) {
            System.out.println("Invalid choice.");
        } else {
            Station selectedStation = matchingStations.get(choice - 1);
            System.out.println("Station found: " + selectedStation);
        }
    }

    private static void showStationsByType(MyArrayList<Station> stationsList) {
    }

    private static void launchGraphicalRepresentation() {
        RailNetworkVisualization.main();
    }
}