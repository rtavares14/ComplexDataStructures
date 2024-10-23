package nl.saxion.cds;

import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.model.Track;
import nl.saxion.cds.solution.MyArrayList;

import java.util.*;

public class Main {

        private static final Comparator<Station> stationNameComp = Comparator.comparing(Station::getName);
        private static final Comparator<Track> distanceToNext = Comparator.comparingDouble(Track::getDistanceToNext);

        static Scanner scan = new Scanner(System.in);
        public static void main(String[] args) {
            MyHashMap<String, Station> stationMap = Station.readFromFile("resources/stations.csv");

            MyArrayList<Track> tracks = Track.readFromFile("resources/tracks.csv");

            int option;

            boolean menu = true;

            while (menu) {
                System.out.println("\n--------------------- MY TRAIN APLICATION MENU ---------------------");
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

                switch (option){
                    case 1:
                        showStationInfoByName(stationMap);
                        break;
                    case 2:
                        System.out.println("Option 2: Show information of a station based on (the beginning of) its name.");
                        // Call method to show information based on the station name
                        break;

                    case 3:
                        System.out.println("Option 3: Show all stations of a certain type.");
                        // Call method to show stations by type
                        break;

                    case 4:
                        System.out.println("Option 4: Determine the shortest route between two stations.");
                        // Call method to find and display the shortest route
                        break;

                    case 5:
                        System.out.println("Option 5: Determine the minimum number of rail connections (MCST).");
                        // Call method to determine the MCST
                        break;

                    case 6:
                        System.out.println("Option 6: Show rail network, routes, and MCST (Graphical representation).");
                        // Call method to show graphical representation
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

    private static void showStationInfoByName(MyHashMap<String, Station> stationMap) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the station name: ");
        String stationName = scan.nextLine();

      Station station =  stationMap.get(stationName);

        if (station != null) {
            System.out.println("Station found: " + station);
        } else {
            System.out.println("Station not found.");
        }
    }


}