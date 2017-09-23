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

    public Map<String, Fauna> regionFauna;
    public Set<Flora> regionFlora;
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
    public void setInitial(Region region) {
        setElevation();

        regionWeather = new Weather();
        regionWeather.setTemperature();
        regionWeather.setPrecipitation();
        regionWeather.setWind();
        setFauna(region);
    }
    public void setFauna(Region region) {
        regionFauna = new HashMap<>();
        String name;
        for (int i = 0; i<SimMap.rand.nextInt(4); i++) {
            name = Namer.genName();
            regionFauna.put(name, new Fauna(region, name));
        }
    }
    public void updateFauna(List<Region> regionList) {
        List<Fauna> badFauna = new ArrayList<>();
        List<Fauna> tempFauna = new ArrayList<>();
        regionFauna.forEach( (name, fauna) -> { tempFauna.add(fauna);} );

        tempFauna.forEach( fauna -> { 
            for (Fauna enemyFauna : tempFauna) {
                if (fauna!=enemyFauna) {
                    enemyFauna.battle(fauna);
                }
        } } );

        tempFauna.forEach( fauna -> { fauna.updateFauna(); if(fauna.getPopulation()<=0) badFauna.add(fauna); });
        if (!badFauna.isEmpty()) badFauna.forEach ( fauna -> regionFauna.remove(fauna.getName()));

        tempFauna.forEach( fauna -> { if (fauna.getPopulation()>2000&&SimMap.rand.nextInt(10)>8) {
            regionList.get(Namer.getRandomItem(adjacencyReg))
            .regionFauna.put(fauna.getName(), new Fauna(fauna)); fauna.changePopulation(-300);
        } } );
    }
    public void setElevation() {
        regionElevationInit = SimMap.rand.nextDouble();
        regionElevation = regionElevationInit;
    }
    public void setElevation(Region targetRegion) {
        regionElevation += targetRegion.regionElevationInit;
    }
    public void setSize(HashSet<Integer> adj) {
        this.regionSize = adj.size() * numRegionsMod * (SimMap.rand.nextInt(maxRegionSize-minRegionSize)+minRegionSize);
    }
    public int getSize() {
        return regionSize;
    }
    public Double getElevation() {
        return regionElevation;
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
        //" "+regionWeather.getTemperature()+" "+regionWeather.getPrecipitation()+" "+regionWeather.getWind()+
        " "+regionFauna.values()+
        "\n";
    }
}
