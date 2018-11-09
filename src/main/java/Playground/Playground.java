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

    void makeMap(int height) {
        if (height % 2 == 0)
            throw new IllegalArgumentException("Even number as height");
        map = new Tile[height][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    void addObstacles() {
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
}
