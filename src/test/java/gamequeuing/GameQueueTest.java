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
    public void setUp() throws Exception {
        Player one = new Player("One", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
        Player two = new Player("Two", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
        playerList = new ArrayList<>(2);
        playerList.add(one);
        playerList.add(two);
        gameQueue = new GameQueue(playerList);
    }

    @Test
    public void morituriTeSalutant() {
        Map<Player, GameStatistics> answer = gameQueue.morituriTeSalutant();

        for (Player player : playerList) {
            System.out.println(player);
            if (!answer.containsKey(player))
                fail();
        }
        if (answer.get(playerList.get(0)).getWins() != 1)
            fail();
        if (answer.get(playerList.get(1)).getWins() != 1)
            fail();
    }
}