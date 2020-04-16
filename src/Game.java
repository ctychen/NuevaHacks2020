import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;


public class Game {
	
	public static final int front = 0, frontstep1 = 1, frontstep2 = 2, right = 3, rightstep1 = 4, rightstep2 = 5, left = 6, leftstep1 = 7, leftstep2 = 8, back = 9, backstep1 = 10, backstep2=11;
	
	public Player player;
	protected int numInfected;
	protected static ArrayList<Person> plist; // NPCs
	protected static ArrayList<Vehicle> vlist; // cars
	protected static int numPoints;
	private int countdown;
	
	public Game() {
		numPoints=0;
		countdown=3600;
	}
	
	public void setNPCsInfected() {
		for (int i = 0; i < plist.size(); i++) {
			Person p = plist.get(i);
			if (Math.random() < 0.2) { // npc's have 1/5 chance of starting out infected
				p.setInfected(true);
			} else {
				p.setInfected(false);
			}
		}
	}
	
	public void setPlayer(int p) {
		player = new Player(p);
		plist = new ArrayList<Person>();
		vlist = new ArrayList<Vehicle>();
	}
	
	public void updateGame(Map map, PApplet g, float tx, float ty) {
		//calls methods to:
		//move NPCs, cars, and pets
		//recalculate risk and update player/npc infection status if necessary
		//update score
		//check game state
		updateGameStatus();
		updateRisk();
		if (Math.random() < 0.02 && vlist.size() < 50)
			spawnCars(map, g, tx, ty);
	}
	
	public void spawnCars(Map map, PApplet g, float tx, float ty) {
		for (Point2D.Float e : map.getCarUpStartingLocs()) {
			if (Math.random() < 0.04 && e.x+tx > 0-g.width/2 && e.x+tx < 1.5f*g.width) {
				vlist.add(new Vehicle(true, true, (int)e.x, (int)e.y));
			}
		}
		for (Point2D.Float e : map.getCarDownStartingLocs()) {
			if (Math.random() < 0.04 && e.x+tx > 0-g.width/2 && e.x+tx < 1.5f*g.width) {
				vlist.add(new Vehicle(true, false, (int)e.x, (int)e.y));
			}
		}
		for (Point2D.Float e : map.getCarLeftStartingLocs()) {
			if (Math.random() < 0.04 && e.y+ty > 0-g.height/2 && e.y+ty < 1.5f*g.height) {
				vlist.add(new Vehicle(false, false, (int)e.x, (int)(e.y-g.width*0.04f)));
			}
		}
		for (Point2D.Float e : map.getCarRightStartingLocs()) {
			if (Math.random() < 0.04 && e.y+ty > 0-g.height/2 && e.y+ty < 1.5f*g.height) {
				vlist.add(new Vehicle(false, true, (int)e.x, (int)(e.y-g.width*0.04f)));
			}
		}
	}
	
	//updates risk based on movement and does a check for infection
	public void updateRisk() {
		ArrayList<Integer> risklist = riskCalc();
		player.risk=player.initRisk+ (int)(0.5*(player.risk - player.initRisk)) + risklist.get(0);
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
					addedrisk+=(DrawingSurface.safeDistance-0.9*distance)*(DrawingSurface.safeDistance-0.9*distance)/80;
					if (!p.isInfected) {
						addedrisk+=(DrawingSurface.safeDistance)*(DrawingSurface.safeDistance)/7000;
					} else {
						// Coronavirus is hella infectious, so if they're infected, your risk goes up way faster
						addedrisk+=(DrawingSurface.safeDistance)*(DrawingSurface.safeDistance)/3000;
					}
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
			if(Math.random()*15==10)
			{
				numPoints++;
			}
			countdown--;
		}
		else
		{
			if(Math.random()*10==10)
			{
				numPoints++;
			}
		}
		System.out.println(numPoints);
	}
	
}
