package environment;
import java.util.*;

public class SimMap {
    private static SimMap instance;
    public static final int numOfRegions = 10;
    private Map<Integer, Region> regionList = new HashMap<>(numOfRegions);
    private Map<Integer, HashSet<Integer>> adjacencyList = new HashMap<>(numOfRegions);
    
    private SimMap () {
        mapInit();
        for(int i = 0; i < numOfRegions; i++) {
            System.out.println("Region: "+i+" Adj: "+adjacencyList.get(i)); 
        }
    }

    private void mapInit () {
        for(int i = 0; i < numOfRegions; i++) {
            regionList.put(i, new Region(i));
            adjacencyList.put(i, new HashSet<Integer>());
        }
        for (int i = 0; i< numOfRegions; i++) {
            setAdjacency(i);
        }
    }

    private void setAdjacency(int regionId) {
        Random rand = new Random();
        int times = rand.nextInt(numOfRegions)+1;
        int val=0;
        for(int i=0;i<times;i++) {
            val = rand.nextInt(numOfRegions);
            if (val!=regionId) {
                adjacencyList.get(regionId).add(val);
                adjacencyList.get(val).add(regionId);
            };
        }
    }

    public static synchronized SimMap getInstance() {
        if (instance==null) {
            instance = new SimMap();
        }
        return instance;
    }

    public Map<Integer, Region> getRegionList() {
        return regionList;
    }
    public Region getRegionList(Integer key) {
        return regionList.get(key);
    }
}
