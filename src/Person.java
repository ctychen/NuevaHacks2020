import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Person {

	protected boolean isInfected;
	protected int risk = 0;
	protected int initRisk = 0; // max risk should be 100 or something 
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
	
	private boolean imageIconsSet = false;

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
		
		if (!imageIconsSet) {
			frontStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "front.png");
			leftStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "left.png");
			rightStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "right.png");
			backStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "back.png");
			
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
			backStepIcons = new PImage[] {
					p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep1.png"),
					p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep2.png")
			};
			// default icon is front-still
			currentIcon = frontStill;
			System.out.print("loaded images");
			
			if (frontStill != null) frontStill.resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (leftStill != null) leftStill.resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (rightStill != null) rightStill.resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (backStill != null) backStill.resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			
			if (frontStepIcons[0] != null) frontStepIcons[0].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (frontStepIcons[1] != null) frontStepIcons[1].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			
			if (leftStepIcons[0] != null) leftStepIcons[0].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (leftStepIcons[1] != null) leftStepIcons[1].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			
			if (rightStepIcons[0] != null) rightStepIcons[0].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (rightStepIcons[1] != null) rightStepIcons[1].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			
			if (backStepIcons[0] != null) backStepIcons[0].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			if (backStepIcons[1] != null) backStepIcons[1].resize((int)(p.width*0.05f+0.5f), (int)(p.width*0.06f+0.5f));
			
			imageIconsSet = true;
		}
	}
		
	public void draw(PApplet p, float tx, float ty) {
		p.pushStyle();
		if (!imageIconsSet)
			setImageIcons(p);
		if (currentIcon != null)
			p.image(currentIcon, x+tx, y+ty);
		else
			p.rect(x+tx, y+ty, p.width*0.05f, p.width*0.06f);
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
		currentIcon = backStepIcons[(i/4)%backStepIcons.length];
		y-=p.width*0.001*speed;

	}

	public void moveLeftIcon(PApplet p, int i) {;

		currentMovingDirection = DrawingSurface.Direction.LEFT;
		currentIcon = leftStepIcons[(i/4)%leftStepIcons.length];
		x-=p.width*0.001*speed;
		
	}

	public void moveRightIcon(PApplet p, int i) {

		currentMovingDirection = DrawingSurface.Direction.RIGHT;
		currentIcon = rightStepIcons[(i/4)%rightStepIcons.length];
		x+=p.width*0.001*speed;
		
	}

	public void moveDownIcon(PApplet p, int i) {

		currentMovingDirection = DrawingSurface.Direction.DOWN;
		currentIcon = frontStepIcons[(i/4)%frontStepIcons.length];
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
	
	public double getDistanceFrom(Person other) {
		int x2 = other.getX();
		int y2 = other.getY();
		return Math.sqrt((this.x - x2) * (this.x - x2) + (this.y - y2) * (this.y - y2));
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
	
	public void setInfected(boolean infected) {
		this.isInfected = infected;
	}

}
