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

    /**
     * Reads the tracks from a file and returns them as a list of tracks.
     * @param filename The name of the file to read the tracks from.
     * @return A list of tracks.
     */
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

    /**
     * Returns the code of the track.
     * @return The code of the track.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the code of the next track.
     * @return The code of the next track.
     */
    public String getNextCode() {
        return nextCode;
    }

    /**
     * Returns the cost of the track.
     * @return The cost of the track.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the distance to the next track.
     * @return The distance to the next track.
     */
    public double getDistanceToNext() {
        return distanceToNext;
    }

    /**
     * @return A string representation of the track.
     */
    @Override
    public String toString() {
        return "Track code = " + code + '\'' +
                ", his nextCode is = " + nextCode + '\'' +
                ", his cost is = " + cost +
                " and the distance to the next track is = " + distanceToNext +
                '}';
    }
}
