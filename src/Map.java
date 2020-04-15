import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Map {

	private ArrayList<ArrayList<String>> map = new ArrayList<ArrayList<String>>();
	// private ArrayList<ArrayList<Integer>> mDet = new
	// ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> maxX = new ArrayList<Integer>();
	private ArrayList<Integer> maxY = new ArrayList<Integer>();
	
	private PImage[] tiles;

	public int m; // Current map index

	public Map(PApplet g) {
		tiles = new PImage[] { 
				g.loadImage("terrain" + FileIO.fileSep + "background.png"),      //0
				g.loadImage("terrain" + FileIO.fileSep + "pathbottom.png"),      //1
				g.loadImage("terrain" + FileIO.fileSep + "pathbottomleft2.png"), //2
				g.loadImage("terrain" + FileIO.fileSep + "pathbottomleft1.png"), //3
				g.loadImage("terrain" + FileIO.fileSep + "pathbottomright2.png"),//4
				g.loadImage("terrain" + FileIO.fileSep + "pathbottomright1.png"),//5
				g.loadImage("terrain" + FileIO.fileSep + "pathcenter.png"),      //6
				g.loadImage("terrain" + FileIO.fileSep + "pathleft.png"),        //7
				g.loadImage("terrain" + FileIO.fileSep + "pathright.png"),       //8
				g.loadImage("terrain" + FileIO.fileSep + "pathtop.png"),         //9
				g.loadImage("terrain" + FileIO.fileSep + "pathtopleft2.png"),    //10
				g.loadImage("terrain" + FileIO.fileSep + "pathtopleft1.png"),    //11
				g.loadImage("terrain" + FileIO.fileSep + "pathtopright2.png"),   //12
				g.loadImage("terrain" + FileIO.fileSep + "pathtopright1.png"),   //13
				};
	}
	protected Point2D.Float playerStart = null;
	
	public void loadMap(int i) { // Loads a map, basically adding it to the appropriate arraylists
		try {
			map.add(fixMap(FileIO.reeeeeeeeeeeeeeeeeead("maps" + FileIO.fileSep + "m" + i)));
			/*
			 * ArrayList<Integer> yoink = new ArrayList<Integer>(); for (int j = 0; j <
			 * map.get(map.size() - 1).get(0).length(); j++) { if (map.get(map.size() -
			 * 1).get(0).indexOf(j + ":") != -1)
			 * yoink.add(Integer.parseInt(map.get(map.size() - 1).get(0)
			 * .substring(map.get(map.size() - 1).get(0).indexOf(j + ":") + 2,
			 * map.get(map.size() - 1) .get(0).indexOf(",", map.get(map.size() -
			 * 1).get(0).indexOf(j + ":") + 2)))); } mDet.add(yoink);
			 */
			maxX.add(0);
			for (int j = 1; j < map.get(map.size() - 1).size(); j++)
				if (maxX.get(maxX.size() - 1) < map.get(map.size() - 1).get(j).length())
					maxX.set(maxX.size() - 1, map.get(map.size() - 1).get(j).length());
			maxY.add(map.get(map.size() - 1).size());
			System.out.println("This map is " + maxX.get(maxX.size() - 1) + " by " + maxY.get(maxY.size() - 1));

		} catch (IOException e) {
			System.out.println("Ruh Roh");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(PApplet g, float tx, float ty) {

		g.pushStyle();

		for (int i = 1; i < map.get(m).size(); i++) {
			for (int j = 0; j < map.get(m).get(i).length(); j++) {
				g.stroke(0);
				switch ((char) map.get(m).get(i).charAt(j)) {

					case '-': // Grass
						g.image(tiles[0], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '=': // Path
						g.image(tiles[6], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '5':
						g.image(tiles[6], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '7':
						g.image(tiles[4], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '8':
						g.image(tiles[9], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '9':
						g.image(tiles[2], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '4':
						g.image(tiles[7], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '6':
						g.image(tiles[8], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '1':
						g.image(tiles[12], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '2':
						g.image(tiles[1], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '3':
						g.image(tiles[10], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '#':
						g.image(tiles[11], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '!':
						g.image(tiles[13], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '&':
						g.image(tiles[5], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case '(':
						g.image(tiles[3], g.width*0.05f*j, g.width*0.05f*(i-1), g.width*0.05f, g.width*0.05f);
						break;
					case 'x': // Player starts here
						g.fill(200, 200, 100);
						g.rect(g.width * 0.05f * j, g.width * 0.05f * (i - 1), g.width * 0.05f, g.width * 0.05f);
						playerStart = new Point2D.Float(g.width * 0.05f * j + g.width * 0.025f - g.width * 0.03f, g.width * 0.05f * (i - 1) + g.width * 0.025f - g.width * 0.05f);
						// g.image(g.loadImage("people" + FileIO.fileSep + "p1" + "front.png"), (float)(playerStart.getX() - g.width * 0.03f), (float)(playerStart.getY() - g.width * 0.05f));
				}	
			}

			g.pushMatrix();
			g.pushStyle();
			g.translate(-tx, -ty);
			g.textAlign(PConstants.LEFT);
			g.fill(255);
			g.text(map.get(m).get(0).substring(map.get(m).get(0).indexOf('\"') + 1,
					map.get(m).get(0).indexOf('\"', map.get(m).get(0).indexOf('\"') + 1)), 0, g.height * 0.03f);
			g.popMatrix();
			g.popStyle();
		}
		g.popStyle();
	}

	public ArrayList<String> getCurrentMap() {
		return map.get(m);
	}

	public int maxX() {
		return maxX.get(m);
	}

	public int maxY() {
		return maxY.get(m);
	}

	public int getMap() {
		return m;
	}

	public boolean isLoaded(int m) {
		return (m < map.size());
	}

	public void setMap(int m) {
		this.m = m;
	}
	

	//This method will basically go into the map and add all the
	//following path symbols, to make terrain drawing easier
	//
	// 7-topL  8-top    9-topR
	// 4-left  5-center 6-right
	// 1-botL  2-bot    3-botR
	//
	// &-topL2  (-topR2
	// !-botL2  #-botR2
	//
	// - - - - -
	// - = = = -
	// - = = = -
	// - = = = -
	// - - - - -
	private ArrayList<String> fixMap(ArrayList<String> raw) { //Fixes edges and stuff REEEEEEE
		
		ArrayList<String> cooked = new ArrayList<String>();
		int my = raw.size()-1;
		cooked.add(raw.get(0));
		for (int y = 1; y < raw.size(); y++) {
			int mx = raw.get(y).length()-1;
			String yeet = "";
			for (int x = 0; x < raw.get(y).length(); x++) {
				if (raw.get(y).charAt(x) == '=') { //Only do something shady if it is a path
					if (x > 0 && y > 1 && raw.get(y).charAt(x-1)=='-' && raw.get(y-1).charAt(x-1)=='-' && raw.get(y-1).charAt(x)=='-')
						yeet+='7';
					else if (x < mx && y > 1 && raw.get(y-1).charAt(x)=='-' && raw.get(y-1).charAt(x+1)=='-' && raw.get(y).charAt(x+1)=='-')
						yeet+='9';
					else if (x > 0 && y < my && raw.get(y).charAt(x-1)=='-' && raw.get(y+1).charAt(x-1)=='-' && raw.get(y+1).charAt(x)=='-')
						yeet+='1';
					else if (x < mx && y < my && raw.get(y).charAt(x+1)=='-' && raw.get(y+1).charAt(x+1)=='-' && raw.get(y+1).charAt(x)=='-')
						yeet+='3';
					else if (x > 0 && raw.get(y).charAt(x-1)=='-')
						yeet+='4';
					else if (y > 1 && raw.get(y-1).charAt(x)=='-')
						yeet+='8';
					else if (x < mx && raw.get(y).charAt(x+1)=='-')
						yeet+='6';
					else if (y < my && raw.get(y+1).charAt(x)=='-')
						yeet+='2';
					else if (y > 1 && x > 0 && raw.get(y-1).charAt(x-1)=='-')
						yeet+='&';
					else if (y > 1 && x < mx && raw.get(y-1).charAt(x+1)=='-')
						yeet+='(';
					else if (y < my && x > 0 && raw.get(y+1).charAt(x-1)=='-')
						yeet+='!';
					else if (y < my && x < mx && raw.get(y+1).charAt(x+1)=='-')
						yeet+='#';
					else
						yeet+='5';
				} else
					yeet += raw.get(y).charAt(x);
			}
			cooked.add(yeet);
		}
		
		return cooked;
	}

	public Point2D.Float getPlayerStart() {
		return this.playerStart;
	}
}
