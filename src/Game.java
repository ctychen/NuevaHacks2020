import java.util.ArrayList;

public class Game {
	
	public static final int front = 0, frontstep1 = 1, frontstep2 = 2, right = 3, rightstep1 = 4, rightstep2 = 5, left = 6, leftstep1 = 7, leftstep2 = 8, back = 9, backstep1 = 10, backstep2=11;
	
	public Player player;
	protected int numInfected;
	protected static ArrayList<Person> plist; // NPCs
	protected static ArrayList<Vehicle> vlist;
	protected static int numPoints;

	
	public Game() {
		numPoints=0;
	}
	
	public void setPlayer(int p) {
		player = new Player(p);
		plist = new ArrayList<Person>();
		vlist = new ArrayList<Vehicle>();
	}
	
	public void updateGame() {
		//calls methods to:
		//move NPCs, cars, and pets
		//recalculate risk and update player/npc infection status if necessary
		//update score
		//check game state
		updateGameStatus();
		updateRisk();
		
	}
	
	//updates risk based on movement and does a check for infection
	public void updateRisk() {
		ArrayList<Integer> risklist = riskCalc();
		player.risk=player.initRisk+risklist.get(0);
		if(player.risk>100) {
			player.risk=100;
		}
		for(int i=1;i<risklist.size();i++)
		{
			if(!player.isInfected)
			{
				if(plist.get(risklist.get(i)).isInfected)
				{
					int x=(int)(Math.random()*100);
					if(x<player.risk) {
						player.isInfected=true;
					}
				}
			}
			else
			{
				int x=(int)(Math.random()*100);
				if(x<player.risk) {
					numInfected++;
				}
			}
		}
	}
	
	//changes initRisk by a certain amount, for risk based on reflex checks and PPE
	public void changeInitRisk(int change) {
		player.initRisk+=change;
		if(player.initRisk>100) {
			player.initRisk=100;
		}
		else if(player.initRisk<0) {
			player.initRisk=0;
		}
	}
	
	//calculates distance from player to other NPCs, calculates risk based off of that
	//returns arraylist of ints with added risk first, then the indices of all the people nearby that are infected
	private ArrayList<Integer> riskCalc() {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		int addedrisk=0;
		list.add(0);
		if (plist != null && plist.size() > 0) {
			for(int i=0;i<plist.size();i++)
			{
				Person p = plist.get(i);
				double distance= player.getDistanceFrom(p);
				
				if(distance < DrawingSurface.safeDistance) {
					addedrisk+=(DrawingSurface.safeDistance)*(DrawingSurface.safeDistance)/7000;
					list.add(i);
					System.out.println("Added " + addedrisk);
					DrawingSurface.playSound = "scream.wav";
				}
			}
		}
		list.set(0, addedrisk);
		System.out.println("Risk is " + player.getRisk());
		return list;
	}
	
	public void updateGameStatus() {
		if(player.isInfected) {
			
		}
		else
		{
			if(Math.random()*10==10)
			numPoints++;
		}
		System.out.println(numPoints);
	}
	
}
