import java.util.ArrayList;

public class Game {
	
	public static final int front = 0, frontstep1 = 1, frontstep2 = 2, right = 3, rightstep1 = 4, rightstep2 = 5, left = 6, leftstep1 = 7, leftstep2 = 8, back = 9, backstep1 = 10, backstep2=11;
	
	protected Player player;
	protected ArrayList<Person> plist;
	protected ArrayList<Vehicle> vlist;
	
	public Game() {
		
	}
	
	public void updateGame() {
		//calls methods to:
		//move NPCs, cars, and pets
		//recalculate risk and update player/npc infection status if necessary
		//update score
		//check game state
	}
	
	public void updateRisk() {
		
	}
	
	
	
	
}
