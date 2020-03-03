package grsni.sim;

import processing.core.*;

public class MapGenerator {
	public static final float NOISE_SCALE = 0.017f;
	public static final int CHUNK_NUMBER = 60;

	public static final float DARK_WATER_HEIGHT = 10;
	public static final float WATER_HEIGHT = 15;
	public static final float BEACH_HEIGHT = 33;
	public static final float SAND_HEIGHT = 39;
	public static final float GRASS_HEIGHT = 62;
	public static final float ROCK_HEIGHT = 85;
	public static final float SNOW_HEIGHT = 100;

	public static final int TILE_SCALE = 1;

	int mapWidth, mapHeight;
	Chunk[][] terrain;
	PApplet parent;

	public MapGenerator(PApplet parent, int x, int h) {
		this.parent = parent;
		terrain = new Chunk[CHUNK_NUMBER][CHUNK_NUMBER];
		mapWidth = x;
		mapHeight = h;
		generateMap();

	}

	private void generateMap() {
		terrain = new Chunk[CHUNK_NUMBER][CHUNK_NUMBER];
		for (int i = 0; i < CHUNK_NUMBER; i++) {
			for (int j = 0; j < CHUNK_NUMBER; j++) {
				terrain[i][j] = new Chunk(parent, i * CHUNK_NUMBER + j, i, j);
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
		int xStartIndex = cornerX / TILE_SCALE / 16;
		int yStartIndex = cornerY / TILE_SCALE / 16;
		int visibleChunksInX = PApplet.floor(mapWidth / TILE_SCALE / Chunk.CHUNK_SIZE) + 1;
		int visibleChunksInY = PApplet.floor(mapHeight / TILE_SCALE / Chunk.CHUNK_SIZE) + 1;
		for (int i = xStartIndex; i < xStartIndex + visibleChunksInX; i++) {
			for (int j = yStartIndex; j < yStartIndex + visibleChunksInY; j++) {
				if (i >= 0 && i < CHUNK_NUMBER && j >= 0 && j < CHUNK_NUMBER) {
					Chunk chunk = terrain[i][j];
					map.image(renderChunk(chunk, TILE_SCALE), i * Chunk.CHUNK_SIZE * TILE_SCALE,
							j * Chunk.CHUNK_SIZE * TILE_SCALE);
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
				int heightColor = getTileColor(c.getValues()[i][j]);
				chunkMap.fill(heightColor);
				chunkMap.square(i * scl, j * scl, scl);
			}
		}
		if (Simulator.debugging) {
			chunkMap.noFill();
			chunkMap.stroke(255, 0, 0);
			chunkMap.square(0, 0, Chunk.CHUNK_SIZE * scl);
			chunkMap.fill(255, 0, 0);
			chunkMap.textAlign(PApplet.CENTER);
			chunkMap.text(c.xPos + ", " + c.yPos, Chunk.CHUNK_SIZE / 2 * scl, Chunk.CHUNK_SIZE / 2 * scl);
		}
		chunkMap.endDraw();
		return chunkMap;
	}

	int getTileColor(float h) {
		int color = 0xffff0000;
		if (h < WATER_HEIGHT) {
			color = COLORS.SEA;
		} else if (h < BEACH_HEIGHT) {
			color = COLORS.BEACH;
		} else if (h < SAND_HEIGHT) {
			color = COLORS.SAND;
		} else if (h < GRASS_HEIGHT) {
			color = COLORS.GRASS;
		} else if (h < ROCK_HEIGHT) {
			color = COLORS.ROCK;
		} else if (h < SNOW_HEIGHT) {
			color = COLORS.SNOW;
		}
		return color;
	}

}

class COLORS {

	public final int RED_TEAM= 0xffff0000;
	public final int GREEN_TEAM = 0xff00ff00;
	public final int BLUE_TEAM = 0xff0000ff;

	public static final int SEA = 0xff0d63d3;
	public static final int BEACH = 0xff3adeb5;
	public static final int SAND = 0xffdfeb63;
	public static final int GRASS = 0xff44b81a;
	public static final int TREE = 0xff115d03;
	public static final int ROCK = 0xffbbbbbb;
	public static final int SNOW = 0xffffffff;

}
