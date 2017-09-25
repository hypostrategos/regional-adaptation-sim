package environment;
public class Flora {
	private int[] affinities; //moisture temperature wind elevation
	private String name;
	private double growthRate;
	private int coverCap;
	private int cover;
	private double size;
	private double waterConsumption;

	public Flora() {}

	// public Flora(Fauna fauna) {
	// 	this.name = fauna.name;
	// 	this.cover = fauna.cover/10;
	// 	this.ferocity = fauna.ferocity;
	// 	this.size = fauna.size;
	// 	this.speed = fauna.speed;
	// 	this.strength = fauna.strength;
	// }
	public Flora(Region myRegion, String name) {
		int size = myRegion.getSize();
		this.name = name;
		affinities = new int[4];
		for (int affinity : affinities) {
			affinity = SimMap.rand.nextInt(3)-1;
			System.out.println(affinity);
		}			
		System.out.println();
		// this.coverCap = myRegion.getElevation();
		// this.cover = 10*size*SimMap.rand.nextInt(10);
		// this.growthRate;
	}

	// public int getStrength() {
	// 	return strength;
	// }
	// public String getName() {
	// 	return name;
	// }
	// public void updateFauna() {
	// 	this.cover+=SimMap.rand.nextInt(300)-100;
	// }
	// public int getPopulation(){
	// 	return cover;
	// }
	// public void changePopulation(Integer cover) {
	// 	this.cover+=cover;
	// }

	// public void battle(Fauna attackingFauna) {
	// 	// int i = Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0); System.out.println(i);
	// 	changePopulation(Math.min( ( ( 50*strength-50*attackingFauna.getStrength() ) ),0) ); 
	// }
 //    @Override
 //    public String toString() {
 //    	return name+" "+cover;
 //    }

}