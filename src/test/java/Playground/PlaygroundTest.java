package Playground;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;

public class PlaygroundTest {

    private int expectedHeight = 21;
    private Playground tested;

    @Before
    public void setUp() {
        tested = new Playground();
    }

    @Test(expected = IllegalArgumentException.class)
    public void makeMap() {
        int temporaryHeight = 20;
        tested.makeMap(temporaryHeight);
    }

    @Test
    public void makeMap1() {
        if (expectedHeight % 2 == 0) fail();
        tested.makeMap(expectedHeight);
        int width = tested.getMap().length;
        if (width <= 0 || width % 2 == 0) fail();
    }

    @Test
    public void addObstaclesPreviousTest() {
        tested.makeMap(expectedHeight);
        tested.addObstacles();
        int width = tested.getMap().length;
        Tile[][] map = tested.getMap();
        int numberOfObstacles = Math.round((width * width) * tested.getPercentageOfObstacles());
        if (numberOfObstacles <= 0) fail();

        int actualNumberOfObstacles = 0;

        for (Tile[] tileArray : map) {
            for (Tile tile : tileArray) {
                if (tile.isTaken())
                    actualNumberOfObstacles++;
            }
        }
        if (actualNumberOfObstacles - numberOfObstacles != 0)
            fail("There are not as many obstacles as there should be");
    }

    @Test
    public void validateTile() {
        tested.makeMap(expectedHeight);
        Tile[][] map = tested.getMap();
        map[0][0].take();
        map[0][1].take();

        int x1 = 1, y1 = 1; // Correct coordinates of a new tile
        int x2 = 0, y2 = 1; //

        boolean result = tested.take(y1, x1, y2, x2);

        if (!result) fail("Correct tile is considered wrong");

        x1 = 0;
        y1 = 0; // Incorrect coordinates of a new tile
        x2 = 1;
        y2 = 0;
        result = tested.take(y1, x1, y2, x2);

        if (result) fail("Incorrect tile is considered valid");

    }

    @Test
    public void addObstacles() {
        Playground playground = new Playground();

        int mapSize = 5;
        playground.makeMap(mapSize);
        playground.addObstacles();

        int numberOfObstacles = Math.round((mapSize * mapSize) * playground.getPercentageOfObstacles());
        String result = playground.printObstacles();

        Pattern pattern = Pattern.compile("\\{\\d+;\\d+}"); //Pattern for single obstacle

        Matcher matcher = pattern.matcher(result);
        int count = 0;
        while (matcher.find())
            count++;
        if (count != numberOfObstacles)
            fail();


        Obstacle[] testObstacles = {new Obstacle(1, 2), new Obstacle(17, 4), new Obstacle(3, 8)};
        String expectedResult = "{1;2},{17;4},{3;8}";

        playground.loadObstacles(testObstacles);
        if (!playground.printObstacles().equals(expectedResult))
            fail();

        //System.out.println(playground.printObstacles());
    }

    @Test
    public void take() {
        Playground playground = new Playground();
        playground.makeMap(7);

        if (playground.take(0, 0, 6, 6)) //Corner invalid tile
            fail();
        if (playground.take(0, 6, 6, 0)) //Corner invalid tile
            fail();

        if (!playground.take(2, 1, 2, 2)) //Normal horizontal tile
            fail();
        if (!playground.take(0, 4, 1, 4)) //Normal vertical tile
            fail();

        if (!playground.take(0, 0, 0, 6)) //Wrapped horizontal tile
            fail();
        if (!playground.take(0, 2, 6, 2)) //Wrapped vertical tile
            fail();

        if (playground.take(2, 1, 2, 2)) //Again normal horizontal tile
            fail();
        if (playground.take(0, 4, 1, 4)) //Again normal vertical tile
            fail();

        if (playground.take(0, 0, 0, 6)) //Again wrapped horizontal tile
            fail();
        if (playground.take(0, 2, 6, 2)) //Again wrapped vertical tile
            fail();
    }
}