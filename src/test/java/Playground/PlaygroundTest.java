package Playground;

import org.junit.Before;
import org.junit.Test;

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
    public void addObstacles() {
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
}