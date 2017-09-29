package environment;
import java.util.*;
import java.io.*;

public class Namer {
	private static ArrayList<String> adjectives = new ArrayList<String>();
	private static ArrayList<String> colours = new ArrayList<String>();
	private static ArrayList<String> carnivores = new ArrayList<String>();
	private static ArrayList<String> herbivores = new ArrayList<String>();
	private static ArrayList<String> plants = new ArrayList<String>();


	public static void populateNames() {
		BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader("names.txt"));
            String sCurrentLine = reader.readLine();
            sCurrentLine = reader.readLine();
            while (!sCurrentLine.equals("---COLOURS---")) {
                adjectives.add(sCurrentLine);
            	sCurrentLine = reader.readLine();
            }
            sCurrentLine = reader.readLine();
            while (!sCurrentLine.equals("---CARNIVORES---")) {
                colours.add(sCurrentLine);
            	sCurrentLine = reader.readLine();
            }
            sCurrentLine = reader.readLine();
            while (!sCurrentLine.equals("---HERBIVORES---")) {
                carnivores.add(sCurrentLine);
            	sCurrentLine = reader.readLine();
            }
            sCurrentLine = reader.readLine();
            while (!sCurrentLine.equals("---PLANTS---")) {
                herbivores.add(sCurrentLine);
            	sCurrentLine = reader.readLine();
            }
            while ((sCurrentLine = reader.readLine())!=null) {
                plants.add(sCurrentLine);
            	sCurrentLine = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
		    try {
		        reader.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	}
	public static String genName(int type) {
		StringBuilder sb = new StringBuilder();
		sb.append(adjectives.get(SimMap.rand.nextInt(adjectives.size() ) ) );
		sb.append(colours.get(SimMap.rand.nextInt(colours.size() ) ) );

		switch(type) {
			case 1: sb.append(carnivores.get(SimMap.rand.nextInt(carnivores.size() ) ) );
			break;
			case 2: sb.append(herbivores.get(SimMap.rand.nextInt(herbivores.size() ) ) );
			break;
			case 0: sb.append(plants.get(SimMap.rand.nextInt(plants.size() ) ) );
			break;
		}
		sb.append("s");
		return sb.toString();
	}    
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
	static Region getEmptyItem(List<Integer> list, int flag) {
		Region goal = list.stream()
		.map(x->SimMap.regionList.get(x))
		.filter(x->(flag==0&&x.regionFlora.size()==0)||(flag==1&&x.regionFauna.size()==0))
		.findAny().orElse(null);

		if (goal==null)
			return getRandomItem(list);
		return goal;
	}
	static <T> Region getRandomItem(List<T> list) {
    	return SimMap.regionList.get(SimMap.rand.nextInt(list.size()));
	}
}