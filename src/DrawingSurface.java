import processing.event.KeyEvent;
import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	private boolean[] keys = new boolean[300]; // Basically if a key is pressed then the boolean of the index of that keycode is true, when it is released it gets set to false
	
	public DrawingSurface() {

	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {

	}

	public void mousePressed() {

	}

	public void mouseDragged() {

	}

	public void keyPressed() {
		if (keyCode == 38) { // up arrow

		} else if (keyCode == 37) { // left arrow

		} else if (keyCode == 40) { // down arrow

		} else if (keyCode == 39) { // right arrow

		}
		
		if (keyCode == 87)  //This little chain of if-else statements is so that you can use either arrow keys or WASD
			keyCode = this.UP;
		else if (keyCode == 65)
			keyCode = this.LEFT;
		else if (keyCode == 83)
			keyCode = this.DOWN;
		else if (keyCode == 68)
			keyCode = this.RIGHT;
		
		keys[keyCode] = true;
		
	}

	public void keyTyped(KeyEvent k) {

	}
	
	public void keyReleased() {
		
		if (keyCode == 87)  //This little chain of if-else statements is so that you can use either arrow keys or WASD
			keyCode = this.UP;
		else if (keyCode == 65)
			keyCode = this.LEFT;
		else if (keyCode == 83)
			keyCode = this.DOWN;
		else if (keyCode == 68)
			keyCode = this.RIGHT;
		
		keys[keyCode] = false;
	}

}
