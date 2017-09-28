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
	}
	public void battle(Fauna enemyFauna) {
		double result = strength-enemyFauna.getStrength();
		if (result>0) {
			// System.out.print(name+" v "+enemyFauna.getName()+" "+result+"||");
			result = result * population * (6-enemyFauna.getSize()) * SimMap.rand.nextDouble()/20;
			// System.out.println(name+" v "+enemyFauna.getName()+" "+result);
			enemyFauna.changePopulation(-(int)result);
			food+=result*(enemyFauna.getSize()/2);
			if(population>enemyFauna.getPopulation()) expansionCapacity=0.5;
			else expansionCapacity=1;
		}
	}
	public void multiply (List<Integer> adjacencyReg) {
        if(population>2000&&SimMap.rand.nextInt(10)>8) {
            Region region = SimMap.regionList.get(Namer.getRandomItem(adjacencyReg));
            region.regionFauna.putIfAbsent(name, new Carnivore(this, region));
        }
	}
    @Override
    public String toString() {
    	return name+" "+population+
    	" "+"C"+" "+(int)food+" "+
    	// " "+ferocity+" "+size+" "+strength+
    	"||";
    }
}