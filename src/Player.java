import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	
	int id; // 0->weirdo, 1->hat guy, 2->cool hair
	
	PImage[] playerIcon = new PImage[12]; 
	
	public Player(int id) {
		//Yeet
	}
	
	public void loadImages(PApplet g) {
		playerIcon[0] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "front.png");
		playerIcon[1] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "frontstep1.png");
		playerIcon[2] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "frontstep2.png");
		playerIcon[3] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "right.png");
		playerIcon[4] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "rightstep1.png");
		playerIcon[5] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "rightstep2.png");
		playerIcon[6] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "left.png");
		playerIcon[7] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "leftstep1.png");
		playerIcon[8] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "leftstep2.png");
		playerIcon[9] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "back.png");
		playerIcon[10] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "backstep1.png");
		playerIcon[11] = g.loadImage("players" + FileIO.fileSep + "p" + (id+1) + "backstep2.png");
	}
	
	public void draw(PApplet g) {
		
	}
	
}
