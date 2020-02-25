package grsni.sim;

import processing.core.*;

public class Simulator extends PApplet {
	MapGenerator mapGen;
	public static boolean debugging=false;

	public static void main(String[] args) {
		PApplet.main(new String[] { grsni.sim.Simulator.class.getName() });
	}

	public void settings() {
		size(800, 600);
	}

	public void setup() {
		mapGen=new MapGenerator(this, width, height);

	}

	public void draw() {

		background(255);
		image(mapGen.renderMap(0, 0), 0, 0);
	}
	
	public void keyPressed() {
		if(key==' ') {
			debugging=!debugging;
		}
		
	}

}
