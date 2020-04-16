import processing.core.PApplet;
import processing.core.PImage;

public class Instructions extends PApplet{

	protected final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 400;

	private float shiftSpeed;
	
	private PImage how;

	private SpookyColors fancyRainbow = new SpookyColors(0, 0, 0, 0, SpookyColors.HORIZONTAL);
	
	public Instructions() {
		shiftSpeed = 0;
	}
	
	public void setup() {
		how = loadImage("intro.png");
		how.resize(width/2, 2*height/3);
	}
	
	public void draw() {
		pushStyle();
		fancyRainbow.dims(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT, SpookyColors.HORIZONTAL);
		fancyRainbow.draw(this);
		popStyle();
		fill(0);
		image(how, width/4, (int)(Math.random()*2*height/7));
		textAlign(CENTER);
		textSize(30);
		textSize(width*(float)Math.random()*0.02f+0.03f*width);
		//translate(-width*0.1f, -height*0.1f);
		translate(width/2 - 200, height/2 - 190);
		fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		text("Game Instructions", width/4+(int)(Math.random()*10), (int)(Math.random()*height/7));
	}
	
}
