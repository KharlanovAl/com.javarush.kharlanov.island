package model.island;

import config.SimulationConfig;

import java.util.ArrayList;
import java.util.List;

public class Island {

    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[height][width];
        initializeLocations();
    }

    public Island () {
        this(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);
    }

    private void initializeLocations() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                locations[y][x] = new Location(x, y);// в Locations посмотреть
            }
        }
    }

    public void initializeFirstLocation(int x, int y) {
        if (isValidLocation(x, y)) {
            AnimalFabric.initializeLocationWithAnimals(locations[y][x]);
        }
    }

    public Location getLocation(int x, int y) {
        if (isValidLocation(x, y)) {
            return locations[y][x];
        }
        return null;
    }

    public boolean isValidLocation(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public List<Location> getAdjacentLocations(Location location) {
        List<Location> adjacent = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = location.getX() + dir[0];
            int newY = location.getY() + dir[1];

            if (isValidLocation(newX, newY)) {
                adjacent.add(locations[newY][newX]);
            }
        }
        return adjacent;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Location[][] getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "Island[" + width + "x" + height + "]";
    }
}
