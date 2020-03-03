package grsni.sim;

public class TileColourer {

	public static final float DARK_WATER_HEIGHT = 10;
	public static final float WATER_HEIGHT = 15;
	public static final float BEACH_HEIGHT = 33;
	public static final float SAND_HEIGHT = 39;
	public static final float GRASS_HEIGHT = 62;
	public static final float ROCK_HEIGHT = 85;
	public static final float SNOW_HEIGHT = 100;

	public static int[][] getTileCompleteColor(float[][][] values) {
		int[][] tiles = new int[values[0].length][values[0][0].length];

		for (int i = 0; i < values[0].length; i++) {
			for (int j = 0; j < values[0][0].length; j++) {
				tiles[i][j] = getTileBaseColor(values[0][i][j]);
			}
		}

		return tiles;
	}

	public static int getTileBaseColor(float val) {
		int color = 0xffff0000;
		if (val < WATER_HEIGHT) {
			color = COLORS.SEA;
		} else if (val < BEACH_HEIGHT) {
			color = COLORS.BEACH;
		} else if (val < SAND_HEIGHT) {
			color = COLORS.SAND;
		} else if (val < GRASS_HEIGHT) {
			color = COLORS.GRASS;
		} else if (val < ROCK_HEIGHT) {
			color = COLORS.ROCK;
		} else if (val < SNOW_HEIGHT) {
			color = COLORS.SNOW;
		}
		return color;
	}
}

class COLORS {

	public static final int RED_TEAM = 0xffff0000;
	public static final int GREEN_TEAM = 0xff00ff00;
	public static final int BLUE_TEAM = 0xff0000ff;

	public static final int SEA = 0xff0d63d3;
	public static final int BEACH = 0xff3adeb5;
	public static final int SAND = 0xffdfeb63;
	public static final int GRASS = 0xff44b81a;
	public static final int TREE = 0xff115d03;
	public static final int ROCK = 0xffbbbbbb;
	public static final int SNOW = 0xffffffff;

}