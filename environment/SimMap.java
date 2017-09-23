package environment;
import java.util.*;

public class SimMap {
    private static SimMap instance;
    public static final int numOfRegions = 20;
    public static final int maxSurfaceArea = 5;
    public static final int mapWidth = 10;
    public static int counter;

    private List<Region> regionList = new ArrayList<>(numOfRegions);
    
    private SimMap () {
        mapInit();
    }

    public void mapUpdate() {
        Random rand = new Random();
        regionList.forEach(region->region.regionWeather.updateWeather(rand,counter+=0.1));
    }

    public void mapDisplay() {
        System.out.println(regionList); 
    }

    private void mapInit () {
        List<HashSet<Integer>> adjacencyList = new ArrayList<>(numOfRegions);
        int region;
        for(region = 0; region < numOfRegions; region++) {
            regionList.add(new Region(region));
            adjacencyList.add(new HashSet<Integer>());
        }
        for (region = 0; region < numOfRegions; region++) {
            setAdjacency(region, adjacencyList);
        }
        for (region = 0; region < numOfRegions; region++) {
            regionList.get(region).setAdjacency(adjacencyList.get(region));
            regionList.get(region).setSize(adjacencyList.get(region));
            regionList.get(region).setInitial();
        }
        for(region = 0; region < numOfRegions; region++) {
            regionList.get(region).setDistance(regionList, mapWidth);
        }
    }

    private void setAdjacency(int regionId, List<HashSet<Integer>> adjacencyList) {
        Random rand = new Random();
        int surfaceArea = rand.nextInt(maxSurfaceArea)+1;
        int val=0;
        int x = regionId%mapWidth;
        int y = regionId/mapWidth;
        for(int i=0;i<surfaceArea;i++) { //random number of adjacencies generated based on maxSurfaceArea
            val = (x-1<0||x+1>=mapWidth)?rand.nextInt(2):rand.nextInt(3); 
            int xd = val + ((x-1<0)?x:x-1);
            val = (y-1<0||y+1>=numOfRegions/mapWidth)?rand.nextInt(2):rand.nextInt(3);
            int yd = val + ((y-1<0)?y:y-1);
            val = xd+yd*mapWidth; //converts x&y displacement into target adjacency region's id
            
            if (val!=regionId) { //update current region HashSet and that of the region it is adjacent to
                adjacencyList.get(regionId).add(val);
                adjacencyList.get(val).add(regionId);
            };
        }
        if (adjacencyList.get(regionId).isEmpty()) //makes sure each region's adjacency list has at least one entity
            setAdjacency(regionId, adjacencyList);
    }

    public static synchronized SimMap getInstance() {
        if (instance==null) {
            instance = new SimMap();
        }
        return instance;
    }

    public List<Region> getRegionList() {
        return regionList;
    }
    public Region getRegionList(Integer key) {
        return regionList.get(key);
    }
}