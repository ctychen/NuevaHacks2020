import processing.event.KeyEvent;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class DrawingSurface extends PApplet {

	private String[] charNames = {"Weirdo", "Hat Guy", "Cool Hair", "Another Hat Guy", "Is this Burroughs?", "Bosdolf Stalters?"}; //names needed
	private String[] desc = {"Spooky yeeter", "A yeeter with a hat", "A fancy yeeter", "Another yeeter with a hat", "Fancy Math/Java teacher", "Combo of Stalin and Hitler, with a bit of Bob Ross"}; //descriptions needed
	
	public Map map;
	
	public RiskBar riskBar;
	
	private boolean init = true;
	
	private boolean[] keys = new boolean[300]; // Basically if a key is pressed then the boolean of the index of that
												// keycode is true, when it is released it gets set to false
	public static final int BEGINNING = 0;
	public static final int PLAYING = 1;
	
	public Player player;
	public static ArrayList<Person> npc = new ArrayList<Person>();
	
	public static int phase = BEGINNING;
	
	public static final int NUMBER_OF_PLAYABLE_CHARACTERS = 6; //How many playable characters are there
	private static final int CHARACTERS_PER_ROW = 5; //How many playable characters should be listed per row
	
	private PImage[] picChar = new PImage[NUMBER_OF_PLAYABLE_CHARACTERS*2];
	
	private PImage[] tipScreens;

	
	float tx = 0, ty = 0;
	
	Clock fancyClock = Clock.systemDefaultZone();
	
	short selected = -1;


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
		for (int i = 0; i < NUMBER_OF_PLAYABLE_CHARACTERS; i++) {
			picChar[i*2] = loadImage("people" + FileIO.fileSep + "p" + (i+1) + "frontstep1.png");
			picChar[i*2+1] = loadImage("people" + FileIO.fileSep + "p" + (i+1) + "frontstep2.png");
		}
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
		map = new Map(this);
		map.loadMap(0);
		map.setMap(0);
		
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		if (phase == BEGINNING)
			drawSelection();
		else if (phase == PLAYING) {
			clear();
			if(player.x >= width*0.5f && player.x <= map.maxX()*width*0.05f-width*0.5f)
				tx= -player.x+width*0.5f;
			else if(player.x < width*0.5f)
				tx = 0;
			else if (player.x > map.maxX()*width*0.05f-width*0.5f)
				tx = -(map.maxX()*width*0.05f-width);
			
			if(player.y >= height*0.5f && player.y <= (map.maxY()-1)*width*0.05f-height*0.5f)
				ty= -player.y+height*0.5f;
			else if(player.y < height*0.5f)//ahem osman
				ty = 0;
			else if(player.y > (map.maxY()-1)*width*0.05f-height*0.5f)
				ty = -((map.maxY()-1)*width*0.05f-height);
			
			translate(tx, ty);
			map.draw(this, tx, ty, init);
			
			if (init) {
				riskBar = new RiskBar(player.getRisk(), 30, 2 * height * 0.03f, 100, 20, 10);
				player.setPosition((int)(map.getPlayerStart().getX()), (int)(map.getPlayerStart().getY()));
			}
			for (Person p : npc) {
				p.move(player.getX(), player.getY());
				p.setImageIcons(this);
				p.draw(this);
			}
			riskBar.draw(this);
			player.draw(this, keys);
			translate(-tx, -ty);
		}
		redraw();
	}
	
	public void showTipScreen() {
		tipScreens = new PImage[] {
				this.loadImage("tips" + FileIO.fileSep + "0.png"),
				this.loadImage("tips" + FileIO.fileSep + "1.png"),
				this.loadImage("tips" + FileIO.fileSep + "2.png"),
				this.loadImage("tips" + FileIO.fileSep + "3.png"),
				this.loadImage("tips" + FileIO.fileSep + "4.png"),
				this.loadImage("tips" + FileIO.fileSep + "5.png"),
				this.loadImage("tips" + FileIO.fileSep + "6.png")
			};
		PImage tip = tipScreens[(int)(Math.random() * tipScreens.length)];
		if (tip != null) tip.resize((int)(this.width), (int)(this.height));
		this.image(tip, 0, 0);
	}
	
	public void drawSelection() {
		clear();
		int zeroOrOne = (int)((fancyClock.millis()/200)%2);
		background((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		if (selected == -1) {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Choose your character", width*0.01f, width*0.02f);
			for (int i = 0; i < NUMBER_OF_PLAYABLE_CHARACTERS; i++)
				image(picChar[2*i+zeroOrOne], width*0.2f*(i%CHARACTERS_PER_ROW), width*0.25f*(0.1f+(int)(i/CHARACTERS_PER_ROW)), width*0.2f, width*0.25f);
				
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
	}

	public void standStill() {

	}
	
	public void mousePressed() { //Yeet, I like them triangles
		if (phase == BEGINNING) {
			if (mouseButton == LEFT) {
				for (short i = 0; i < NUMBER_OF_PLAYABLE_CHARACTERS; i++) {
					if (mouseX > width*0.2f*i && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.25f && mouseY < height*0.25f+width*0.2f) { //Ok i have no idea how or why this works, but somehow it sorta works
						selected = i;
						System.out.println("Selected " + i);
						//phase = 1;
					} else if (mouseX > width*0.2f*(i-5) && mouseX < width*0.2f*i+width*0.2f && mouseY > height*0.6f && mouseY < height*0.6f+width*0.2f) { //Ok i have no idea how or why this works, but somehow it sorta works
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
	// https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list/32037206
	// Useful keycodes: 20-Shift, 17-Ctrl, 18-Alt, 20-Caps, 27-Esc, 32-Space,
	// 33-PageUp, 34-PageDown, 35-End, 36-Home, 48-57 is numbers 0-9, 65-90 is A-Z,
	// 96-105 is numpad 0-9, 112-123 is F1-F12, 127-Del
	public void keyPressed() {
		if (phase == BEGINNING && selected != -1) {
			if (keyCode == 32) {
				phase = PLAYING;
				player = new Player(selected);
				player.setImageIcons(this);
				
			} else if (keyCode == 8) {
				selected = -1;
			}
		} else if (phase == PLAYING) {
			init = false;
			if (keyCode == 87) // This little chain of if-else statements is so that you can use either arrow keys or WASD
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

		if (keyCode == 87) // This little chain of if-else statements is so that you can use either arrow keys or WASD
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
