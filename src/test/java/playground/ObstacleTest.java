package playground;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.fail;

public class ObstacleTest {

    @Test
    public void testToString() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Obstacle obstacle = new Obstacle(x, y);
        String expectedResult = String.format("{%d;%d}", x, y);

        //System.out.println(expectedResult);
        //System.out.println(obstacle.toString());

        if (!obstacle.toString().equals(expectedResult))
            fail();
    }

    @Test
    public void getX() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Obstacle obstacle = new Obstacle(x, y);

        if (obstacle.getX() != x)
            fail();
    }

    @Test
    public void getY() {
        Random random = new Random();
        int x = random.nextInt();
        int y = random.nextInt();

        Obstacle obstacle = new Obstacle(x, y);

        if (obstacle.getY() != y)
            fail();
    }
}