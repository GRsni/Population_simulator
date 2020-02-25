package grsni.sim;

import java.util.Arrays;

import processing.core.*;

public class Chunk {
	public static final int CHUNK_SIZE = 16;
	int xPos, yPos;
	float[][] values;
	OpenSimplexNoise valGenerator;
	PApplet parent;

	public Chunk(PApplet parent, OpenSimplexNoise gen, int i, int j) {
		this.parent = parent;
		this.xPos = i;
		this.yPos = j;
		valGenerator = gen;
		values = generateValues();
	}

	float[][] generateValues() {
		float[][] vals = new float[CHUNK_SIZE][CHUNK_SIZE];

		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
//				System.out.println(i + ": " + j + ", " + xPos + ": " + yPos);
				float val = (float) valGenerator.eval((i + CHUNK_SIZE * xPos) * MapGenerator.NOISE_SCALE,
						(j + CHUNK_SIZE * yPos) * MapGenerator.NOISE_SCALE) + 0.707f;
				vals[i][j] = PApplet.map(val, 0, 1.414f, 0, 255);
			}
		}
		return vals;
	}

	public float[][] getValues() {
		return values;
	}

	public void printValues() {
		for (int i = 0; i < CHUNK_SIZE; i++) {
			System.out.println(Arrays.toString(values[i]));
		}
		System.out.println();
	}

}
