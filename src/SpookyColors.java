import java.awt.Color;

import processing.core.PApplet;

public class SpookyColors {
	public static final boolean VERTICAL = true;
	public static final boolean HORIZONTAL = false;
	float x1, y1, x2, y2;
	int c = 0;
	int[] indexes = new int[200];
	Color[] colors = new Color[200];
	boolean orientation;
	
	public SpookyColors(float x1, float y1, float x2, float y2, boolean orientation) {
		for (int k = 0; k < colors.length; k++) {
			colors[k] = new Color(0, 0, 0);
			indexes[k] = k;
		}
		dims(x1, y1, x2, y2, orientation);
	}
	public void draw(PApplet p) {
		float width = x2-x1;
		float height = y2-y1;
		float s = (float)width/colors.length;
		float vs = (float)height/colors.length;
		p.fill(255, 255, 255);
		p.noStroke();
		p.rect(x1, y1, x2, y2);
		for (int j = 0; j < colors.length; j++) {
			changeColors(j, p);
			if (orientation)
				p.rect(x1, y1 + j*vs, width, y1 + vs);
			else
				p.rect(x1 + j*s, y1, x1 + s, height);
			//line(width/2, height/2, width/2 + (int)(1000*Math.cos(j* (colors.length/(2*3.14)))), height/2 + (int)(1000*Math.sin(j* (colors.length/(2*3.14)))));
			
		}
	}

	
	public void dims(float x1, float y1, float x2, float y2, boolean orientation) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.orientation = orientation;
	}
	
	
	private void changeColors(int index, PApplet p) {
		
		int i = indexes[index];
		int l = 4;
		int r = colors[index].getRed();
		int g = colors[index].getGreen();
		int b = colors[index].getBlue();
		if ( (double)((int)(System.currentTimeMillis()/5)) % 1 == 0) {
			
			//Starts with green
			if (i==0) {
				r = 100;
				g = 255;
				b = 100;
			}
			//Adds red to make yellow
			else if (i < 155/l) {
				r+=l;
				g=255;
				b=100;
			}
			//Removes green to make red
			else if (i < 2*155/l) {
				r=255;
				g-=l;
				b=100;
			}
			//Adds blue to make purple
			else if (i < 3*155/l) {
				r=255;
				g=100;
				b+=l;
			}	
			//Removes the red to make blue
			else if (i < 4*155/l) {
				r-=l;
				g=100;
				b=255;
			}
			//Adds green to make turquoise
			else if (i < 5*155/l) {
				r=100;
				g+=l;
				b=255;
			}
			//Removes blue to get back to green
			else if (i < 6*155/l) {
				r=100;
				g=255;
				b-=l;
				//if(index == 0 ) System.out.println(r + " " + g + " " + b);
			}
			else if (i >= 6*155/l) indexes[index] = -1;
			
			//Makes all colors lighter
			if (r < 100) r = 100;
			if (g < 100) g = 100;
			if (b < 100) b = 100;
			
			//Makes sure no value gets above 255
			if (r > 255) r = 255;
			if (g > 255) g = 255;
			if (b > 255) b = 255;
			
			//Makes sure no value is negative
			r = Math.abs(r);
			g = Math.abs(g);
			b = Math.abs(b);
			indexes[index]++;
		}
		//System.out.println(r + " " + g + " " + b);
		p.fill(r, g, b);
		p.stroke(r, g, b);
		colors[index] = new Color(r, g, b);
	}
	
}