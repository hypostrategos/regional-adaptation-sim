package environment;
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

public class Region {
    private enum Terrain { 
        FLAT, HILLS, MOUNTAINS 
    }

    private Fauna regionFauna;
    private Flora regionFlora;
    private Weather regionWeather;
    private Water regionWater;

    private int regionId;
    private int regionSize;
    private List<Adjacency<Integer, Integer, Integer>> regionAdjacency = new ArrayList<>();
    private Terrain regionTerrain;

    public Region (int regionId) {
       this.regionId = regionId;
    }
    public void setAdjacency(int region, int direction, int distance) {
        regionAdjacency.add(new Adjacency<>(region, direction, distance)); 
    }
}
