package playground;

import lombok.Getter;

import java.util.LinkedList;
import java.util.Random;

@Getter
public class Playground implements Cloneable {
    private Tile[][] map;
    @Getter
    private final int size;
    private Obstacle[] obstacles;
    @Getter
    private final double percentageOfObstacles;

    /**
     * Creates playground with custom amount of obstacles
     * Mostly for test purposes
     *
     * @param size                  Size of game board
     * @param percentageOfObstacles Percentage of fields taken before game begins.
     * @throws IllegalArgumentException If odd number is given as size of the playground
     */
    public Playground(int size, double percentageOfObstacles) throws IllegalArgumentException {
        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater then 0");
        if (size % 2 == 0)
            throw new IllegalArgumentException("Size must be odd");
        if (percentageOfObstacles < 0.0 || percentageOfObstacles > 1.0)
            throw new IllegalArgumentException("Percentage of obstacles must be between 0% - 100%");

        this.size = size;
        this.percentageOfObstacles = percentageOfObstacles;

        makeMap(size);
        addObstacles();
    }

    /**
     * Creates playground with 10% obstacles
     *
     * @param size Size of game board
     * @throws IllegalArgumentException If odd number is given as size of the playground
     */
    public Playground(int size) throws IllegalArgumentException {
        this(size, 0.1);
    }

    /**
     * Copy constructor
     * Creates deep copy of playground
     *
     * @param playground model for copy
     */
    private Playground(Playground playground) {
//        this.map = playground.map.clone(); //Clone of map but refers to same objects
        this.map = new Tile[playground.size][playground.size];
        for (int i = 0; i < playground.map.length; i++)
            for (int j = 0; j < playground.map.length; j++)
                this.map[i][j] = playground.map[i][j].clone();
        this.size = playground.size;
        this.obstacles = playground.obstacles.clone();
        this.percentageOfObstacles = playground.percentageOfObstacles;
    }

    /**
     * Creates deep copy of playground
     *
     * @return Copy of current instance
     */
    public Playground clone() {
        return new Playground(this);
    }

    /**
     * Creates map
     *
     * @param height Size of the map
     */
    private void makeMap(int height) {
        map = new Tile[height][height];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    @org.jetbrains.annotations.Contract(pure = true)
    private int getNumberOfObstacles() {
        return Math.round((size * size) * (float) percentageOfObstacles);
    }

    /**
     * Generate list of obstacles on the map
     *
     * @return List of obstacles formatted for communication
     */
    public String printObstacles() {
        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < obstacles.length; i++) {
            resultBuilder.append(obstacles[i].toString());
            resultBuilder.append(i != obstacles.length - 1 ? "," : "");
        }
        return resultBuilder.toString();
    }

    /**
     * Fills map with randomly generated obstacles
     */
    private void addObstacles() {
        obstacles = new Obstacle[getNumberOfObstacles()];
        LinkedList<Integer> freeSpots = new LinkedList<>(); //Maybe hash table would be faster

        for (int i = 0; i < size * size; i++)
            freeSpots.add(i); //Project two dimensional array to one dimension

        Random random = new Random();


        for (int i = 0; i < getNumberOfObstacles(); i++) { //Generate obstacles
            int chosen = random.nextInt(freeSpots.size()); //Pick free spot
            int pickedFiled = freeSpots.get(chosen);

            freeSpots.remove(chosen); //It's no longer free

            int row = pickedFiled / size;
            int column = pickedFiled - row * size;

            map[row][column].take();
            obstacles[i] = new Obstacle(column, row);
        }
    }

    /**
     * Tried to place 2x1 block on a board.
     *
     * @param x1 y of firs 1x1
     * @param y1 x of firs 1x1
     * @param x2 y of second 1x1
     * @param y2 x of second 1x1
     * @return If arguments doesn't make valid move returns false
     */
    public boolean take(int x1, int y1, int x2, int y2) {
        if (x1 > map.length || x1 < 0)
            return false;
        if (y1 > map.length || y1 < 0)
            return false;
        if (x2 > map.length || x2 < 0)
            return false;
        if (y2 > map.length || y2 < 0)
            return false;
        if (map[x1][y1].isTaken() || map[x2][y2].isTaken())
            return false;

        if (x1 == x2) { //Horizontal block
            //Blocks aren't directly adjacent. Check if they are on the very right and left edge
            if ((Math.abs(y1 - y2) != 1) && (Math.abs(y1 - y2) != map.length - 1))
                return false;
        } else if (y1 == y2) { //Vertical block
            //Blocks aren't directly adjacent. Check if they are on the very top and bottom
            if ((Math.abs(x1 - x2) != 1) && (Math.abs(x1 - x2) != map.length - 1))
                return false;
        } else return false;


        map[x1][y1].take();
        map[x2][y2].take();
        return true;

    }

    public boolean isFull() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (vonNeumannFreeNeighbours(i, j))
                    return false;
            }
        }
        return true;
    }

    private boolean vonNeumannFreeNeighbours(int x, int y) {
        for (int i = -1; i < 2; i += 2)
            if (!map[modulus(x + i, map.length)][modulus(y, map.length)].isTaken())
                return true;

        for (int i = -1; i < 2; i += 2)
            if (!map[modulus(x, map.length)][modulus(y + i, map.length)].isTaken())
                return true;

        return false;
    }

    private int modulus(int number, int base) {
        return number < 0 ? number % base + base : number % base;
    }
}
