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
		this.cover = flora.getCover()/10;
		flora.changeCover(-this.cover);
		this.affinities=flora.affinities.clone();
		setAffinity();
		setRest();
		setMaxCover();
	}
	public Flora(Region myRegion, String name) {
		this.name = name;
		this.myRegion = myRegion;
		affinities = new int[4];
		for (int i=0; i<affinities.length; i++) {
			affinities[i] = SimMap.rand.nextInt(3)-1;
		}			
		setAffinity();
		setRest();
		setMaxCover();
		setCover();
	}
	public String getName() {
		return name;
	}
	public int getCover() {
		return cover;
	}
	public double getSize() {
		return size;
	}
	public void setRest() {
		double mod = SimMap.rand.nextDouble();
		size = 1.0-mod+0.1;
		growthRate = regionAffinity+mod;
	}
	public void setRest(double size) {
		this.size = size;
		growthRate = regionAffinity+(1.0-size);
	}
	private void setMaxCover() {
		maxCover = (int)(regionAffinity*200*myRegion.getSize()/size)+2000;
	}
	private void setCover() {
		cover = 100*SimMap.rand.nextInt(10)+1000;
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
			// cover+=growthRate*100*(total/(maxCover+cover));
			cover+=(cover/crowding)*(growthRate/10.0)+100;
		// System.out.println((cover/crowding)*(growthRate/10.0));
	}
	public void changeCover(int cover) {
		this.cover+=cover;
	}
    @Override
    public String toString() {
    	return name+
    	// " "+affinities[0]+affinities[1]+affinities[2]+affinities[3]+
    	// " "+regionAffinity+" "+growthRate+
    	// " "+size+
    	" "+maxCover+
    	" "+cover
    	;
    }
}