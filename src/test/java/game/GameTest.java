package game;

import mainlogic.Player;
import org.junit.Before;
import org.junit.Test;
import playground.Playground;

import static org.junit.Assert.fail;

public class GameTest {

    private Player playerOne = new Player("123456","One", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
    private Player playerTwo = new Player("987654","Two", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
    private Playground playground = new Playground(21);
    private Game game;

    @Before
    public void setUp() {
        game = new Game(playerOne, playerTwo, playground);
    }

    @Test
    public void play() {
        try {
            game.GameResult winner = game.play();
            if (winner.getPlayer() != playerOne)
                fail();
        }
        catch (Exception e) {
            fail();
        }
    }
}