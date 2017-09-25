import environment.SimMap;
import java.awt.event.*;
import javax.swing.*;

public class Graphics {
	public static void start() {
    	JFrame jf = new JFrame("key event");
	    jf.setSize(50,50);
	    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    jf.addKeyListener(new keyEvent());
	    jf.setVisible(true);
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
			case 85 : SimMap.counter+=0.1 ; map.mapUpdate(); // u key
			break;
			case 68 : { map.mapDisplay(); System.out.println(SimMap.counter); }// d key 
			break;
			case 88 : map.mapIncreaseSpecies(1);
			break;
			case 90: map.mapIncreaseSpecies(2);
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