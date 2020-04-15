import processing.core.PApplet;

public class About extends PApplet{

	protected final int DRAWING_WIDTH = 800, DRAWING_HEIGHT = 600;

	private float shiftSpeed;

	private SpookyColors fancyRainbow = new SpookyColors(0, 0, 0, 0, SpookyColors.VERTICAL);
	
	public About() {
		shiftSpeed = 0;
	}
	
	public void draw() {
		pushStyle();
		fancyRainbow.dims(0, 0, DRAWING_WIDTH, DRAWING_HEIGHT, SpookyColors.VERTICAL);
		fancyRainbow.draw(this);
		popStyle();
		fill(0);
		textAlign(CENTER);
		textSize(50);
		textSize(width*(float)Math.random()*0.03f+0.05f*width);
		//translate(-width*0.1f, -height*0.1f);
		translate(width/2 - 200, height/2 - 190);
		fill((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
		text("About", width/2+(int)(Math.random()*10), height-(int)(Math.random()*10));
		
	}
	
}
