import environment.SimMap;
// import java.util.concurrent.*;

public class MapGen {
    public static void main(String[] args) {
        SimMap map = SimMap.getInstance();
        map.mapUpdate();

    	// final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    	// executorService.scheduleAtFixedRate(map::mapUpdate, 0, 5, TimeUnit.SECONDS);
    	Graphics.start();
	}
}