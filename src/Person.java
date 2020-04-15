import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Person {

	protected boolean isInfected;
	protected double risk;
	protected double initRisk; // max risk should be 100 or something 
	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected PImage[] frontIcons;
	protected PImage[] leftIcons;
	protected PImage[] rightIcons;
	protected PImage[] backIcons;
	protected PImage currentIcon;
	protected int id;
	protected DrawingSurface.Direction currentMovingDirection;
	protected int normalSpeed;
	protected int maxSpeed;
	protected Pet pet;

	public Person() {

	}

	public Person(int id) {
		// all the image filenames are like p1front or p3left, so ID for choosing the
		// image
		switch(id) {
		case 1: // blue haired person
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		case 2: // blue hat guy
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		case 3: // green fancy guy
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		case 4: // red hat guy
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		case 5: // boomer 1
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		// ... etc
		}
		currentMovingDirection = DrawingSurface.Direction.UP;
		// TODO set init pos
		
		
		this.x = xi;
		this.y = yi;
	}

	public void addPet(Pet.Type type) {
		pet = new Pet(type);
	}

	public void setImageIcons(PApplet p) {
		frontIcons = new PImage[] { 
				p.loadImage("people" + FileIO.fileSep + "p" + id + "front.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "frontstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "frontstep2.png") 
			};
		leftIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "left.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "leftstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "leftstep2.png")
		};
		rightIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "right.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "rightstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "rightstep2.png")
		};
		backIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "back.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "backstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "backstep2.png")
		};
	}

	public void standStillIcon(PApplet p, int i) {
		if (currentMovingDirection == DrawingSurface.Direction.UP) {
			currentIcon = frontIcons[0];
		} else if (currentMovingDirection == DrawingSurface.Direction.LEFT) {
			currentIcon = leftIcons[0];
		} else if (currentMovingDirection == DrawingSurface.Direction.RIGHT) {
			currentIcon = rightIcons[0];
		} else if (currentMovingDirection == DrawingSurface.Direction.DOWN) {
			currentIcon = backIcons[0];
		}
	}

	public void moveUpIcon(PApplet p, int i) {
		y+=normalSpeed;
		if (i % 20 == 0) {
			currentIcon = frontIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = frontIcons[2];
		}
	}

	public void moveLeftIcon(PApplet p, int i) {
		x-=normalSpeed;
		if (i % 20 == 0) {
			currentIcon = leftIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = leftIcons[2];
		}
	}

	public void moveRightIcon(PApplet p, int i) {
		x+=normalSpeed;
		if (i % 20 == 0) {
			currentIcon = rightIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = rightIcons[2];
		}
	}

	public void moveDownIcon(PApplet p, int i) {
		y-=normalSpeed;
		if (i % 20 == 0) {
			currentIcon = backIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = backIcons[2];
		}
	}

}
