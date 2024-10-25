package nl.saxion.cds.model;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.data_structures.map.MyHashMap;
import nl.saxion.cds.data_structures.solution.MyArrayList;

import java.awt.*;

public class RailNetworkVisualization implements Runnable {
    private static final int mapWidth = 1620 / 2;
    private static final int mapHeight = (1920 / 2) + 30;
    private static final double MIN_LAT = 50.66;
    private static final double MAX_LAT = 53.56;
    private static final double MIN_LON = 3.31;
    private static final double MAX_LON = 7.25;

    private MyArrayList<Station> stations;
    private MyArrayList<Track> tracks;

    public RailNetworkVisualization(MyArrayList<Station> stationList, MyArrayList<Track> tracks) {
        this.stations = stationList;
        this.tracks = tracks;
    }

    public static int[] geoToPixel(double latitude, double longitude) {
        int pixelX = (int) ((longitude - MIN_LON) / (MAX_LON - MIN_LON) * mapWidth);
        int pixelY = (int) ((MAX_LAT - latitude) / (MAX_LAT - MIN_LAT) * mapHeight);
        //System.out.println("Pixel coords: (" + pixelX + ", " + pixelY + ")");

        if (latitude < 51.5 && longitude > 5.5) {
            pixelX += 18;
        }

        return new int[]{pixelX, pixelY};
    }

    public static void main(MyArrayList<Station> stationList, MyArrayList<Track> tracks) {
        SaxionApp.start(new RailNetworkVisualization(stationList, tracks), mapWidth, mapHeight);
    }

    public static void close() {
        SaxionApp.quit();
    }

    @Override
    public void run() {
        SaxionApp.setBackgroundColor(Color.black);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight - 30);

        MyHashMap<String, Station> stationMap = new MyHashMap<>();

        for (Station station : stations) {
            if (station.getCountry().equals("NL")) {
                stationMap.add(station.getCode(), station);
            }
        }

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

        for (Station station : stationMap.values()) {
            double latitude = station.getLatitude();
            double longitude = station.getLongitude();
            int[] pixelCoords = geoToPixel(latitude, longitude);

            if (!station.getName().equals("Amsterdam Centraal") && !station.getName().equals("Rotterdam Centraal")
                    && !station.getName().equals("Den Haag Centraal") && !station.getName().equals("Hurdegaryp")
                    && !station.getName().equals("Deventer")) {

                SaxionApp.setBorderColor(Color.red);
                SaxionApp.setFill(Color.red);
                SaxionApp.drawCircle(pixelCoords[0], pixelCoords[1], 4);
            }
        }

        for (Station station : stationMap.values()) {
            double latitude = station.getLatitude();
            double longitude = station.getLongitude();
            int[] pixelCoords = geoToPixel(latitude, longitude);

            if (station.getName().equals("Amsterdam Centraal") || station.getName().equals("Rotterdam Centraal")
                    || station.getName().equals("Den Haag Centraal") || station.getName().equals("Hurdegaryp")
                    || station.getName().equals("Deventer")) {

                SaxionApp.setBorderColor(Color.ORANGE);
                SaxionApp.setFill(Color.ORANGE);
                SaxionApp.drawCircle(pixelCoords[0], pixelCoords[1], 5);
            }
        }
    }
}
