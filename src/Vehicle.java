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
	
	public static boolean loadedCars = false;
	
	int id;
	
	public static void loadCars(PApplet g) {
		for (int i = 0; i < carNumbers; i++) {
			frontIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "front.png");
			backIcons[i] = g.loadImage("cars" + FileIO.fileSep + "v" + (i+1) + "back.png");
			frontIcons[i].resize((int)(g.width*0.06f+0.5f), (int)(g.width*0.07f+0.5f));
			backIcons[i].resize((int)(g.width*0.06f+0.5f), (int)(g.width*0.07f+0.5f));
		}
	}
	
	//If vertical is false, then the car will be moving horizontally
	//True direction goes up, false direction goes down
	public Vehicle(boolean vertical, boolean direction, int x, int y) {
		this.vertical = vertical;
		speed = (int)(Math.random()*10);
		id = (int)(Math.random()*carNumbers);
	}
	
	public void draw(PApplet g, int tx, int ty) {
		if (!loadedCars)
			loadCars(g);
		if (vertical) {
			if (direction) {
				y -= speed;
				g.image(backIcons[id], x+tx, y+ty);
			} else {
				y += speed;
				g.image(frontIcons[id], x+tx, y+ty);
			}
		}
	}
	
}
