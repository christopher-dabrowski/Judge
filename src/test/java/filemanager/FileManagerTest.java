package filemanager;

import mainlogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import static org.junit.Assert.fail;

public class FileManagerTest {
    private static String tempFolderName = "TEMP";

    @Before
    public void setUp() throws Exception {
        new File(tempFolderName).mkdir();
    }

    @After
    public void tearDown() throws Exception {

        File tempFolder = new File(tempFolderName);
        if (!tempFolder.delete()) fail(); //Wasn't able to delete folder
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
        try {
            String location = FileManager.getProgramLocation();

            if (!location.matches(".*Judge"))
                fail();
        }
        catch (Exception e) {
            fail();
        }
    }
}