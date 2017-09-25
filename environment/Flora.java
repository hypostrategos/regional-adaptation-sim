package environment;
public class Flora {
	private int[] affinities; //elevation moisture temperature wind 
	private double regionAffinity;
	private String name;
	public int maxCover;
	private int cover;
	private double size;
	private double growthRate;
	private Region myRegion;

	public Flora() {}

	public Flora(Flora flora, Region myRegion) {
		this.myRegion = myRegion;
		this.name = flora.name;
		this.cover = 300;
		this.affinities=flora.affinities.clone();
		setAffinity();
		setMaxCover();
		setRest();
	}
	public Flora(Region myRegion, String name) {
		int size = myRegion.getSize();
		this.name = name;
		this.myRegion = myRegion;
		affinities = new int[4];
		for (int i=0; i<affinities.length; i++) {
			affinities[i] = SimMap.rand.nextInt(3)-1;
		}			
		setAffinity();
		setMaxCover();
		setCover();
		setRest();
	}
	public String getName() {
		return name;
	}
	public int getCover() {
		return cover;
	}
	public void setRest() {
		double mod = SimMap.rand.nextDouble();
		size = 1.0-mod;
		growthRate = regionAffinity+mod;
	}
	public void setRest(double size) {
		this.size = size;
		growthRate = regionAffinity+(1.0-size);
	}
	private void setMaxCover() {
		maxCover = (int)(regionAffinity*1000*myRegion.getSize()+(SimMap.rand.nextInt(10)+1)*2000);
	}
	private void setCover() {
		cover = 100*SimMap.rand.nextInt(10)+100;
	}
	private void setAffinity () {
		this.regionAffinity = subAffinity(myRegion.getElevation(), affinities[0])+
		subAffinity(myRegion.regionWeather.getTemperatureInit(), affinities[1])+
		subAffinity(myRegion.regionWeather.getPrecipitationInit(), affinities[2])+
		subAffinity(myRegion.regionWeather.getWindInit(), affinities[3]);
	}
	private double subAffinity(double regionVal, int affinity) {
		if (affinity==-1) return 1-regionVal;
		else if(affinity==1) return regionVal;
		else return -4*(regionVal-0.5)*(regionVal-0.5)+1;
	}
	public void growFlora(int crowding) {
		int total = Math.max((6-crowding),1)*maxCover;
		if (cover<total)
			cover+=10*growthRate*(SimMap.rand.nextInt(10)+1)*(total/(maxCover+cover));
	}
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
    @Override
    public String toString() {
    	return name+
    	// " "+affinities[0]+affinities[1]+affinities[2]+affinities[3]+
    	// " "+regionAffinity+" "+size+" "+growthRate+
    	// " "+maxCover+
    	" "+cover
    	;
    }

}