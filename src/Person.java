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
	protected int normalSpeed;
	protected int maxSpeed;
	protected Pet pet;

	public Person() {

	}

	public Person(int id) {
		// all the image filenames are like p1front or p3left, so ID for choosing the
		// image
		this.personID = id + 1;
		switch(personID) {
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
		case 5: // boomer, gray hair
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			// ... //
		case 6: // red haired hat weirdo
			normalSpeed = 5; // whatever these should be
			maxSpeed = 7;
			initRisk = 10;
			
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
		
		frontStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "front.png");
		leftStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "left.png");
		rightStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "right.png");
		backStill = p.loadImage("people" + FileIO.fileSep + "p" + personID + "back.png");
		
		frontStepIcons = new PImage[] { 
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "frontstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "frontstep2.png") 
			};
		leftStepIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "left.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "leftstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "leftstep2.png")
		};
		rightStepIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "right.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "rightstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + personID + "rightstep2.png")
		};
//		backIcons = new PImage[] {
//				p.loadImage("people" + FileIO.fileSep + "p" + personID + "back.png"),
//				p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep1.png"),
//				p.loadImage("people" + FileIO.fileSep + "p" + personID + "backstep2.png")
//		};
		// default icon is front-still
		this.currentIcon = frontStill; 
		p.frameRate(5);
	}
	
	public void draw(PApplet p) {
		p.pushStyle();
		System.out.println("Drawing at " + x + ", " + y);
		p.image(currentIcon, x, y);
		p.popStyle();
	}
	
	public void spawn(ArrayList<String> map, PApplet g) {
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).length(); j++) {
				if (map.get(i).charAt(j) == 'x') {
					setPosition((int)(g.width*0.05f*(j)), (int)(g.width*0.05f*(i-1.5f)));
					System.out.println("Placing at spawnpoint " + x + ", " + y);
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
	
	public void standStillIcon(PApplet p, int i) {
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
//		y-=normalSpeed;
//		if (i % 20 == 0) {
//			currentIcon = backIcons[1];
//		} else if (i % 10 == 0) {
//			currentIcon = backIcons[2];
//		}
		
		p.image(backStepIcons[i%backStepIcons.length], x, y);
		y-=normalSpeed;

	}

	public void moveLeftIcon(PApplet p, int i) {
//		x-=normalSpeed;
//		if (i % 20 == 0) {
//			currentIcon = leftStepIcons[1];
//		} else if (i % 10 == 0) {
//			currentIcon = leftStepIcons[2];
//		}
//		p.redraw();

		p.image(leftStepIcons[i%leftStepIcons.length], x, y);
		x-=normalSpeed;
		
	}

	public void moveRightIcon(PApplet p, int i) {
//		x+=normalSpeed;
//		if (i % 20 == 0) {
//			currentIcon = rightStepIcons[1];
//		} else if (i % 10 == 0) {
//			currentIcon = rightStepIcons[2];
//		}
//		p.redraw();


		p.image(rightStepIcons[i%rightStepIcons.length], x, y);
		x+=normalSpeed;
		
	}

	public void moveDownIcon(PApplet p, int i) {
//		y+=normalSpeed;
//		if (i % 20 == 0) {
//			currentIcon = frontStepIcons[1];
//		} else if (i % 10 == 0) {
//			currentIcon = frontStepIcons[2];
//		}
//		p.redraw();


		p.image(frontStepIcons[i%frontStepIcons.length], x, y);
		y+=normalSpeed;
		
	}

}
