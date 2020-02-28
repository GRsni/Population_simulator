package grsni.sim;

import processing.core.*;

public class MapGenerator {
	public static final float NOISE_SCALE = 0.05f;
	public static final int CHUNK_NUMBER = 200;

	int scl = 2, mapWidth, mapHeight;
	Chunk[][] terrain;
	PApplet parent;
	OpenSimplexNoise generator;

	public MapGenerator(PApplet parent, int x, int h) {
		this.parent = parent;
		terrain = new Chunk[CHUNK_NUMBER][CHUNK_NUMBER];
		mapWidth = x;
		mapHeight = h;
		generator = new OpenSimplexNoise(System.nanoTime());
		generateMap();

	}

	private void generateMap() {
		terrain = new Chunk[CHUNK_NUMBER][CHUNK_NUMBER];
		for (int i = 0; i < CHUNK_NUMBER; i++) {
			for (int j = 0; j < CHUNK_NUMBER; j++) {
				terrain[i][j] = new Chunk(parent, generator, i, j);
//				terrain[i][j].printValues();
			}
		}

	}

	PGraphics renderMap(int cornerX, int cornerY) {
		PGraphics mapImage = parent.createGraphics(mapWidth, mapHeight);
		mapImage.beginDraw();
		mapImage.noStroke();

		renderChunks(mapImage, cornerX, cornerY);

		mapImage.endDraw();
		return mapImage;
	}

	void renderChunks(PGraphics map, int cornerX, int cornerY) {
		int xStartIndex = cornerX / scl / 16;
		int yStartIndex = cornerY / scl / 16;
		int visibleChunksInX = PApplet.floor(mapWidth / scl / Chunk.CHUNK_SIZE) + 1;
		int visibleChunksInY = PApplet.floor(mapHeight / scl / Chunk.CHUNK_SIZE) + 1;
		for (int i = xStartIndex; i < xStartIndex + visibleChunksInX; i++) {
			for (int j = yStartIndex; j < yStartIndex + visibleChunksInY; j++) {
				if (i >= 0 && i < CHUNK_NUMBER && j >= 0 && j < CHUNK_NUMBER) {
					Chunk chunk = terrain[i][j];
					map.image(renderChunk(chunk, scl), i * Chunk.CHUNK_SIZE * scl, j * Chunk.CHUNK_SIZE * scl);
				}
			}
		}
	}

	public PGraphics renderChunk(Chunk c, int scl) {
		PGraphics chunkMap = parent.createGraphics(Chunk.CHUNK_SIZE * scl, Chunk.CHUNK_SIZE * scl);
		chunkMap.beginDraw();

		chunkMap.noStroke();
		for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
			for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
				chunkMap.fill(c.getValues()[i][j]);
				chunkMap.square(i * scl, j * scl, scl);
			}
		}
		if (Simulator.debugging) {
			chunkMap.noFill();
			chunkMap.stroke(255, 0, 0);
			chunkMap.square(0, 0, Chunk.CHUNK_SIZE * scl);
			chunkMap.fill(255, 0, 0);
			chunkMap.text(c.xPos + ", " + c.yPos, Chunk.CHUNK_SIZE / 2 * scl, Chunk.CHUNK_SIZE / 2 * scl);
		}
		chunkMap.endDraw();
		return chunkMap;
	}

}

class COLORS {
	public final int SEA = 0xff0d1ed3;
	public final int BEACH = 0xff3ed8ea;
	public final int SAND = 0xffe8f088;
	public final int GRASS = 0xff0ebf06;
	public final int TREE = 0xff115d03;
	public final int ROCK = 0xffbbbbbb;
	public final int SNOW = 0xffffffff;
	public final int RED_TEAM= 0xffff0000;
	public final int GREEN_TEAM = 0xff00ff00;
	public final int BLUE_TEAM = 0xff0000ff;

}
