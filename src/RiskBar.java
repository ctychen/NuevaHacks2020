import java.awt.Color;

import processing.core.PApplet;

public class RiskBar {

	float width, height, x, y, roundness, currentRisk;
	float MAX_RISK = 100;
	float MID_RISK = 50, WARNING_RISK = 75;
	float[] bgColor = {0, 0, 0}; // change this 
	float[] riskColor = {255, 0, 0}; // change this
	float[] warningColor = {255, 255, 0}; // change this
	boolean upwards = false;
	
	String text = "";
	
	// Horizontal 
	public RiskBar(float startingRisk, float width, float height, float x, float y, float roundness) {
		this.height = height;
		this.width = width;
		this.roundness = roundness;
		this.x = x;
		this.y = y;
		this.currentRisk = startingRisk;
	}

	// Vertical
	public RiskBar(float startingRisk, float width, float height, float x, float y, float roundness, boolean upwards) {
		this.height = height;
		this.width = width;
		this.roundness = roundness;
		this.x = x;
		this.y = y;
		this.currentRisk = startingRisk;
		this.upwards = upwards;
	}

	public void change(float amount) {
		if (currentRisk + amount >= MAX_RISK) {
			// "YOU'RE OOFED" effect shows	
			text = "HA YOU'RE OOFED!";
			DrawingSurface.playSound = "oof.wav";
		}
		else if (currentRisk + amount <= 0) {
			currentRisk = 0;
		}
		else {
			currentRisk += amount;	
			text = "Ruh roh, you just gained some risk";
		}
	}

	public void set(float amount) {
		this.currentRisk = amount;
		if (currentRisk < 0)
			currentRisk = 0;
		else if (currentRisk > MAX_RISK)
			currentRisk = MAX_RISK;
		if (currentRisk == MAX_RISK) {
			text = "HA YOU'RE OOFED!";
			DrawingSurface.playSound = "oof.wav";
		}
	}

	public void draw(PApplet p) {
		p.fill(bgColor[0], bgColor[1], bgColor[2]);
		p.rect(x, y, width, height, roundness);
		p.fill(riskColor[0], riskColor[1], riskColor[2]);
		if (!upwards)
			p.rect(x, y, (float) (width * ((float) currentRisk / MAX_RISK)), height, roundness);
		else
			p.rect(x, y+height, width, - (float) (height * ((float) currentRisk / MAX_RISK)), roundness);
		p.text(text, x+width*0.5f, y+height*1.4f);
	}

}
