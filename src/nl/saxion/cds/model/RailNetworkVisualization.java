package nl.saxion.cds.model;

import nl.saxion.app.SaxionApp;

import java.awt.*;

public class RailNetworkVisualization implements Runnable {

    static int mapWidth = 650;
    static int mapHeight = 750;

    public static void main() {
        SaxionApp.start(new RailNetworkVisualization(),mapWidth ,mapHeight );
    }

    public static void close() {
        SaxionApp.quit();
    }

    public void run() {
        SaxionApp.setBackgroundColor(Color.gray);
        // remove the footer from the screen
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, mapWidth, mapHeight);


    }

}
