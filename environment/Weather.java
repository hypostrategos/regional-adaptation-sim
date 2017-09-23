package environment;
import java.util.*;
public class Weather {
	private double temperatureInit;
	private double precipitationInit;
	private double windInit;
	private double temperature;
	private double precipitation;
	private double wind;


	public void setTemperature(Random rand) {
		temperatureInit = 10*rand.nextDouble();
	}
	public double getTemperature() {
		return temperature;
	}
	public void setPrecipitation(Random rand) {
		precipitationInit = 10*rand.nextDouble();
	}
	public double getPrecipitation() {
		return precipitation;
	}
	public void setWind(Random rand) {
		windInit = 10*rand.nextDouble();
	}
	public double getWind() {
		return wind;
	}

	public void updateWeather(Random rand, double phase) {
		temperature = temperatureInit+10*Math.sin(phase)*rand.nextDouble();
		precipitation = precipitationInit+10*Math.sin(phase)*rand.nextDouble();
		wind = windInit+10*Math.sin(phase)*rand.nextDouble();

	}

}
