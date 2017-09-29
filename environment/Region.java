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
    public void setDistance (int mapWidth) {
        adjacencyDist = new ArrayList<>();
        adjacencyDir = new ArrayList<>();
        Integer adjacencies = adjacencyReg.size();
        for (Integer targetRegion : adjacencyReg) {
            Region targRegionObj = SimMap.regionList.get(targetRegion);

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
            switch(flag) {
                case 0 : name = Namer.genName(0); regionFlora.put(name, new Flora(this, name));
                break;
                case 1 : 
                if (i%3==1) {
                    name = Namer.genName(1);
                    regionFauna.put(name, new Carnivore(this, name));
                }
                else {
                    name = Namer.genName(2);
                    regionFauna.put(name, new Herbivore(this, name));
                }
                break;
            }
        }
    }

    public void updateFauna() {
        int crowding = regionFauna.size();
        List<Fauna> badFauna = new ArrayList<>();

        regionFauna.values().stream()
        .filter(fauna -> (regionFauna.size()==1&&fauna instanceof Carnivore)
            ||(regionFlora.size()==0&&fauna instanceof Herbivore))
        .forEach(fauna -> {
            fauna.setRegion( Namer.getRandomItem(adjacencyReg) )
            .regionFauna.putIfAbsent(fauna.getName(), fauna); 
            badFauna.add(fauna);
        } );

        regionFauna.values().stream()
        .filter(fauna->(fauna instanceof Herbivore && fauna.getFood()<2*fauna.getPopulation()) || fauna.getFood()<1 )
        .forEach(herb -> {
            regionFlora.values().stream()
            .filter(flora->flora.getFoliage()>herb.getPopulation()/2)
            .forEach(flora->herb.grazeOn(flora));
        } );
        regionFauna.values().stream()
        .filter(fauna->(fauna instanceof Carnivore && fauna.getFood()<2*fauna.getPopulation()) || fauna.getFood()<1 )
        .forEach(carn -> { 
            regionFauna.values().stream()
            .filter(fauna->carn!=fauna&&fauna.getPopulation()>carn.getPopulation())
            .forEach(fauna->carn.battle(fauna));
        } );
        regionFauna.values().stream()
        .forEach(fauna-> {
            fauna.faunaUpdate(crowding);
            if (fauna.getPopulation()<=0) badFauna.add(fauna);
        } );

        if (!badFauna.isEmpty()) 
            badFauna.forEach ( fauna -> regionFauna.remove(fauna.getName()) );
        
        regionFauna.values()
        .forEach( fauna -> fauna.multiply(adjacencyReg) );
    }
    public void updateFlora() {
        int crowding = regionFlora.size();
        List<Flora> badFlora = new ArrayList<>();
        regionFlora.values().stream()
        .forEach( flora -> {
            flora.floraUpdate(crowding);
            if(flora.getCover()<=0) badFlora.add(flora);
        } );

        if (!badFlora.isEmpty()) 
            badFlora.forEach ( flora -> regionFlora.remove(flora.getName()) );

        regionFlora.values()
        .forEach( flora -> flora.multiply(adjacencyReg) );
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
    public int getRegionId() {
        return regionId;
    }
    @Override
    public String toString() {
        return "Region: "+regionId+
        // " || Adj: "+adjacencyReg+" || Size: "+regionSize+" || Dist: "+adjacencyDist +" || Dir: "+adjacencyDir+
        // " "+regionElevationInit+" "+regionElevation+
        // " "+regionWeather.getTemperature()+" "+regionWeather.getPrecipitation()+" "+regionWeather.getWind()+
        // " "+regionFlora.values()+"\n"+    
        " "+regionFauna.values()+
        "\n";
    }
}