package grsni.sim;

import java.util.Arrays;

import processing.core.*;

public class Chunk {
	int id;
	public static final int CHUNK_SIZE = 16;
	int xPos, yPos;
	float[][] values;
	PApplet parent;

	public Chunk(PApplet parent, int id, int i, int j) {
		this.id = id;
		this.parent = parent;
		this.xPos = i;
		this.yPos = j;
		values = generateValues();
	}

	float[][] generateValues() {
//		System.err.println("Chunk ID :" + id);
		float[][] vals = new float[CHUNK_SIZE][CHUNK_SIZE];

		for (int i = 0; i < CHUNK_SIZE; i++) {
			for (int j = 0; j < CHUNK_SIZE; j++) {
//				System.out.println(i + ": " + j + ", " + xPos + ": " + yPos);
				float val = generateTileValue(i, j, xPos, yPos, 0, 0);
				float variance = generateTileValue(i, j, xPos, yPos, 2000, 4000);
				float humidity = generateTileValue(i, j, xPos, yPos, 8000, 10000);
//				vals[i][j] = PApplet.map(val, 0, 1.415f, 0, 100);
				vals[i][j] = PApplet.map(val, 0, 1, 0f, 100f);
				vals[i][j]+=PApplet.map(variance, 0f, 1f, -20f, 20f);
			}
//			 System.err.println(Arrays.toString(vals[i]));
		}
		return vals;
	}

	float  generateTileValue(int xIndex, int yIndex, int xPos, int yPos, int xOffset, int yOffset) {
		float xNoisePos = MapGenerator.NOISE_SCALE * ((xIndex + CHUNK_SIZE * xPos) + xOffset);
		float yNoisePos = MapGenerator.NOISE_SCALE * ((yIndex + CHUNK_SIZE * yPos) + yOffset);
//		return (float) (SimplexNoise.noise(xNoisePos, yNoisePos));
		return parent.noise(xNoisePos, yNoisePos);
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
