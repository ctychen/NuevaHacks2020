import processing.core.PApplet;
import processing.core.PImage;

public class Vehicle {
	int x, y;
	boolean vertical;
	boolean direction;
	int speed;
	public static int carNumbers = 1;
	public static PImage[] frontIcons = new PImage[carNumbers];
	public static PImage[] backIcons = new PImage[carNumbers];
	public static PImage[] rightIcons = new PImage[carNumbers];
	public static PImage[] leftIcons = new PImage[carNumbers];
	public boolean delete = false;
	
	int swerve = 0; // -1 is left, 0 is none, 1 is right
	
	public static boolean loadedCars = false;
	
	int id;
	
	public static void loadCars(PApplet g) {
		for (int i = 0; i < carNumbers; i++) {
			frontIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "front.png");
			backIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "back.png");
			frontIcons[i].resize((int)(g.width*0.07f+0.5f), (int)(g.width*0.09f+0.5f));
			backIcons[i].resize((int)(g.width*0.07f+0.5f), (int)(g.width*0.09f+0.5f));
			
			leftIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "left.png");
			rightIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "right.png");
			leftIcons[i].resize((int)(g.width*0.1f+0.5f), (int)(g.width*0.12f+0.5f));
			rightIcons[i].resize((int)(g.width*0.1f+0.5f), (int)(g.width*0.12f+0.5f));
		}
	}
	
	//If vertical is false, then the car will be moving horizontally
	//True direction goes up, false direction goes down
	public Vehicle(boolean vertical, boolean direction, int x, int y) {
		this.vertical = vertical;
		this.direction = direction;
		speed = (int)(Math.random()*10) + 20;
		id = (int)(Math.random()*carNumbers);
		this.x = x;
		this.y = y;
	}
	
	public void draw(PApplet g, int tx, int ty, int mx, int my) {
		if (!loadedCars)
			loadCars(g);
		if (Math.random() < 0.01f)
			swerve = (int)(Math.random()*3-1);
		else if (Math.random() < 0.3f)
			swerve = 0;
		if (vertical) {
			if (direction) {
				y -= speed;
				g.image(backIcons[id], x+tx, y+ty);
			} else {
				y += speed;
				g.image(frontIcons[id], x+tx, y+ty);
			}
			x+= speed*0.2f*swerve;
		}
		else {
			if (direction) {
				x += speed;
				g.image(rightIcons[id], x+tx, y+ty);
			} else {
				x -= speed;
				g.image(leftIcons[id], x+tx, y+ty);
			}
			y+= speed*0.2f*swerve;
		}
		if (x > 1.5f*mx*g.width*0.05f || x < -0.5f*mx*g.width*0.05f || y > 1.5f*my*g.width*0.05f || y < -0.5f*my*g.width*0.05f)
			delete = true;
	}
	
	public boolean collide(Player p, PApplet g) {
		
		int distance = (int)(Math.sqrt( Math.pow(p.x-(this.x+g.width*0.035f), 2) + Math.pow(p.y-(this.y+g.width*0.06f), 2) ) + 0.5f);
		
		if (distance < g.width*0.04f)
			return true;
		else if (distance < g.width*0.2f && Math.random() < 0.6f)
			swerve = (int)(Math.random()*3-1);
		
		return false;
	}
	
}
