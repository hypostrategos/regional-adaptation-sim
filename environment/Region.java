package environment;
import java.util.*;

public class Region {
    private enum Terrain { 
        FLAT, HILLS, MOUNTAINS 
    }
    public static final int maxRegionSize = 10;
    public static final int minRegionSize = 1;
    public static final int maxRegionDistance = 10;
    public static final int minRegionDistance = 1;
    public static final int numRegionsMod = 1;

    private Fauna regionFauna;
    private Flora regionFlora;
    private Weather regionWeather;
    private Water regionWater;

    private int regionId;
    private int regionSize;
    private List<Integer> adjacencyReg;
    private List<Integer> adjacencyDir;
    private List<Integer> adjacencyDist;
    private Terrain regionTerrain;

    public Region (int regionId) {
        this.regionId = regionId;
    }
    public void setAdjacency (HashSet<Integer> adj) {
        adjacencyReg = new ArrayList<>(adj);
    }
    public void setDistance (List<Region> regionList) {
        adjacencyDist = new ArrayList<>();
        for (Integer targetRegion : adjacencyReg) {
            adjacencyDist.add( regionSize + regionList.get(targetRegion).getRegionSize() );
        }
    }
    public void setSize(HashSet<Integer> adj) {
        Random rand = new Random();
        this.regionSize = adj.size() * numRegionsMod * (rand.nextInt(maxRegionSize-minRegionSize)+minRegionSize);
    }
    public List<Integer> getAdjacencyReg () {
        return adjacencyReg;
    }
    public List<Integer> getAdjacencyDist () {
        return adjacencyDist;
    }
    public int getRegionSize() {
        return regionSize;
    }
    @Override
    public String toString() {
        return "Region: "+regionId+" || Adj: "+adjacencyReg+" || Size: "+regionSize+" || Dist: "+adjacencyDist;
    }
}
