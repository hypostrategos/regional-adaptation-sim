package environment;

import java.util.*;
public class Fauna {
	private String name;
	private Integer population;
	private int ferocity;
	private int size;
	private int speed;
	private Double rateMeatConsumption;
	private Double rateVegConsumption;
	private int strength;

	public Fauna(Fauna fauna) {
		this.name = fauna.name;
		this.population = fauna.population/10;
		this.ferocity = fauna.ferocity;
		this.size = fauna.size;
		this.speed = fauna.speed;
		this.strength = fauna.strength;
	}
	public Fauna(Region myRegion, String name) {
		int size = myRegion.getSize();
		this.name = name;
		this.population = 10*size*SimMap.rand.nextInt(10);
		this.ferocity = SimMap.rand.nextInt(5);
		this.size = SimMap.rand.nextInt(5);
		this.speed = SimMap.rand.nextInt(5);
		this.strength = this.ferocity+this.size+this.speed;
	}

	public int getStrength() {
		return strength;
	}
	public String getName() {
		return name;
	}
	public void updateFauna() {
		this.population+=SimMap.rand.nextInt(300)-100;
	}
	public int getPopulation(){
		return population;
	}
	public void changePopulation(Integer population) {
		this.population+=population;
	}

	public void battle(Fauna attackingFauna) {
		// int i = Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0); System.out.println(i);
		changePopulation(Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0) ); 
	}
    @Override
    public String toString() {
    	return name+" "+population;
    }
}