package environment;
import java.util.*;

public class Herbivore extends Fauna {
	public Herbivore() {
		super();
	}
	public Herbivore(Herbivore herbivore, Region myRegion) {
		super(herbivore, myRegion);
	}
	public Herbivore(Region myRegion, String name) {
		super(myRegion, name);
	}
    @Override
	public void grazeOn(Flora flora) {
		double consumption;
		consumption = population*SimMap.rand.nextDouble()*(size/(10*flora.getSize()));
		flora.changeFoliage((int)-consumption);
		food+=consumption*flora.getSize();
	}
    @Override
	public void multiply (List<Integer> adjacencyReg) {
        if(population>2000&&SimMap.rand.nextInt(10)>8) {
            Region region = SimMap.regionList.get(Namer.getRandomItem(adjacencyReg));
            region.regionFauna.putIfAbsent(name, new Herbivore(this, region));
        }
	}
    @Override
    public String toString() {
    	return super.toString() +" HERB "+"||";
    }
}