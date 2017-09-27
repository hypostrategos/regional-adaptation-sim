package environment;

import java.util.*;
public class Fauna {
	private String name;
	private Integer population;
	private int ferocity;
	private int size;
	private Double carnCoeff;
	// private Double rateVegConsumption;
	private int strength;
	private double meat=1000;
	private double veg=1000;
	private Region myRegion;
	private double growthRate;
	private double expansionCapacity;

	public Fauna() {}
	public Fauna(Fauna fauna, Region myRegion) {
		this.myRegion = myRegion;
		this.name = fauna.name;
		this.population = fauna.getPopulation()/10;
		fauna.changePopulation(-this.population);
		this.ferocity = fauna.ferocity;
		this.size = fauna.size;
		this.strength = fauna.strength;
		this.carnCoeff = fauna.carnCoeff;
		this.growthRate = fauna.growthRate;
	}
	public Fauna(Region myRegion, String name) {
		int regionSize = myRegion.getSize();
		this.name = name;
		this.myRegion = myRegion;
		this.carnCoeff = SimMap.rand.nextDouble();
		this.population = (int)(10*regionSize*(SimMap.rand.nextInt(10)+1)*(1.5-carnCoeff));
		this.ferocity = (int)(carnCoeff*10);
		this.size = SimMap.rand.nextInt(5)+1;
		this.strength = this.ferocity+this.size;
		this.growthRate = 0.02 + 0.20*SimMap.rand.nextDouble()*(1.5-carnCoeff)*(1.5-size/5.0);
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
	public double getCarn() {
		return carnCoeff;
	}
	public void changePopulation(Integer population) {
		this.population+=population;
	}
	public double getVeg() {
		return veg;
	}
	public double getMeat() {
		return meat;
	}
	public int getSize() {
		return size;
	}
	public void expansionCapacityMod(double mod) {
		expansionCapacity=mod;
	}
	public void faunaUpdate() {
		double consumption;
		if (meat>population*2) {
			consumption = (population*carnCoeff*SimMap.rand.nextDouble())/2.0;
			// System.out.print(meat+"->");
			meat=Math.min((meat-expansionCapacity*consumption/(6-size)),population*2);
			// System.out.println(meat);
		} else if (veg>population*2) {
			consumption = (population*(1-carnCoeff)*SimMap.rand.nextDouble())/2.0;
			veg=Math.min((veg-expansionCapacity*consumption/(6-size)),population*2);
		} else if (meat>0) {
			consumption = (population*carnCoeff*SimMap.rand.nextDouble())/4.0;
			meat-=consumption/(6-size); consumption/=-2.0;
		} else if (veg>0) {
			consumption = (population*(1-carnCoeff)*SimMap.rand.nextDouble())/4.0;
			veg-=consumption/(6-size); consumption/=-2.0;
		} else {
			consumption = (population*SimMap.rand.nextDouble())/6.0;
			meat=0; veg=0; consumption=-consumption;
		}
		population+=(int)(consumption*growthRate);
	}
	public void battle(Fauna enemyFauna) {
		double result = strength-enemyFauna.getStrength();
		if (result>0) {
			// System.out.print(name+" v "+enemyFauna.getName()+" "+result+"||");
			result = result * population * (6-enemyFauna.getSize()) * SimMap.rand.nextDouble()/20;
			// System.out.println(name+" v "+enemyFauna.getName()+" "+result);
			enemyFauna.changePopulation(-(int)result);
			meat+=result*(enemyFauna.getSize()/2);
			if(population>enemyFauna.getPopulation()) expansionCapacity=0.5;
			else expansionCapacity=1;
		}
	}
	public void grazeOn(Flora flora) {
		double consumption;
		consumption = (1-carnCoeff)*population*SimMap.rand.nextDouble()*(size/(10*flora.getSize()));
		// System.out.println(consumption);
		flora.changeCover((int)-consumption);
		veg+=consumption*flora.getSize();
		if(population>flora.getCover()) expansionCapacity=0.5;
		else expansionCapacity=1;
	}
    @Override
    public String toString() {
    	return name+" "+population+
    	" "+(carnCoeff>0.5?"C":"H")+" "+(int)meat+" "+(int)veg+
    	// " "+ferocity+" "+size+" "+strength+
    	"||";
    }
}