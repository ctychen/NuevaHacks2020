import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Person{
	
	private int movingUpCounter = 0;
	private int movingLeftCounter = 0;
	private int movingRightCounter = 0;
	private int movingDownCounter = 0;
	
	public Player(int id) {
		//Yeet
		super(id);
	}
	
	public void draw(PApplet g, boolean[] keys, float tx, float ty) {
		super.draw(g, tx, ty);
		
		if (keys[g.UP]) {
			//y-=g.width*0.001*normalSpeed;
			moveUpIcon(g, movingUpCounter);
			movingUpCounter++;
			movingUpCounter%=16;
		}
		if (keys[g.DOWN]) {
			//y+=g.width*0.001*normalSpeed;
			moveDownIcon(g, movingDownCounter);
			movingDownCounter++;
			movingDownCounter%=16;
		}
		if (keys[g.LEFT]) {
			//x-=g.width*0.001*normalSpeed;
			moveLeftIcon(g, movingLeftCounter);
			movingLeftCounter++;
			movingLeftCounter%=16;
		}
		if (keys[g.RIGHT]) {
			//x+=g.width*0.001*normalSpeed;
			moveRightIcon(g, movingRightCounter);
			movingRightCounter++;
			movingRightCounter%=16;
		}
		else if (!keys[g.LEFT] && !keys[g.DOWN] && !keys[g.UP]) {
			standStillIcon(g);
		}
	}
	
}
