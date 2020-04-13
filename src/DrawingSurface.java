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
	
    // List of keycodes here: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list/31637206
	// Useful keycodes: 16-Shift, 17-Ctrl, 18-Alt, 20-Caps, 27-Esc, 32-Space, 33-PageUp, 34-PageDown, 35-End, 36-Home, 48-57 is numbers 0-9, 65-90 is A-Z, 96-105 is numpad 0-9, 112-123 is F1-F12, 127-Del  
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
