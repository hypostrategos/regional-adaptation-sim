import environment.SimMap;
import environment.Namer;

public class MapGen {
    public static void main(String[] args) {
    	Namer.populateNames();
        SimMap map = SimMap.getInstance();

    	Graphics.start();
	}
}