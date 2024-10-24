package nl.saxion.cds.model;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.data_structures.solution.MyArrayList;

import java.awt.*;

public class RailNetworkVisualization implements Runnable {

    private static final int mapWidth = 1620 / 2;
    private static final int mapHeight = 1920 / 2;
    private static final double MIN_LAT = 50.75;
    private static final double MAX_LAT = 53.57;
    private static final double MIN_LON = 3.35;
    private static final double MAX_LON = 7.15;

    private MyArrayList<Station> stations;

    public RailNetworkVisualization(MyArrayList<Station> stationList) {
        this.stations = stationList;
    }

    public static int[] geoToPixel(double latitude, double longitude) {
        int pixelX = (int) ((longitude - MIN_LON) / (MAX_LON - MIN_LON) * mapWidth);
        int pixelY = (int) ((MAX_LAT - latitude) / (MAX_LAT - MIN_LAT) * mapHeight);

        System.out.println("Pixel coords: (" + pixelX + ", " + pixelY + ")");

        return new int[]{pixelX, pixelY};
    }

    public static void main(MyArrayList<Station> stationList) {
        SaxionApp.start(new RailNetworkVisualization(stationList), mapWidth, mapHeight);
    }

    public static void close() {
        SaxionApp.quit();
    }

    @Override
    public void run() {
        SaxionApp.setBackgroundColor(Color.black);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight);

        MyArrayList<Station> matchingStations = new MyArrayList<>();


        for (Station station : stations) {
            if (station.getCountry().equals("NL")) {
                matchingStations.addLast(station);
            }
        }

        for (Station station : matchingStations) {
                double latitude = station.getLatitude();
                double longitude = station.getLongitude();
                int[] pixelCoords = geoToPixel(latitude, longitude);


                SaxionApp.setBorderColor(Color.red);
                SaxionApp.setFill(Color.red);
                SaxionApp.drawCircle(pixelCoords[0], pixelCoords[1], (int) 3.5);
                //SaxionApp.pause();
        }
    }
}
