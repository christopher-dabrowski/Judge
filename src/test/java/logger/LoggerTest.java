package logger;

import game.Game;
import game.GameResult;
import mainlogic.Player;
import org.junit.Before;
import org.junit.Test;
import playground.Playground;

public class LoggerTest {

    private Player playerOne;
    private Player playerTwo;
    private Playground playground;
    private Game game;

    @Before
    public void setUp() throws Exception {

        playerOne = new Player("123456", "Deeper Blue", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
        playerTwo = new Player("987654", "Garry Kasparov", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
        playground = new Playground(51);
        game = new Game(playerOne, playerTwo, playground);
    }

    @Test
    public void loggerTest() {
        GameResult gameResult = game.play();
        //TODO find a way to test it aromatically
    }
}