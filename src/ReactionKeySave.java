import processing.core.PApplet;

public class ReactionKeySave {
	
	int[] keyCodes = {67, 69, 70, 71, 72, 73, 76, 80, 81, 82, 84, 75, 77};
	char[] buttons = {'C', 'E', 'F', 'G', 'H', 'I', 'L', 'P', 'Q', 'R', 'T', 'K', 'M'};
	String[] messages = {"to not touch your face", "to dodge a sneeze", "to dodge a cough, to not rub your eyes"};
	String[] congrats = {"avoided touching your face", "dodged a sneeze", "dodged a cough, didn't rub your eyes"};
	String[] reee = {"touched your face", "got hit by a sneeze", "got coughed on, rubbed your eyes"};
	int key;
	int message;
	int maxTime;
	int time;
	
	short saved = 0; //-1 is no, 1 is yes, 0 is undecided
	
	public ReactionKeySave() {
		key = (int)(Math.random()*keyCodes.length);
		message = (int)(Math.random()*messages.length);
		maxTime = (int)(50*Math.random()+40);
	}
	
	public void draw(PApplet g, boolean keys[]) {
		g.pushStyle();
		g.fill((float)Math.random()*255, (float)Math.random()*255, (float)Math.random()*255);
		g.rect(g.width*0.25f, g.height*0.4f, g.width*0.5f, g.height*0.1f);
		g.fill(0);
		if ((keys[keyCodes[key]] || saved == 1) && saved != -1) { //saved
			g.text("NICE SAVE, you " + congrats[message], g.width*0.45f, g.height*0.45f);
			saved = 1;
			
		} else if ((time > maxTime || saved == -1) && saved != 1) {
			g.text("TOO SLOW! You " + reee[message], g.width*0.45f, g.height*0.5f);
				
			saved = -1;
		
		} else {
			g.text("Press " + buttons[key] + " " + messages[message], g.width*0.45f, g.height*0.45f);
			
		}
		
		time++;
		
		g.popStyle();
	}
	
}
