import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Pet {

	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected PImage icon;
	
	protected enum Type {
		CAT, DOG, TURTLE, NINETAILFOX; // Change this lol
	}
	
	public Pet() {
		
	}
	
	
}
