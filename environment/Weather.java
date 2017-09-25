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
		temperatureInit = SimMap.rand.nextDouble();
	}
	public void setPrecipitation() {
		precipitationInit = SimMap.rand.nextDouble();
	}
	public void setWind() {
		windInit = SimMap.rand.nextDouble();
	}

	public double getTemperature() {
		return temperature;
	}
	public double getPrecipitation() {
		return precipitation;
	}
	public double getWind() {
		return wind;
	}

	public double getTemperatureInit() {
		return temperatureInit;
	}
	public double getPrecipitationInit() {
		return precipitationInit;
	}
	public double getWindInit() {
		return windInit;
	}


	public void updateWeather() {
		temperature = temperatureInit+Math.sin(SimMap.counter)*SimMap.rand.nextDouble();
		precipitation = precipitationInit+Math.sin(SimMap.counter)*SimMap.rand.nextDouble();
		wind = windInit+Math.sin(SimMap.counter)*SimMap.rand.nextDouble();

	}
}
