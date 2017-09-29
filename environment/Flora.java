package environment;

import java.util.*;
public class Flora {
	private int[] affinities; //elevation moisture temperature wind 
	private double regionAffinity;
	private String name;
	public int maxCover;
	private int cover;
	private double size;
	private double growthRate;
	private Region myRegion;
	private int foliage;
	private int food=2000;

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
	public int getFoliage() {
		return foliage;
	}
	public double getSize() {
		return size;
	}
	public void setRest() {
		double mod = SimMap.rand.nextDouble();
		size = 1.2-mod;
		growthRate = regionAffinity+mod;
	}
	public void setRest(double size) {
		this.size = size;
		growthRate = regionAffinity+(1.0-size);
	}
	private void setMaxCover() {
		maxCover = (int)(regionAffinity*200*myRegion.getSize()/size)+1000;
	}
	private void setCover() {
		cover = 100*SimMap.rand.nextInt(10)+100;
		foliage = cover;
	}
	private void setAffinity () {
		this.regionAffinity = subAffinity(myRegion.getElevation(), affinities[0])+
		subAffinity(myRegion.regionWeather.getTemperatureInit(), affinities[1])+
		subAffinity(myRegion.regionWeather.getPrecipitationInit(), affinities[2])+
		subAffinity(myRegion.regionWeather.getWindInit(), affinities[3]);
	}
	public void changeCover(int cover) {
		this.cover += cover;
	}
	public void changeFoliage(int foliage) {
		this.foliage+=foliage;
	}
	private double subAffinity(double regionVal, int affinity) {
		if (affinity==-1) return 1-regionVal;
		else if(affinity==1) return regionVal;
		else return -4*(regionVal-0.5)*(regionVal-0.5)+1;
	}
	public void floraUpdate(int crowding) {
		growFoliage();
		spreadFlora(crowding);
	}
	private void growFoliage() {
		double change;
		double weather = subAffinity(myRegion.regionWeather.getTemperature(), affinities[1]);
		change=(foliage*weather)/10.0;
			// System.out.println(change);
		if (foliage<0) {
			foliage=0;
		} else if (weather<0) {
			foliage += change*5;
		}

		if (food>10*cover) 
			food=10*cover;
		food += change;

		if (food>foliage&&weather>0) {
			change = (foliage<cover*4) ? cover/10 : 0;
			foliage+=change; food-=change;
		} else if (food<0&&foliage>0) {
			change = food*2;
			foliage+=change; food=Math.abs(food);
		}
	}
	private void spreadFlora(int crowding) {
		int total = Math.max((6-crowding),1)*maxCover;
		double change;
		// food-=cover/10;
		if (cover<total&&food>cover) {
			change=(cover/crowding)*(growthRate/10.0);
			food-=change*2; cover+=change;
		} else if(food<=0&&foliage<=0) {
			change = -food*2+100;
			cover-=change; food+=change;
		}
	}
	public void multiply (List<Integer> adjacencyReg) {
        if(cover>2000&&SimMap.rand.nextInt(10)>8) {
            Region region = SimMap.regionList.get(Namer.getRandomItem(adjacencyReg));
            region.regionFlora.putIfAbsent(name, new Flora(this, region));
        }
	}

    @Override
    public String toString() {
    	return name+
    	// " "+affinities[0]+affinities[1]+affinities[2]+affinities[3]+
    	// " "+regionAffinity+" "+growthRate+
    	// " "+size+
    	// " "+maxCover+
    	" "+cover+" "+food+" "+foliage
    	;
    }
}