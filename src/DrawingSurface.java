import processing.event.KeyEvent;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.IOException;
import java.time.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class DrawingSurface extends PApplet {

	private String[] charNames = {"Weirdo", "Hat Guy", "Cool Hair"};
	private String[] desc = {"Spooky yeeter", "A yeeter with a hat", "A fancy yeeter"};
	
	private PImage[] picChar = new PImage[6];
	
	private boolean[] keys = new boolean[300]; // Basically if a key is pressed then the boolean of the index of that
												// keycode is true, when it is released it gets set to false
	public static final int BEGINING = 0;
	public static final int PLAYING = 1;
	
	public Player player;
	
	public static int phase = BEGINING;
	
	public static final int NUMBER_OF_PLAYABLE_CHARACTERS = 3;
	
	Clock fancyClock = Clock.systemDefaultZone();
	
	short selected = -1;
	
	private String playerIconPath; // i dont think its too good of an idea to store this as a string, especially in this class
	
	
	

	public static enum Direction { // wow, an enum? boi this aint c#
		UP(0), DOWN(1), LEFT(2), RIGHT(3);

		int dirID;

		Direction(int id) {
			dirID = id;
		}

		public int getDir() {
			return dirID;
		}
	}

	private Direction currentMovingDirection = Direction.UP;

	public DrawingSurface() {
		
		
	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
		picChar[0] = loadImage("players" + FileIO.fileSep + "p1frontstep1.png");
		picChar[1] = loadImage("players" + FileIO.fileSep + "p1frontstep2.png");
		picChar[2] = loadImage("players" + FileIO.fileSep + "p2front.png");
		picChar[3] = loadImage("players" + FileIO.fileSep + "p2front.png");
		picChar[4] = loadImage("players" + FileIO.fileSep + "p3frontstep1.png");
		picChar[5] = loadImage("players" + FileIO.fileSep + "p3frontstep2.png");
		try {
			File audioFile = new File("sound" + FileIO.fileSep + "run.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			 
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.start();
			//audioClip.close();
			//audioStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		if (phase == BEGINING)
			drawSelection();
		else if (phase == PLAYING) {
			//yeet
		}
	}
	
	public void drawSelection() {
		clear();
		int zeroOrOne = (int)((fancyClock.millis()/200)%2);
		background((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		if (selected == -1) {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Choose your character", width*0.01f, height*0.04f);
			for (int i = 0; i < 3; i++)
				image(picChar[2*i+zeroOrOne], width*0.2f*i, 1*height*0.25f, width*0.2f, width*0.25f);
		} else {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Character Chosen: " + charNames[selected] + "\nDescription: " + desc[selected], width*0.01f, height*0.04f);
			textAlign(CENTER);
			text("Press SPACE to start\nOr press BACKSPACE to go back", width*0.5f, height*0.9f);
			image(picChar[2*selected+zeroOrOne], width*0.2f, height*0.2f, width*0.4f, height*0.65f);
		}
		
	}

	public void choosePlayer(int id) { // shouldnt this be in the player class? oh wait, i just added it there
		switch (id) {
		case 1:
			this.playerIconPath = "players" + FileIO.fileSep + "p1front.png";
		case 2:
			this.playerIconPath = "players" + FileIO.fileSep + "p2front.png";
		case 3:
			this.playerIconPath = "players" + FileIO.fileSep + "p3front.png";
		}

	}

	public void standStill() {
		if (currentMovingDirection == Direction.UP) {
			playerIconPath = "";
		} else if (currentMovingDirection == Direction.LEFT) {
			playerIconPath = "";
		} else if (currentMovingDirection == Direction.RIGHT) {
			playerIconPath = "";
		} else if (currentMovingDirection == Direction.DOWN) {
			playerIconPath = "";
		}
	}
	
	public void mousePressed() { //Yeet, I like them triangles
		if (phase == BEGINING) {
			if (mouseButton == LEFT) {
				for (short i = 0; i < NUMBER_OF_PLAYABLE_CHARACTERS; i++) {
					if (mouseX > width*0.2f*i && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.25f && mouseY < height*0.25f+width*0.2f) {
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					} else if (mouseX > width*0.2f*(i-5) && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.6f && mouseY < height*0.6f+width*0.2f) {
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					}
				}
			}
		}
	}

	public void mouseDragged() {

	}

	// List of keycodes here:
	// https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list/31637206
	// Useful keycodes: 16-Shift, 17-Ctrl, 18-Alt, 20-Caps, 27-Esc, 32-Space,
	// 33-PageUp, 34-PageDown, 35-End, 36-Home, 48-57 is numbers 0-9, 65-90 is A-Z,
	// 96-105 is numpad 0-9, 112-123 is F1-F12, 127-Del
	public void keyPressed() {
		if (phase == BEGINING && selected != -1) {
			if (keyCode == 32) {
				phase = PLAYING;
				player = new Player(selected);
				//player.setLoc((int)(width*0.2), (int)(height*0.2));
				//player.spawn(map.getCurrentMap(), this);
			} else if (keyCode == 8) {
				selected = -1;
			}
		} else if (phase == PLAYING) {
			if (keyCode == 38) { // up arrow
	
			} else if (keyCode == 37) { // left arrow
	
			} else if (keyCode == 40) { // down arrow
	
			} else if (keyCode == 39) { // right arrow
	
			}
	
			if (keyCode == 87) // This little chain of if-else statements is so that you can use either arrow
								// keys or WASD
				keyCode = this.UP;
			else if (keyCode == 65)
				keyCode = this.LEFT;
			else if (keyCode == 83)
				keyCode = this.DOWN;
			else if (keyCode == 68)
				keyCode = this.RIGHT;
	
			keys[keyCode] = true;
		}

	}

	public void keyTyped(KeyEvent k) {

	}

	public void keyReleased() {

		if (keyCode == 87) // This little chain of if-else statements is so that you can use either arrow
							// keys or WASD
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
