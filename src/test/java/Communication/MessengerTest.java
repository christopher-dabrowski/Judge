package Communication;

import MainLogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MessengerTest {

    Player playerOne = new Player("One", "Jan", "Kowalski", "java -jar .\\ProxyPlayer.jar");
    Player playerTwo = new Player("Two", "John", "Bukowski", "java -jar .\\ProxyPlayer1.jar");
    Messenger messenger = new Messenger(playerOne, playerTwo);
    final int playGroundsize = 10;

    @Before
    public void setUp() throws Exception {
        messenger.openCommunication();
    }

    @After
    public void tearDown() {
        messenger.endCommunication();
    }

    @Test
    public void openCommunication() {
//        messenger.openCommunication();
////        Process process = Runtime.getRuntime().exec("tasklist.exe");
////        Scanner scanner = new Scanner(new InputStreamReader(process.getInputStream()));
////        while (scanner.hasNext()) {
////            System.out.println(scanner.nextLine());
////        }
////        scanner.close();
//        messenger.endCommunication();
    }

    @Test
    public void sendPlaygroundSizePlayer1() {
        try {
            messenger.sendPlaygroundSizePlayer(playGroundsize, playerOne);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void sendPlaygroundSizePlayer2() {
        try {
            messenger.sendPlaygroundSizePlayer(playGroundsize, playerTwo);
        } catch (Exception e) {
            fail();
        }
    }
}