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
	private int meat=1000;
	private int veg=1000;
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
		this.ferocity = SimMap.rand.nextInt(5);
		this.size = SimMap.rand.nextInt(5);
		this.speed = SimMap.rand.nextInt(5);
		this.strength = this.ferocity+this.size+this.speed;
		this.carnCoeff = SimMap.rand.nextDouble();
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
	public int getVeg() {
		return veg;
	}
	public void faunaUpdate() {
		int consumption;
		if (meat>0) {
			consumption = (int)(population*carnCoeff*SimMap.rand.nextDouble()+100);
			meat-=consumption;
		} else if (veg>0) {
			consumption = (int)(population*(1-carnCoeff)*SimMap.rand.nextDouble()+100);
			veg-=consumption;
		} else {
			consumption = -(int)(population*SimMap.rand.nextDouble()+100);
		}
		population+=consumption;
	}
	public void battle(Fauna enemyFauna) {
		// int i = Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0); System.out.println(i);
		// int result = (Math.min( ( ( strength-attackingFauna.getStrength() ) ),0) ); 
		int result = strength-enemyFauna.getStrength();
		if (result>0) {
			result = (int)((result/10+1) * population * SimMap.rand.nextDouble());
			enemyFauna.changePopulation(-result);
			meat+=result;
		}
	}
	public void grazeOn(Flora flora) {
		int consumption;
		consumption = (int)((1-carnCoeff)*population*SimMap.rand.nextDouble());
		if (flora.getCover()>consumption) {
			flora.changeCover(-consumption);
			veg+=consumption;
		}
	}
    @Override
    public String toString() {
    	return name+" "+population+" "+(carnCoeff>0.5?"Carn":"Herb");
    }
}