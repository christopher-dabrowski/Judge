package Game;

import MainLogic.Player;
import Playground.Playground;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class GameTest {

    private Player playerOne = new Player("123456","One", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
    private Player playerTwo = new Player("987654","Two", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
    private Playground playground = new Playground();
    private Game game;

    @Before
    public void setUp() {
        playground.makeMap(13);
        game = new Game(playerOne, playerTwo, playground);
    }

    @Test
    public void play() {
        try {
            GameResult winner = game.play();
            System.out.println(winner.toString());
            if (winner.getPlayer() != playerOne)
                fail();
        }
        catch (Exception e) {
            fail();
        }
    }
}