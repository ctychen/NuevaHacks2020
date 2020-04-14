import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Pet {

	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected PImage icon;
	protected int speed;
	protected int maxSpeed;
	protected Type type;
	
	protected enum Type {
		CAT(5, 8), DOG(7, 10), TURTLE(2, 3), NINETAILFOX(4, 10); // Change this lol
		
		int speed, maxSpeed;
		PImage[] icons; // add image icons per pet
		
		private Type(int speed, int maxSpeed) {
			this.speed = speed;
			this.maxSpeed = maxSpeed;
		}
		
		public int getSpeed() {
			return speed;
		}
		
		public int getMaxSpeed() {
			return maxSpeed;
		}
		
//		public PImage getIcon() {
//			return icons[(int)(Math.random() * icons.length - 1)];
//		}
	}
	
	public Pet(Type type) {
		this.type = type;
		this.speed = type.getSpeed();
		this.maxSpeed = type.getMaxSpeed();
	}
	
	
}
