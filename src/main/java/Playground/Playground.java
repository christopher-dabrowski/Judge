package Playground;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Random;

@Getter
public class Playground implements Cloneable {
    private Tile[][] map;
    private int size;
    @Getter //For creating second map with same obstacles
    private Obstacle[] obstacles;
    @Setter
    private float percentageOfObstacles = 0.1f;

    public Playground clone() {
        Playground clone = new Playground();
        clone.map = this.map;
        clone.size = this.size;
        clone.obstacles = obstacles.clone();
        clone.percentageOfObstacles = this.percentageOfObstacles;
        return clone;
    }

    public void makeMap(int height) {
        if (height % 2 == 0)
            throw new IllegalArgumentException("Even number as height");
        size = height;
        map = new Tile[height][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    @org.jetbrains.annotations.Contract(pure = true)
    private int getNumberOfObstacles() {
        return Math.round((size * size) * percentageOfObstacles);
    }

    /**
     * Generate list of obstacles on the map
     * @return List of obstacles formatted for communication
     */
    public String printObstacles() {
        StringBuilder resultBuilder = new StringBuilder();

        for (int i=0; i<obstacles.length; i++)
            resultBuilder.append(obstacles[i].toString() + (i != obstacles.length-1 ? "," : ""));

        return resultBuilder.toString();
    }

    /**
     * Fills map with randomly generated obstacles
     */
    public void addObstacles() {
        obstacles = new Obstacle[getNumberOfObstacles()];
        LinkedList<Integer> freeSpots = new LinkedList<Integer>(); //Maybe hash table would be faster

        for (int i=0; i<size*size; i++)
            freeSpots.add(new Integer(i)); //Project two dimensional array to one dimension

        Random random = new Random();


        for (int i=0; i<getNumberOfObstacles(); i++) { //Generate obstacles
            int chosen = random.nextInt(freeSpots.size()); //Pick free spot
            int pickedFiled = freeSpots.get(chosen);

            freeSpots.remove(chosen); //It's no longer free

            int row = pickedFiled/size;
            int column = pickedFiled - row*size;

            map[row][column].take();
            obstacles[i] = new Obstacle(column, row);
        }
    }

    public void loadObstacles(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Tried to place 2x1 block on a board.
     *
     * @param y1 y of firs 1x1
     * @param x1 x of firs 1x1
     * @param y2 y of second 1x1
     * @param x2 x of second 1x1
     * @return If arguments doesn't make valid move returns false
     */
    public boolean take(int y1, int x1, int y2, int x2) {
        if (y1 > map.length || y1 < 0)
            return false;
        if (x1 > map.length || x1 < 0)
            return false;
        if (y2 > map.length || y2 < 0)
            return false;
        if (x2 > map.length || x2 < 0)
            return false;
        if (map[y1][x1].isTaken() || map[y2][x1].isTaken())
            return false;

        if (y1 == y2) { //Horizontal block
            if (Math.abs(x1 - x2) != 1) { //Blocks aren't directly adjacent
                if (!((x1 == 0 && x2 == map.length - 1) || (x2 == 0 && x1 == map.length - 1))) //Check if they are on the very right and left edge
                    return false;
            }
        } else if (x1 == x2) { //Vertical block
            if (Math.abs(y1 - y2) != 1) { //Blocks aren't directly adjacent
                if (!((y1 == 0 && y2 == map.length - 1) || (y2 == 0 && y1 == map.length - 1))) //Check if they are on the very top and bottom
                    return false;
            }
        } else {
            return false;
        }


        map[y1][x1].take();
        map[y2][x2].take();
        return true;

    }
}
