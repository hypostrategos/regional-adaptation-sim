package environment;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class Region {
    private enum Directions {
        NW, N, NE, W, E, SW, S, SE
    }
    public static final int maxRegionSize = 10;
    public static final int minRegionSize = 1;
    public static final int maxRegionDistance = 10;
    public static final int minRegionDistance = 1;
    public static final int numRegionsMod = 1;

    public Map<String, Fauna> regionFauna=new HashMap<>();
    public Map<String, Flora> regionFlora=new HashMap<>();
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
        setElevation();
        regionWeather = new Weather();
        regionWeather.setTemperature();
        regionWeather.setPrecipitation();
        regionWeather.setWind();
        setBio(0);
        setBio(1);
    }
    public void setBio(int flag) {
        String name;
        for (int i = 0; i<SimMap.rand.nextInt(4)+1; i++) {
            name = Namer.genName();
            switch(flag) {
                case 0 : regionFlora.put(name, new Flora(this, name));
                break;
                case 1 : regionFauna.put(name, new Fauna(this, name));
                break;
            }
        }
    }

    public void updateFauna(List<Region> regionList) {
        List<Fauna> badFauna = new ArrayList<>();
        List<Fauna> tempFauna = new ArrayList<>();
        regionFauna.forEach( (name, fauna) -> { tempFauna.add(fauna); 
            if(fauna.getPopulation()<=0) badFauna.add(fauna); } );

        tempFauna.forEach( fauna -> { 
            if (tempFauna.size()>1&&fauna.getCarn()>0.5&&fauna.getMeat()<fauna.getPopulation()*2) {
                for (Fauna enemyFauna : tempFauna) {
                    if (fauna!=enemyFauna) {
                        fauna.battle(enemyFauna);
                    }
                }
            } else if(regionFlora.size()>0&&fauna.getVeg()<fauna.getPopulation()*2) { 
                for (Flora flora : regionFlora.values()) {
                    fauna.grazeOn(flora);
                }
            } else {
                fauna.expansionCapacityMod(0.1);
            }
            fauna.faunaUpdate();
        } );

        if (!badFauna.isEmpty()) badFauna.forEach ( fauna -> { 
            regionFauna.remove(fauna.getName()); tempFauna.remove(fauna); } );

        for (Fauna fauna : tempFauna) {
            if(fauna.getPopulation()>2000&&SimMap.rand.nextInt(10)>8) {
                Region region = regionList.get(Namer.getRandomItem(adjacencyReg));
                region.regionFauna.putIfAbsent(fauna.getName(), new Fauna(fauna, region));
            }
        }
    }
    public void updateFlora(List<Region> regionList) {
        int crowding = regionFlora.size();
        List<Flora> badFlora = new ArrayList<>();
        List<Flora> tempFlora = new ArrayList<>();
        regionFlora.forEach( (name, flora) -> { tempFlora.add(flora); flora.growFlora(crowding);
        if(flora.getCover()<=0) badFlora.add(flora); } );
        if (!badFlora.isEmpty()) badFlora.forEach ( flora -> { 
            regionFlora.remove(flora.getName()); tempFlora.remove(flora); } );

        for (Flora flora : tempFlora) {
            if(flora.getCover()>2000&&SimMap.rand.nextInt(10)>8) {
                Region region = regionList.get(Namer.getRandomItem(adjacencyReg));
                region.regionFlora.putIfAbsent(flora.getName(), new Flora(flora, region));
            }
        }
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
        return "Region: "+regionId+
        // " || Adj: "+adjacencyReg+" || Size: "+regionSize+" || Dist: "+adjacencyDist +" || Dir: "+adjacencyDir+
        // " "+regionElevationInit+" "+regionElevation+
        // " "+regionWeather.getTemperature()+" "+regionWeather.getPrecipitation()+" "+regionWeather.getWind()+
        // " "+regionFlora.values()+    
        " "+regionFauna.values()+
        "\n";
    }
}
