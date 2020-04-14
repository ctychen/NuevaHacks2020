import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public abstract class Person {

	protected boolean isInfected;
	protected double risk;
	protected int x;
	protected int y;
	protected int xi;
	protected int yi;
	protected PImage[] frontIcons;
	protected PImage[] leftIcons;
	protected PImage[] rightIcons;
	protected PImage[] backIcons;
	protected PImage currentIcon;
	protected int id;

	public Person() {

	}

	public Person(int id) {
		// all the image filenames are like p1front or p3left, so ID for choosing the
		// image
	}

	public void addPet(Pet.Type type) {

	}

	public void setImageIcons(PApplet p) {
		frontIcons = new PImage[] { 
				p.loadImage("people" + FileIO.fileSep + "p" + id + "frontstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "frontstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "frontstep1.png") 
			};
		leftIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "left.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "leftstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "leftstep1.png")
		};
		rightIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "right.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "rightstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "rightstep1.png")
		};
		backIcons = new PImage[] {
				p.loadImage("people" + FileIO.fileSep + "p" + id + "back.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "backstep1.png"),
				p.loadImage("people" + FileIO.fileSep + "p" + id + "backstep1.png")
		};
	}


	public void moveUpIcon(PApplet p, int i) {
		if (i % 20 == 0) {
			currentIcon = frontIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = frontIcons[2];
		}
	}

	public void moveLeftIcon(PApplet p, int i) {
		if (i % 20 == 0) {
			currentIcon = leftIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = leftIcons[2];
		}
	}

	public void moveRightIcon(PApplet p, int i) {
		if (i % 20 == 0) {
			currentIcon = rightIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = rightIcons[2];
		}
	}

	public void moveDownIcon(PApplet p, int i) {
		if (i % 20 == 0) {
			currentIcon = backIcons[1];
		} else if (i % 10 == 0) {
			currentIcon = backIcons[2];
		}
	}

}
