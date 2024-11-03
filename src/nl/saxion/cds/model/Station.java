package nl.saxion.cds.model;

import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.Coordinate;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.data_structures.list.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Station implements Comparable<Station> {
    private String code;

    private String name;

    private String country;

    private String type;

    private double latitude;

    private double longitude;

    public Station(String code, String name, String country, String type, double latitude, double longitude) {
        this.code = code.trim();
        this.name = name.trim();
        this.country = country.trim().toUpperCase();
        this.type = type.trim();
        this.latitude = latitude;
        this.longitude = longitude;
    }


    /**
     * Reads the stations from a file and stores them in the given data structures.
     *
     * @param filename    The name of the file to read the stations from.
     * @param stationList The list to store the stations in.
     * @param stationMap  The map to store the stations in.
     * @param stationTree The tree to store the stations in.
     */
    public static void readFromFileToDataStructures(String filename, MyArrayList<Station> stationList, MyHashMap<String, Station> stationMap, MyBinarySearchTree<String, Station> stationTree) {

        try (Scanner scan = new Scanner(new File(filename))) {
            if (scan.hasNextLine()) {
                scan.nextLine();
            }

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split(",");

                if (parts.length < 6) {
                    continue;
                }

                String stationCode = parts[0];
                Station station = new Station(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));

                stationList.addLast(station);

                stationMap.add(stationCode, station);

                stationTree.add(stationCode, station);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the code of the station.
     *
     * @return The code of the station.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the name of the station.
     *
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country of the station.
     *
     * @return The country of the station.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the type of the station.
     *
     * @return The type of the station.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the latitude of the station.
     *
     * @return The latitude of the station.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the station.
     *
     * @return The longitude of the station.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return A string representation of the station.
     */
    @Override
    public String toString() {
        return "Station information:" + "\nStation code = " + code + "\nName = " + name + "\nCountry = " + country + "\nType = " + type + "\nLatitude = " + latitude + "\nLongitude = " + longitude + "\n";
    }

    /**
     * Compares this station to another station based on their types.
     *
     * @param other The other station to compare to.
     * @return A negative integer, zero, or a positive integer as this station is less than, equal to, or greater than the other station.
     */
    @Override
    public int compareTo(Station other) {
        return this.type.compareTo(other.type);
    }

    /**
     * Returns the coordinate of the station.
     *
     * @return The coordinate of the station.
     */
    public Coordinate getCoordinate() {
        return new Coordinate(latitude, longitude);
    }
}
