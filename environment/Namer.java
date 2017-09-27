package environment;
import java.util.*;

public class Namer {
	public static String genName() {
		int length = SimMap.rand.nextInt(8)+2;
		StringBuilder sb = new StringBuilder(length+1);
		String vowels = "aeiou";

		sb.append((char)(SimMap.rand.nextInt('Z' - 'A') + 'A'));
		sb.append(vowels.charAt(SimMap.rand.nextInt(vowels.length())));
		for (int i = 0; i<length; i++) {
			sb.append((char)(SimMap.rand.nextInt('z' - 'a') + 'a'));
		}
		return sb.toString();
	}
    public static void timer(Runnable func, String str) {
        long startTime = System.currentTimeMillis();
        func.run();
        long endTime = System.currentTimeMillis();
        System.out.println(str+" took " + (endTime - startTime) + " milliseconds");
    }
	static <T> T getRandomItem(List<T> list) {
    	return list.get(SimMap.rand.nextInt(list.size()));
	}
}