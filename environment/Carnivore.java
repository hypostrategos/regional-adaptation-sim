package environment;
import java.util.*;

public class Carnivore extends Fauna {
	public Carnivore() {
		super();
	}
	public Carnivore(Carnivore carnivore, Region myRegion) {
		super(carnivore, myRegion);
	}
	public Carnivore(Region myRegion, String name) {
		super(myRegion, name);
		ferocity*=2;
		strength = ferocity+size;
		growthRate/=2;
		population/=2;
	}
	public void battle(Fauna enemyFauna) {
		double result = strength-enemyFauna.getStrength();
		result = result>0 ? result : enemyFauna instanceof Herbivore ? 1 : -1; 
		if (result>0) {
			// System.out.print(myRegion.getRegionId()+" "+name+" v "+enemyFauna.getName()+" "+result+"||");
			result = result * population * (6-enemyFauna.getSize()) * SimMap.rand.nextDouble()/10;
			// System.out.println(" "+result);
			enemyFauna.changePopulation(-(int)result);
			food+=result*(enemyFauna.getSize()/2);
		}
	}
    @Override
	public void multiply (List<Integer> adjacencyReg) {
        if(population>2000&&SimMap.rand.nextInt(10)>8) {
            Region region = SimMap.regionList.get(Namer.getRandomItem(adjacencyReg));
            region.regionFauna.putIfAbsent(name, new Carnivore(this, region));
        }
	}
    @Override
    public String toString() {
    	return super.toString() +" CARN "+"||";
    }
}