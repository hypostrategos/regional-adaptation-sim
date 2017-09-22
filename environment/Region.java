import java.util.*;

class Adjacency <T, U, V> {
    private final T region;
    private final U direction;
    private final V distance;

    public Adjacency(T region, U direction, V distance) {
        this.region = region;
        this.direction = direction;
        this.distance = distance;
    }

    public T getRegion() { return region; }
    public U getDirection() { return direction; }
    public V getDistance() { return distance; }
}

public class Region extends Map {
    private enum Terrain { 
        FLAT, HILLS, MOUNTAINS 
    }

    private Fauna regionFauna;
    private Flora regionFlora;
    private Weather regionWeather;
    private Water regionWater;

    private int regionId;
    private int regionSize;
    private ArrayList<Adjacency<Integer, Integer, Integer>> regionAdjacency;
    private Terrain regionTerrain;
}
