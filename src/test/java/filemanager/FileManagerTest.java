package filemanager;

import mainlogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.fail;

public class FileManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    //ToDo Write solid tests
    @Test
    public void importPlayers() {
        try {
            ArrayList<String> errors = new ArrayList<String>();
            ArrayList<Player> players = FileManager.importPlayers(errors);

            for (Player player : players) {
                System.out.println(player);
            }
            System.out.println("Errors:");
            for (String s : errors)
                System.out.println(s);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getProgramLocation() {
    }

    @Test
    public void getPlayersFolderLocation() {
        try {
            System.out.println(FileManager.getPlayersFolderLocation());
        } catch (IOException e) {
            fail();
        }
    }
}