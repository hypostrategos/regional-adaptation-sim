import environment.SimMap;
import java.awt.event.*;
import javax.swing.*;

public class Graphics {
	public static void start() {
    	JFrame jf = new JFrame("key event");
	    jf.setSize(400,400);
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
			case 85 : SimMap.counter+=0.1 ; map.mapUpdate(); // u key
			break;
			case 68 : { map.mapDisplay(); System.out.println(SimMap.counter); }// d key 
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