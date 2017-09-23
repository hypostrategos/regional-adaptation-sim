package environment;
import java.util.*;
public class Weather {
	private double temperatureInit;
	private double precipitationInit;
	private double windInit;
	private double temperature;
	private double precipitation;
	private double wind;


	public void setTemperature() {
		temperatureInit = 10*SimMap.rand.nextDouble();
	}
	public double getTemperature() {
		return temperature;
	}
	public void setPrecipitation() {
		precipitationInit = 10*SimMap.rand.nextDouble();
	}
	public double getPrecipitation() {
		return precipitation;
	}
	public void setWind() {
		windInit = 10*SimMap.rand.nextDouble();
	}
	public double getWind() {
		return wind;
	}

	public void updateWeather() {
		temperature = temperatureInit+10*Math.sin(SimMap.counter)*SimMap.rand.nextDouble();
		precipitation = precipitationInit+10*Math.sin(SimMap.counter)*SimMap.rand.nextDouble();
		wind = windInit+10*Math.sin(SimMap.counter)*SimMap.rand.nextDouble();

	}
}
