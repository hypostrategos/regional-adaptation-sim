import environment.SimMap;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import environment.SimMap;
import environment.Region;

public class Display {
	public static final int xWidth = 1200;
	public static final int yWidth = 800;

	public static void start() {
    	JFrame jf = new JFrame("Regional Adaptation Sim");
	    jf.setSize(xWidth+50,yWidth+50);
	    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setUpDisplay();
	    jf.add(new Circles());
	    jf.addKeyListener(new keyEvent());
	    jf.setVisible(true);
	}
	public static void setUpDisplay() {
		SimMap.regionList.stream()
		.forEach( region -> new Circle(region) );
	}
}

class Circles extends JPanel {
    private static ArrayList<Circle> circles = new ArrayList<Circle>();

    public static void add(Circle circle) {
        circles.add(circle);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Circle circle: circles) {
            Ellipse2D circle2D = new Ellipse2D.Double();
            circle2D.setFrameFromCenter(
                circle.getCenterX(),
                circle.getCenterY(),
                circle.getCenterX() + circle.getRadius(),
                circle.getCenterY() + circle.getRadius());

            circle.getAdjacencies().stream()
            .filter(target -> circle.getId()>target)
            .forEach(target -> g.drawLine(
            	circle.getCenterX(), circle.getCenterY(), 
            	circles.get(target).getCenterX(), circles.get(target).getCenterY())
            );

            g2.draw(circle2D);
        }
    }
}

class Circle {
	private int id, centerX, centerY, radius;
    private List<Integer> adjacencies;
	// private Region myRegion;

	Circle( Region region ) {
		id = region.getRegionId();
		// myRegion = region;
	    radius = region.getSize();
	    centerX = 50+(int)((id%SimMap.mapWidth)*(Display.xWidth/SimMap.mapWidth));
	    centerY = 50+(int)((id/SimMap.mapWidth)*(Display.yWidth/(SimMap.numOfRegions/SimMap.mapWidth)));
	    adjacencies = region.getAdjacencyReg();
	    Circles.add(this);
	}
	public int getCenterX() {
		return centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public int getRadius() {
		return radius;
	}
	public int getId() {
		return id;
	}
	public List<Integer> getAdjacencies() {
		return adjacencies;
	}
}

class keyEvent implements KeyListener {
	SimMap map;
	public keyEvent () {
		map = SimMap.getInstance();
	}

	public void keyPressed(KeyEvent k) {
		// System.out.println(k.getKeyCode());
		switch(k.getKeyCode()) {
			case 84 : { map.mapBioCount(); System.out.println("animals: "+SimMap.totalFauna+" plants: "+SimMap.totalFlora); } //t key
			break;
			case 85 : map.mapUpdate(); // u key
			break;
			case 73 : map.mapUpdate(100); //i key
			break;
			case 68 : { map.mapDisplay(); System.out.println(SimMap.counter); }// d key 
			break;
			case 88 : map.mapIncreaseSpecies(1); //z key
			break;
			case 90: map.mapIncreaseSpecies(2); //x key
			break;
			case 27 : System.exit(0); //esc key
			break;
		}
	}
	public void keyReleased(KeyEvent k) {
	}
	public void keyTyped(KeyEvent k) {
	}
}