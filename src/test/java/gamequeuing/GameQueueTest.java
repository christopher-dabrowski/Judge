package gamequeuing;

import mainlogic.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.fail;

public class GameQueueTest {

    private GameQueue gameQueue;
    private List<Player> playerList;


    @Before
    public void setUp() {
        Player one = new Player("123456", "Deeper Blue", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar", "java -jar .\\ProxyPlayer.jar");
        Player two = new Player("987654", "Garry Kasparov", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar", "java -jar .\\ProxyPlayer1.jar");
        playerList = new ArrayList<>(2);
        playerList.add(one);
        playerList.add(two);
        gameQueue = new GameQueue(playerList);
    }

    @Test
    public void morituriTeSalutant() {
        Map<Player, GameStatistics> answer = gameQueue.morituriTeSalutant();

        for (Player player : playerList) {
            if (!answer.containsKey(player))
                fail();
        }
        int sum = 0;
        for (GameStatistics gameStatistics : answer.values()) {
            sum += gameStatistics.getWins();
        }
        if (sum != playerList.size())
            fail();
    }
}