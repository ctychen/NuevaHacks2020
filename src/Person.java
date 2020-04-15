import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Person {

	protected boolean isInfected;
	protected int risk;
	protected int initRisk; // max risk should be 100 or something 
	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected int walkingAnimationPart = 1;
	protected PImage frontStill;
	protected PImage leftStill;
	protected PImage rightStill;
	protected PImage backStill;
	protected PImage[] frontStepIcons;
	protected PImage[] leftStepIcons;
	protected PImage[] rightStepIcons;
	protected PImage[] backStepIcons;
	protected PImage currentIcon;
	protected int personID;
	protected DrawingSurface.Direction currentMovingDirection;
	protected int speed, maxSpeed;
	protected int vx, vy;
	protected Pet pet;

	public Person() {

	}

	public Person(int id) {
		// all the image filenames are like p1front or p3left, so ID for choosing the
		// image
		this.personID = id + 1;
		switch(personID) {
		case 1: // blue haired person
			speed = 7;
			initRisk = 5 + (int)(Math.random() * 5);
			// ... //
		case 2: // blue hat guy
			speed = 10; 
			initRisk = 3 + (int)(Math.random() * 5);
			// ... //
		case 3: // green fancy guy
			speed = 9;
			initRisk = 5 + (int)(Math.random() * 5);
			// ... //
		case 4: // red hat guy
			speed = 10; 
			initRisk = 3 + (int)(Math.random() * 5);
			// ... //
		case 5: // boomer, gray hair
			speed = 5; 
			initRisk = 15 + (int)(Math.random() * 10);
			// ... //
		case 6: // red haired hat weirdo
			speed = 5; 
			initRisk = 10 + (int)(Math.random() * 10);
			
		// ... etc
		}
		currentMovingDirection = DrawingSurface.Direction.UP;
		maxSpeed = speed;
		risk = initRisk;
		this.x = xi;
		this.y = yi;
	}

	public void addPet(Pet.Type type) {
		pet = new Pet(type);
	}

	public void setImageIcons(PApplet p) {
		
		frontStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "front.png");
		leftStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "left.png");
		rightStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "right.png");
		
		frontStepIcons = new PImage[] { 
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "frontstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "frontstep2.png") 
			};
		leftStepIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "leftstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "leftstep2.png")
		};
		rightStepIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "rightstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "rightstep2.png")
		};
//		backStepIcons = new PImage[] {
//				p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep1.png"),
//				p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep2.png")
//		};
		// default icon is front-still
		frontStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		leftStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		rightStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
//		backStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		for (PImage e : frontStepIcons) {
			e.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		}
		for (PImage e : leftStepIcons) {
			e.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		}
		for (PImage e : rightStepIcons) {
			e.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		}
		this.currentIcon = frontStill;
//		for (PImage e : backStepIcons) {
//			e.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
//		}
	}
	
	public void draw(PApplet p) {
		p.pushStyle();
		p.image(currentIcon, x, y);
		p.popStyle();
	}
	
	public void spawn(ArrayList<String> map, PApplet g) {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == 'x') {
					setPosition((int)(g.width*0.05f*(j)), (int)(g.width*0.05f*(i-1.5f)));
				}
			}
		}
	}

	// moving down by default but setting position
	public void setPosition(int x, int y) {
		setPosition(x, y, DrawingSurface.Direction.DOWN);
	}
	
	public void setPosition(int x, int y, DrawingSurface.Direction dir) {
		this.x = x;
		this.y = y;
		this.xi = x;
		this.yi = y;
		this.currentMovingDirection = dir;
	}
	
	public void standStillIcon(PApplet p) {
		if (currentMovingDirection == DrawingSurface.Direction.UP) {
			currentIcon = backStill;
		} else if (currentMovingDirection == DrawingSurface.Direction.LEFT) {
			currentIcon = leftStill;
		} else if (currentMovingDirection == DrawingSurface.Direction.RIGHT) {
			currentIcon = rightStill;
		} else if (currentMovingDirection == DrawingSurface.Direction.DOWN) {
			currentIcon = frontStill;
		}
	}

	public void moveUpIcon(PApplet p, int i) {
		
		currentMovingDirection = DrawingSurface.Direction.UP;
		currentIcon = backStepIcons[i%backStepIcons.length];
		y-=p.width*0.001*speed;

	}

	public void moveLeftIcon(PApplet p, int i) {;

		currentMovingDirection = DrawingSurface.Direction.LEFT;
		currentIcon = leftStepIcons[i%leftStepIcons.length];
		x-=p.width*0.001*speed;
		
	}

	public void moveRightIcon(PApplet p, int i) {

		currentMovingDirection = DrawingSurface.Direction.RIGHT;
		currentIcon = rightStepIcons[i%rightStepIcons.length];
		x+=p.width*0.001*speed;
		
	}

	public void moveDownIcon(PApplet p, int i) {

		currentMovingDirection = DrawingSurface.Direction.DOWN;
		currentIcon = frontStepIcons[i%frontStepIcons.length];
		y+=p.width*0.001*speed;
		
	}
	
	public void draw(PApplet g, boolean[] keys) {
		if (keys[g.UP]) {
		}
		if (keys[g.DOWN]) {
		}
		if (keys[g.LEFT]) {
		}
		if (keys[g.RIGHT]) {
		}
		else if (!keys[g.LEFT] && !keys[g.DOWN] && !keys[g.UP]) {
			standStillIcon(g);
		}
	}
	
	public void move(int px, int py) {
		px = (int)(Math.random()*200-100);
		py = (int)(Math.random()*200-100);
		float temp = (float)Math.sqrt( (px)*(px) + (py)*(py) );
		if (Math.random() < 0.5) {
			vx = (int)(speed*(px)/temp);
		} else {
			vy = (int)(speed*(py)/temp);
		}
		x+=vx;
		y+=vy;	
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getRisk() {
		return this.risk;
	}
	
	public void changeRisk(int amount) {
		this.risk += amount;
	}
	
	public PImage getCurrentIcon() {
		return this.currentIcon;
	}

}
