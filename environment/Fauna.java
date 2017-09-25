package environment;

import java.util.*;
public class Fauna {
	private String name;
	private Integer population;
	private int ferocity;
	private int size;
	private int speed;
	private Double carnCoeff;
	// private Double rateVegConsumption;
	private int strength;
	private double meat=1000;
	private double veg=1000;
	private Region myRegion;

	public Fauna() {}
	public Fauna(Fauna fauna, Region myRegion) {
		this.myRegion = myRegion;
		this.name = fauna.name;
		this.population = fauna.population/10;
		this.ferocity = fauna.ferocity;
		this.size = fauna.size;
		this.speed = fauna.speed;
		this.strength = fauna.strength;
		this.carnCoeff = fauna.carnCoeff;
	}
	public Fauna(Region myRegion, String name) {
		int size = myRegion.getSize();
		this.name = name;
		this.myRegion = myRegion;
		this.population = 10*size*SimMap.rand.nextInt(10);
		this.carnCoeff = SimMap.rand.nextDouble();
		this.ferocity = (int)(SimMap.rand.nextInt(5)*carnCoeff);
		this.size = SimMap.rand.nextInt(5)+1;
		this.speed = SimMap.rand.nextInt(5);
		this.strength = this.ferocity+this.size+this.speed;
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
	public int getSize() {
		return size;
	}
	public void faunaUpdate() {
		double consumption;
		if (meat*2>population) {
			consumption = (population*carnCoeff*SimMap.rand.nextDouble()+100)/2;
			meat-=consumption/(6-size);
		} else if (veg*2>population) {
			consumption = (population*(1-carnCoeff)*SimMap.rand.nextDouble()+100)/2;
			veg-=consumption/(6-size);
		} else {
			consumption = -(population/10*SimMap.rand.nextDouble()+100);
			veg-=consumption/(6-size); meat-=consumption/(6-size);
		}
		population+=(int)consumption;
	}
	public void battle(Fauna enemyFauna) {
		// int i = Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0); System.out.println(i);
		// int result = (Math.min( ( ( strength-attackingFauna.getStrength() ) ),0) ); 
		int result = strength-enemyFauna.getStrength();
		if (result>0) {
			result = (int)((result/10+1) * population * SimMap.rand.nextDouble());
			enemyFauna.changePopulation(-result);
			meat+=result*(enemyFauna.getSize()/5.0);
		}
	}
	public void grazeOn(Flora flora) {
		double consumption;
		consumption = (1-carnCoeff)*population*SimMap.rand.nextDouble()*(size/(10*flora.getSize()));
		flora.changeCover((int)-consumption);
		veg+=consumption;
	}
    @Override
    public String toString() {
    	return name+" "+population+
    	" "+(carnCoeff>0.5?"C":"H")+" "+(int)meat+" "+(int)veg+
    	"||";
    }
}