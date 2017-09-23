package environment;
import java.util.*;

public class Region {
    private enum Directions {
        NW, N, NE, W, E, SW, S, SE
    }
    public static final int maxRegionSize = 10;
    public static final int minRegionSize = 1;
    public static final int maxRegionDistance = 10;
    public static final int minRegionDistance = 1;
    public static final int numRegionsMod = 1;

    public Fauna regionFauna;
    public Flora regionFlora;
    public Weather regionWeather;
    public Water regionWater;

    private int regionId;
    private int regionSize;
    private List<Integer> adjacencyReg;
    private List<Directions> adjacencyDir;
    private List<Integer> adjacencyDist;
    public Double regionElevationInit;
    private Double regionElevation; 

    public Region (int regionId) {
        this.regionId = regionId;
    }
    public void setAdjacency (HashSet<Integer> adj) {
        adjacencyReg = new ArrayList<>(adj);
    }
    public void setDistance (List<Region> regionList, int mapWidth) {
        adjacencyDist = new ArrayList<>();
        adjacencyDir = new ArrayList<>();
        Integer adjacencies = adjacencyReg.size();
        for (Integer targetRegion : adjacencyReg) {
            Region targRegionObj = regionList.get(targetRegion);

            adjacencyDist.add( regionSize + targRegionObj.getRegionSize() );

            setDirection(targetRegion, mapWidth);
            setElevation(targRegionObj);
        }
        regionElevation = regionElevation/(adjacencies+1);
    }
    private void setDirection(Integer targetRegion, int mapWidth) {
        int diff = regionId - targetRegion;
        if (diff>mapWidth) 
            adjacencyDir.add(Directions.NW);
        else if (diff==mapWidth)
            adjacencyDir.add(Directions.N);
        else if (diff>1)
            adjacencyDir.add(Directions.NE);
        else if (diff==1)
            adjacencyDir.add(Directions.W);
        else if (diff==-1)
            adjacencyDir.add(Directions.E);
        else if (diff>-mapWidth)
            adjacencyDir.add(Directions.SW);
        else if (diff==-mapWidth)
            adjacencyDir.add(Directions.S);
        else if (diff<-mapWidth)
            adjacencyDir.add(Directions.SE);
    }
    public void setInitial() {
        Random rand = new Random();
        setElevation(rand);

        regionWeather = new Weather();
        regionWeather.setTemperature(rand);
        regionWeather.setPrecipitation(rand);
        regionWeather.setWind(rand);
    }
    public void setElevation(Random rand) {
        regionElevationInit = rand.nextDouble();
        regionElevation = regionElevationInit;
    }
    public void setElevation(Region targetRegion) {
        regionElevation += targetRegion.regionElevationInit;
    }
    public Double getElevation() {
        return regionElevation;
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
        return "Region: "+regionId+//" || Adj: "+adjacencyReg+" || Size: "+regionSize+" || Dist: "+adjacencyDist +" || Dir: "+adjacencyDir+" "+regionElevation+" "+regionElevationInit+
        " "+regionWeather.getTemperature()+" "+regionWeather.getPrecipitation()+" "+regionWeather.getWind()+
        "\n";
    }
}
