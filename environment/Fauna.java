package environment;

import java.util.*;
public class Fauna {
	protected String name;
	protected Integer population;
	protected int ferocity;
	protected int size;
	protected int strength;
	protected double food=1000;
	protected Region myRegion;
	protected double growthRate;
	protected int regionSizeLimit;

	public Fauna() {}
	public Fauna(Fauna fauna, Region myRegion) {
		this.myRegion = myRegion;
		this.name = fauna.name;
		this.population = fauna.getPopulation()/10;
		fauna.changePopulation(-this.population);
		this.ferocity = fauna.ferocity;
		this.size = fauna.size;
		this.strength = fauna.strength;
		this.growthRate = fauna.growthRate;
		setRegionSizeLimit();
	}
	public Fauna(Region myRegion, String name) {
		this.name = name;
		this.myRegion = myRegion;
		population = (int)(10*myRegion.getSize()*(SimMap.rand.nextInt(10)+1));
		ferocity = SimMap.rand.nextInt(5)+1;
		size = SimMap.rand.nextInt(5)+1;
		strength = ferocity+size;
		growthRate = 0.02 + 0.20*SimMap.rand.nextDouble()*(1.5-size/5.0);
		setRegionSizeLimit();
	}
	protected void setRegionSizeLimit() {
		regionSizeLimit = myRegion.getSize()*(6-size)*100+1000;
	}
	public int getStrength() {
		return strength;
	}
	public String getName() {
		return name;
	}
	public int getPopulation(){
		return population;
	}
	public void changePopulation(Integer population) {
		population+=population;
	}
	public double getFood() {
		return food;
	}
	public int getSize() {
		return size;
	}
	public void faunaUpdate(int crowding) {
		double consumption;
		if (food>population*2) {
			consumption = (population*SimMap.rand.nextDouble())/2.0;
			food-=consumption/(6-size);
			consumption = population<regionSizeLimit ? consumption : consumption/(2.0+crowding) ;
		} else if (food>0) {
			consumption = (population*SimMap.rand.nextDouble())/4.0;
			food-=consumption/(6-size); consumption/=-2.0;
		} else {
			consumption = -(population*SimMap.rand.nextDouble())/6.0;
			food=0;
		}
		population+=(int)( consumption*growthRate );
	}
	public void multiply(List<Integer> adjacencyReg) {}
	public void grazeOn(Flora flora) {}
	public void battle(Fauna enemyFauna) {}

	@Override
    public String toString() {
    	return name+" "+population+" "+(int)food
    	// +" "+regionSizeLimit
    	// +" "+ferocity+" "+size+" "+strength
    	;
    }

}