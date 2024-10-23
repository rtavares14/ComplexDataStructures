package nl.saxion.cds;

import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.model.RailNetworkVisualization;
import nl.saxion.cds.model.Station;
import nl.saxion.cds.model.Track;
import nl.saxion.cds.solution.MyArrayList;

import java.util.*;

public class Main {

        private static final Comparator<Station> stationNameComp = Comparator.comparing(Station::getName);
        private static final Comparator<Track> distanceToNext = Comparator.comparingDouble(Track::getDistanceToNext);

        static Scanner scan = new Scanner(System.in);
        //private static boolean isGuiOpen = false;
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
                         break;

                    case 3:
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


    private static void launchGraphicalRepresentation() {
        RailNetworkVisualization.main();
    }
}