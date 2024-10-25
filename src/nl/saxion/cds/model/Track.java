package nl.saxion.cds.model;

import nl.saxion.cds.data_structures.list.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Track {
    private String code;
    private String nextCode;
    private int cost;
    private double distanceToNext;

    public Track(String code, String nextCode, int cost, double distanceToNext) {
        this.code = code;
        this.nextCode = nextCode;
        this.cost = cost;
        this.distanceToNext = distanceToNext;
    }


    public static MyArrayList<Track> readFromFile(String filename){
        MyArrayList<Track> tracks = new MyArrayList<>();
        try (Scanner scan = new Scanner(new File(filename))) {
            if (scan.hasNextLine()) {
                scan.nextLine(); // This skips the first line (header)
            }
            while (scan.hasNextLine()) {
                String[] line = scan.nextLine().split(",");

                Track track = new Track(line[0], line[1],
                        Integer.parseInt(line[2]),
                        Double.parseDouble(line[3]));
                tracks.addLast(track);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    public String getCode() {
        return code;
    }

    public String getNextCode() {
        return nextCode;
    }

    public int getCost() {
        return cost;
    }

    public double getDistanceToNext() {
        return distanceToNext;
    }

    @Override
    public String toString() {
        return "Track code = " + code + '\'' +
                ", his nextCode is = " + nextCode + '\'' +
                ", his cost is = " + cost +
                " and the distance to the next track is = " + distanceToNext +
                '}';
    }
}
