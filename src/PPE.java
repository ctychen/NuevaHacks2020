import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class PPE {

	protected PImage icon;
	protected int m_ac;
	protected int m_uses; // Number of times you can use it ; "hits"
	protected Type type;
	
	protected enum Type {
		
		N95(10, 5); // add more
		
		int ac, uses;
		PImage[] icons; // add image icons per PPE
		
		private Type(int typeac, int typeuses) {
			this.ac = typeac;
			this.uses = typeuses;
		}
		
		public int getAC() {
			return ac;
		}
		
		public int getUses() {
			return uses;
		}
		
//		public PImage getIcon() {
//			return icons[(int)(Math.random() * icons.length - 1)];
//		}
	}
	
	public PPE(Type type) {
		this.type = type;
		this.m_ac = type.getAC();
		this.m_uses = type.getUses();
	}
	
	
}
