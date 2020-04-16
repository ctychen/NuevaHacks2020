import processing.event.KeyEvent;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DrawingSurface extends PApplet {

	private String[] charNames = {"Weirdo", "Hat Guy", "Cool Hair", "Another Hat Guy", "Is this Burroughs?", "Bosdolf Stalters?"}; //names needed
	private String[] desc = {"Spooky yeeter", "A yeeter with a hat", "A fancy yeeter", "Another yeeter with a hat", "Fancy Math/Java teacher", "Combo of Stalin and Hitler, with a bit of Bob Ross"}; //descriptions needed
	private String[] instincts = {
			"Oh no, someone sneezed near you! Did you turn away?",
			"Someone's speaking really moistly, did you avoid it?",
			"Your nose itches! Do you scratch it?",
			"Your eyes feel dry, do you rub them?"
	};
	
	public Map map;
	
	public RiskBar riskBar;
	
	private JFrame frame = new JFrame("Fancy Window");
	private JOptionPane pane;
	private JDialog dialog;
	
	private boolean init = true;
	private boolean saveAttempted = false;
	
	private boolean[] keys = new boolean[300]; // Basically if a key is pressed then the boolean of the index of that
												// keycode is true, when it is released it gets set to false
	public static final int BEGINNING = 0;
	public static final int PLAYING = 1;
	public static final int LOADING = 2;
	public static final int DEAD = 3;
	
	public static int phase = BEGINNING;
	
	public static final int NUMBER_OF_PLAYABLE_CHARACTERS = 6; //How many playable characters are there
	private static final int CHARACTERS_PER_ROW = 5; //How many playable characters should be listed per row
	
	private PImage[] picChar = new PImage[NUMBER_OF_PLAYABLE_CHARACTERS*2];
	
	private PImage[] tipScreens;

	Game game;
	
	float tx = 0, ty = 0;
	char toPress = ' ';
	
	protected static double safeDistance;
	
	Clock fancyClock = Clock.systemDefaultZone();
	long endTime; // should get set when user closes dialog box, and when game end
	
	short selected = -1;
	
	public static String playSound;

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
		map.loadMap(1);
		map.setMap(1);
		System.out.println("Yay map time");
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		if (phase == BEGINNING) {
			drawSelection();
		}
		else if (phase == PLAYING) {
			clear();
			
			if (playSound != null) {
				try {
					File audioFile = new File("sound" + FileIO.fileSep + playSound);
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
				playSound = null;
			}
			
			if (game == null) {
				game = new Game();
			}
			game.updateGame(map, this, tx, ty);
			if(game.player.x >= width*0.5f && game.player.x <= map.maxX()*width*0.05f-width*0.5f)
				tx= -game.player.x+width*0.5f;
			else if(game.player.x < width*0.5f)
				tx = 0;
			else if (game.player.x > map.maxX()*width*0.05f-width*0.5f)
				tx = -(map.maxX()*width*0.05f-width);
			
			if(game.player.y >= height*0.5f && game.player.y <= (map.maxY()-1)*width*0.05f-height*0.5f)
				ty= -game.player.y+height*0.5f;
			else if(game.player.y < height*0.5f)//ahem osman
				ty = 0;
			else if(game.player.y > (map.maxY()-1)*width*0.05f-height*0.5f)
				ty = -((map.maxY()-1)*width*0.05f-height);
			
			//translate(tx, ty);
			map.draw(this, tx, ty, init, game);
			
			if (init) {
				riskBar = new RiskBar(game.player.getRisk(), width*0.01f, 2 * height * 0.03f, width*0.015f, height*0.04f, 10, true);
				game.player.setPosition((int)(map.getPlayerStart().getX()), (int)(map.getPlayerStart().getY()));
				game.setNPCsInfected();
				System.out.println("NPCs infected: ");
				for (Person p : game.plist) {
					System.out.println(p.isInfected);
				}
			}
			for (Person p : game.plist) {
				p.move(game.player.getX(), game.player.getY());
				p.draw(this, tx, ty);
			}
			if (keys[17]) { // DEBUGGING PURPOSES ONLY!!!
				game.player.initRisk+=5;
			}
			for (int i = 0; i < game.vlist.size(); i++) {
				game.vlist.get(i).draw(this, (int)tx, (int)ty, map.maxX(), map.maxY());
				if (game.vlist.get(i).delete) {
					game.vlist.remove(i);
					i--;
				} else if (game.vlist.get(i).collide(game.player, this)) {
					System.out.println("You just got run over!");
					game.player.gotHit(true);
					phase = DEAD;
				}
					
			}
			
			safeDistance = this.width*0.08f+0.5f;
			
			riskBar.set(game.player.getRisk());
			riskBar.draw(this);
			game.player.draw(this, keys, tx, ty);
			//translate(-tx, -ty);
		} else if (phase == DEAD) {
			g.text("HA YOU'RE DEAD!!!! JOKES ON YOU!!!", width/2, height/2);
			displayStats();
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
			fill(0);
			textSize(width*(float)Math.random()*0.03f+0.05f*width);
			translate(width/6 - 100, height/6 - 90);
			fill((int)(100 + (Math.random()*155)), (int)(100 + (Math.random()*155)), (int)(100 + (Math.random()*155)));
			text("Social Distancing...", width*0.2f+(int)(Math.random()*10), height/2 + (int)(Math.random()*10));
			textSize(width*(float)Math.random()*0.02f+0.02f*width);
			text("...The Game!", width*0.2f+(int)(Math.random()*10), height/2 + height/8 + (int)(Math.random()*10));
			text("Press I for Instructions!", width*0.2f+(int)(Math.random()*10), height/2 + 2*height/8 + (int)(Math.random()*10));
			text("Press X for About and Help", width*0.2f+(int)(Math.random()*10), height/2 + 3*height/8 + (int)(Math.random()*10));
		} else {
			textAlign(LEFT);
			textSize(width*0.02f);
			text("Character Chosen: " + charNames[selected] + "\nDescription: " + desc[selected], width*0.01f, height*0.04f);
			textAlign(CENTER);
			text("Press SPACE to start\nOr press BACKSPACE to go back", width*0.5f, height*0.9f);
			image(picChar[2*selected+zeroOrOne], width*0.2f, height*0.2f, width*0.4f, height*0.65f);
		}
		
	}
	
	public void displayInstructions() {
		Instructions instr = new Instructions();
		PApplet.runSketch(new String[] { "" }, instr);
		PSurfaceAWT surf = (PSurfaceAWT) instr.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(instr.DRAWING_WIDTH, instr.DRAWING_HEIGHT);
		window.setMinimumSize(new Dimension(100, 100));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(true);
		window.setLocation(screenSize.width / 2 - window.getSize().width / 2,
				screenSize.height / 2 - window.getSize().height / 2);

		window.setVisible(true);
	}
	
	public void displayAbout() {
		About a = new About();
		PApplet.runSketch(new String[] { "" }, a);
		PSurfaceAWT surf = (PSurfaceAWT) a.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		window.setSize(a.DRAWING_WIDTH, a.DRAWING_HEIGHT);
		window.setMinimumSize(new Dimension(100, 100));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setResizable(true);
		window.setLocation(screenSize.width / 2 - window.getSize().width / 2,
				screenSize.height / 2 - window.getSize().height / 2);
		window.setVisible(true);
	}
	
	public void displayStats() {
		
		pane = new JOptionPane(
				(game.player.getHit() ? "Oh no, you got hit by a car, it's over!" : 
					"Unfortunately, you got infected, and now symptoms are kicking in! You have to stop.") + 
				"\n Here are your stats:" +
				"\n Your total points: " + game.numPoints + 
				"\n You infected " + game.numInfected + " other people." + 
				"\n This is why socially distancing is important!"
				);
		dialog = pane.createDialog(frame, "Game Over");
		dialog.setLocation((int)(width/4),(int)(height/2));
		pane.setLocation((int)(width/4),(int)(height/2));
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	
	public void instinctKeySave() {
		String instinct = instincts[(int)(Math.random() * instincts.length)];
		pane = new JOptionPane(instinct + "\nPress the key " + toPress + " as fast as possible to see if you were able to make the right choice...");
		long startTime = System.currentTimeMillis();
		long timePassed;
		
		// Random letter key for you to press
		Random r = new Random();
		toPress = (char)(r.nextInt(26) + 'a');
		
		dialog = pane.createDialog(frame, "Quick! Make a Save!");
		dialog.setLocation((int)(Math.random()*width),(int)(Math.random()*height));
		dialog.setVisible(true);
		
		if (saveAttempted) {
			timePassed = endTime - startTime;
			if (timePassed <= (80 + (int)(Math.random() * 200))) { // that's our time limit, may wanna change it
				JOptionPane yeet = new JOptionPane("You were able to make the right decision, you're fine!");
				JDialog spookyDialog = yeet.createDialog(frame, "");
				spookyDialog.setLocation((int)(Math.random()*width),(int)(Math.random()*height));
				spookyDialog.setVisible(true);
			}	else {
				
			}
		}
		
	}
	
	public void instinctClickSave() {
		String instinct = instincts[(int)(Math.random() * instincts.length)];
		pane = new JOptionPane(instinct + "\nClose this dialog as fast as possible to see if you were able to make the right choice...");
		dialog = pane.createDialog(frame, "Quick! Make a Save!");
		dialog.setLocation((int)(Math.random()*width),(int)(Math.random()*height));
		dialog.setVisible(true);
//		Instant startTime = Instant.now();
//		Instant endTime = startTime; // placeholder
		long startTime = System.currentTimeMillis();
		
		dialog.addWindowListener(new WindowAdapter() 
		{
//		  Instant closed; 
		  public void windowClosed(WindowEvent e)
		  {
//			closed = Instant.now(); // stops counting your time
			endTime = System.currentTimeMillis();
		  }
		  
		  public long getEnd() {
			  return endTime;
		  }
		  
//		  public Instant getEnd() {
//			  return closed;
//		  }
		});

		long timePassed = (endTime-startTime);
		if (timePassed <= (80 + (int)(Math.random() * 200))) { // that's our time limit, may wanna change it
			JOptionPane yeet = new JOptionPane("You were able to make the right decision, you're fine!");
			JDialog spookyDialog = yeet.createDialog(frame, "");
			spookyDialog.setLocation((int)(Math.random()*width),(int)(Math.random()*height));
			spookyDialog.setVisible(true);
		} else {
			
		}
		
		// Duration timeElapsed = Duration.between(startTime, endTime); 
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
		if (phase == BEGINNING) {
			if (keyCode == 73) { // I
				displayInstructions();
			}
			if (keyCode == 88) { // X
				displayAbout();
			}
			if (selected != -1) {
				if (keyCode == 32) { // Draws the loading screens with spicy spicy tips
					//phase = PLAYING;
					phase = LOADING;
					showTipScreen();
//					game = new Game();
//					game.setPlayer(selected);
//					game.player.setImageIcons(this);
					
				} else if (keyCode == 8) {
					selected = -1;
				}
			}
			
		}
		else if (phase == LOADING) {
			if (keyCode == 32) {
				phase = PLAYING;
				game = new Game();
				game.setPlayer(selected);
				game.player.setImageIcons(this);
				
			}
		}
		
		else if (phase == PLAYING) {
			init = false;
			// this is for the instinct key press save
			if (keyCode == java.awt.event.KeyEvent.getExtendedKeyCodeForChar(toPress)) {
				saveAttempted = true;
				endTime = System.currentTimeMillis();
			}
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
