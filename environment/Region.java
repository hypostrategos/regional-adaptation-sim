package environment;
import java.util.*;

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
    private List<Integer> adjacencyReg;
    private List<Integer> adjacencyDir = new ArrayList<>();
    private List<Integer> adjacencyDist = new ArrayList<>();
    private Terrain regionTerrain;

    public Region (int regionId, int regionSize) {
       this.regionId = regionId;
       this.regionSize = regionSize;
    }
    public void setAdjacency (HashSet<Integer> adj) {
        adjacencyReg = new ArrayList<>(adj);
//        for(Integer adjacency : adjacencyReg) {
            
  //      }
    }
    public List<Integer> getAdjacencyReg () {
        return adjacencyReg;
    }
}
