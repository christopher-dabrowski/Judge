package Playground;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class Playground {
    private Tile[][] map;
    private long[] seeds; // y , x ...
    @Setter
    private float percentageOfObstacles = 0.1f;

    public void makeMap(int height) {
        if (height % 2 == 0)
            throw new IllegalArgumentException("Even number as height");
        map = new Tile[height][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    public void addObstacles() {
        int numberOfObstacles = Math.round((map.length * map.length) * percentageOfObstacles);
        seeds = new long[2 * numberOfObstacles];
        Random rng = new Random();

        for (int i = 0; i < numberOfObstacles * 2; i++) {
            long tmp = System.currentTimeMillis();
            rng.setSeed(tmp);
            int x = rng.nextInt() % map.length;
            tmp = System.currentTimeMillis();
            rng.setSeed(tmp);
            int y = rng.nextInt() % map.length;

            if (map[y][x].isTaken()) { //
                i--;
                continue;
            }
            map[y][x].taken();
            seeds[i++] = y;
            seeds[i] = x;
        }

    }

    public boolean validateTile(int y1, int x1, int y2, int x2) {
        if (y1 > map.length || y1 < 0)
            return false;
        if (x1 > map.length || x1 < 0)
            return false;
        if (y2 > map.length || y2 < 0)
            return false;
        if (x2 > map.length || x2 < 0)
            return false;
        if (map[y1][x1].isTaken() || map[y2][x1].isTaken()) {
            return false;
        }
        return (y1 - y2 == 1) ^ (x1 - x2 == 1);

    }
}
