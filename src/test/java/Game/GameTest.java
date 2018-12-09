package Game;

import MainLogic.Player;
import Playground.Playground;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class GameTest {

    private Player playerOne = new Player("One", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
    private Player playerTwo = new Player("Two", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
    private Playground playground = new Playground(13, 0.0);
    private Game game;

    @Before
    public void setUp() {
        game = new Game(playerOne, playerTwo, playground);
    }

    @Test
    public void play() {
        GameResult winner = game.play();
        System.out.println(winner.toString());
        if (winner.getPlayer() != playerOne)
            fail();
    }
}