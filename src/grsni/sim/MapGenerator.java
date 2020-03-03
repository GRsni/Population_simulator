package grsni.sim;

import processing.core.*;

public class MapGenerator {
	public static final float NOISE_SCALE = 0.017f;
	public static final int CHUNK_NUMBER = 60;

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
		int tiles[][] = c.getTiles();
		PGraphics chunkMap = parent.createGraphics(Chunk.CHUNK_SIZE * scl, Chunk.CHUNK_SIZE * scl);
		chunkMap.beginDraw();

		chunkMap.noStroke();
		for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
			for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
				chunkMap.fill(tiles[i][j]);
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

}
