package FileManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class FileManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void importPlayers() {
        //System.out.println(FileManager.PLAYERS_FOLDER);
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