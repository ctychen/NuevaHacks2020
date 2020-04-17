import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Pet {

	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected PImage frontStill;
	protected PImage leftStill;
	protected PImage rightStill;
	protected PImage backStill;
	protected PImage[] frontStepIcons;
	protected PImage[] leftStepIcons;
	protected PImage[] rightStepIcons;
	protected PImage[] backStepIcons;
	protected PImage currentIcon;
	protected int speed;
	protected int maxSpeed;
	protected Type type;
	protected int typeID;
	protected DrawingSurface.Direction currentMovingDirection;
	private boolean imageIconsSet = false;
	protected int vx, vy;
	boolean leashed = false;
	
	protected enum Type {
		CAT(1, 5, 8), DOG(2, 7, 10), TURTLE(3, 2, 3), NINETAILFOX(4, 4, 10); // Change this lol
		
		int id, speed, maxSpeed;
		
		private Type(int id, int speed, int maxSpeed) {
			this.id = id;
			this.speed = speed;
			this.maxSpeed = maxSpeed;
		}
		
		public int getSpeed() {
			return speed;
		}
		
		public int getMaxSpeed() {
			return maxSpeed;
		}
		
		public int getID() {
			return id;
		}
		
	}
	
	public Pet(Type type) {
		this.type = type;
		this.typeID = type.getID();
		this.speed = type.getSpeed();
		this.maxSpeed = type.getMaxSpeed();
		this.currentMovingDirection = DrawingSurface.Direction.RIGHT; // default
	}
	
	public void setImageIcons(PApplet p) {
		
		frontStill = p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "front.png");
		leftStill = p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "left.png");
		rightStill = p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "right.png");
		backStill = p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "back.png");
		
		frontStepIcons = new PImage[] { 
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "frontstep1.png"),
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "frontstep2.png") 
			};
		leftStepIcons = new PImage[] {
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "leftstep1.png"),
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "leftstep2.png")
		};
		rightStepIcons = new PImage[] {
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "rightstep1.png"),
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "rightstep2.png")
		};
		backStepIcons = new PImage[] {
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "backstep1.png"),
				p.loadImage("pets" + FileIO.fileSep + "a" + typeID + "backstep2.png")
		};
		
		// default icon is right-still
		this.currentIcon = rightStill;
		
		if (frontStill != null) frontStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (leftStill != null) leftStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (rightStill != null) rightStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (backStill != null) backStill.resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		
		if (frontStepIcons[0] != null) frontStepIcons[0].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (frontStepIcons[1] != null) frontStepIcons[1].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		
		if (leftStepIcons[0] != null) leftStepIcons[0].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (leftStepIcons[1] != null) leftStepIcons[1].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		
		if (rightStepIcons[0] != null) rightStepIcons[0].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (rightStepIcons[1] != null) rightStepIcons[1].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		
		if (backStepIcons[0] != null) backStepIcons[0].resize((int)(p.width*0.05f), (int)(p.width*0.06f));
		if (backStepIcons[1] != null) backStepIcons[1].resize((int)(p.width*0.05f), (int)(p.width*0.06));
		
		imageIconsSet = true;
	}
	
//	public void draw(PApplet p) {
//		p.pushStyle();
//		p.image(currentIcon, x, y, p.width*0.05f, p.width*0.06f);
//		p.popStyle();
//	}
	
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
	
	
	
	
}
