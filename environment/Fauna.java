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
	protected double expansionCapacity;

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
	}
	public Fauna(Region myRegion, String name) {
		int regionSize = myRegion.getSize();
		this.name = name;
		this.myRegion = myRegion;
		population = (int)(10*regionSize*(SimMap.rand.nextInt(10)+1));
		ferocity = SimMap.rand.nextInt(5)+1;
		size = SimMap.rand.nextInt(5)+1;
		strength = ferocity+size;
		growthRate = 0.02 + 0.20*SimMap.rand.nextDouble()*(1.5-size/5.0);
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
	public double getFodd() {
		return food;
	}
	public int getSize() {
		return size;
	}
	public void expansionCapacityMod(double mod) {
		expansionCapacity=mod;
	}
	public void faunaUpdate() {
		double consumption;
		if (food>population*2) {
			consumption = (population*SimMap.rand.nextDouble())/2.0;
			// System.out.print(food+"->");
			food=Math.min((food-expansionCapacity*consumption/(6-size)),population*2);
			// System.out.println(food);
		} else if (food>0) {
			consumption = (population*SimMap.rand.nextDouble())/4.0;
			food-=consumption/(6-size); consumption/=-2.0;
		} else {
			consumption = (population*SimMap.rand.nextDouble())/6.0;
			food=0;
		}
		population+=(int)(consumption*growthRate);
	}
	public void multiply(List<Integer> adjacencyReg) {}
	public void grazeOn(Flora flora) {}
	public void battle(Fauna enemyFauna) {}
	// public void battle(Fauna enemyFauna) {
	// 	double result = strength-enemyFauna.getStrength();
	// 	if (result>0) {
	// 		// System.out.print(name+" v "+enemyFauna.getName()+" "+result+"||");
	// 		result = result * population * (6-enemyFauna.getSize()) * SimMap.rand.nextDouble()/20;
	// 		// System.out.println(name+" v "+enemyFauna.getName()+" "+result);
	// 		enemyFauna.changePopulation(-(int)result);
	// 		meat+=result*(enemyFauna.getSize()/2);
	// 		if(population>enemyFauna.getPopulation()) expansionCapacity=0.5;
	// 		else expansionCapacity=1;
	// 	}
	// }
	// public void grazeOn(Flora flora) {
	// 	double consumption;
	// 	consumption = (1-carnCoeff)*population*SimMap.rand.nextDouble()*(size/(10*flora.getSize()));
	// 	// System.out.println(consumption);
	// 	flora.changeCover((int)-consumption);
	// 	veg+=consumption*flora.getSize();
	// 	if(population>flora.getCover()) expansionCapacity=0.5;
	// 	else expansionCapacity=1;
	// }
}