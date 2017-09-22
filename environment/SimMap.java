package environment;
import java.util.*;

public class SimMap {
    private static SimMap instance;
    public static final int numOfRegions = 100;
    public static final int maxSurfaceArea = 10;
    public static final int maxSize = 10;
    public static final int minSize = 1;
    public static final int mapWidth = 5;
    private List<Region> regionList = new ArrayList<>(numOfRegions);
    
    private SimMap () {
        mapInit();
    }

    private void mapInit () {
        List<HashSet<Integer>> adjacencyList = new ArrayList<>(numOfRegions);
        Random rand = new Random();
        int region;
        int size;
        for(region = 0; region < numOfRegions; region++) {
            size = rand.nextInt(maxSize-minSize)+minSize; 
            regionList.add(new Region(region, size));
            adjacencyList.add(new HashSet<Integer>());
        }
        for (region = 0; region < numOfRegions; region++) {
            setAdjacency(region, adjacencyList);
        }
        for(region = 0; region < numOfRegions; region++) {
            regionList.get(region).setAdjacency(adjacencyList.get(region));
            System.out.println("Region: "+region+" Adj: "+regionList.get(region).getAdjacencyReg()); 
        }
    }

    private void setAdjacency(int regionId, List<HashSet<Integer>> adjacencyList) {
        Random rand = new Random();
        int surfaceArea = rand.nextInt(maxSurfaceArea)+1;
        int val=0;
        for(int i=0;i<surfaceArea;i++) {
            val = rand.nextInt(10);
            val = (regionId-mapWidth)+();///
            if (val!=regionId&&val>=0&&val<numOfRegions) {
                adjacencyList.get(regionId).add(val);
                adjacencyList.get(val).add(regionId);
            };
        }
        if (adjacencyList.get(regionId)==null) 
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
