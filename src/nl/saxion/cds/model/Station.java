package nl.saxion.cds.model;

import nl.saxion.cds.data_structures.list.MyDoublyLinkedList;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.trees.MyBinarySearchTree;
import nl.saxion.cds.solution.MyArrayList;

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
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be null or blank.");
        }
        this.code = code.trim();

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }
        this.name = name.trim();

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank.");
        }
        this.country = country.trim().toUpperCase();

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank.");
        }
        this.type = type.trim();

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        this.latitude = latitude;

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
        this.longitude = longitude;
    }



    public static void readFromFileToHash(String filename,
                                          MyArrayList<Station> stationList,
                                          MyHashMap<String, Station> stationMap,
                                          MyBinarySearchTree<String, Station> stationTree) {

        try (Scanner scan = new Scanner(new File(filename))) {
            if (scan.hasNextLine()) {
                scan.nextLine();
            }

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split(",");

                if (parts.length < 6) {
                    // Skip bad lines
                    continue;
                }

                String stationCode = parts[0];
                Station station = new Station(parts[0], parts[1], parts[2], parts[3],
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]));

                stationList.addLast(station);

                stationMap.add(stationCode, station);

                stationTree.add(stationCode, station);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Station information:" +
                "\nStation code = " + code +
                "\nName = " + name +
                "\nCountry = " + country +
                "\nType = " + type +
                "\nLatitude = " + latitude +
                "\nLongitude = " + longitude + "\n";
    }

    @Override
    public int compareTo(Station other) {
        return this.type.compareTo(other.type);
    }
}
