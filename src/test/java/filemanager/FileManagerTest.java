package filemanager;

import mainlogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.fail;

public class FileManagerTest {
    private static String tempFolderName = "TEMP";
    private HashMap<String, String> playerIndexesAndInfoFiles = null;

    @Before
    public void setUp() throws Exception {
        new File(tempFolderName).mkdir();

        playerIndexesAndInfoFiles = new HashMap<String, String>();
        playerIndexesAndInfoFiles.put("123456", "aliasAA\nAmadeusz Arogund\nsuperAAAA.exe");
        playerIndexesAndInfoFiles.put("987654", "BARB\nBarnabius Zawacki\nliczyd\u0142o300.js -run");
        playerIndexesAndInfoFiles.put("999998", null);
        playerIndexesAndInfoFiles.put("999999", "ciekawaNazwa\nmo333 \n\n");

        createTemporaryPlayersData(playerIndexesAndInfoFiles);
    }

    private void createTemporaryPlayersData(HashMap<String, String> playerIndexesAndInfoFiles) {
        try {
            for (String playerIndex : playerIndexesAndInfoFiles.keySet()) {
                String playerFolderPath = tempFolderName + "/" + playerIndex;
                new File(playerFolderPath).mkdir();

                String playerInfoFileContent = playerIndexesAndInfoFiles.get(playerIndex);
                if (playerInfoFileContent != null) {
                    PrintWriter writer = new PrintWriter(playerFolderPath + "/info.txt");
                    writer.write(playerInfoFileContent);
                    writer.close();
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

    private void deleteTemporaryPlayersData(HashMap<String, String> playerIndexesAndInfoFiles) {
        try {
            for (String playerIndex : playerIndexesAndInfoFiles.keySet()) {
                String playerFolderPath = tempFolderName + "/" + playerIndex;

                String playerInfoFileContent = playerIndexesAndInfoFiles.get(playerIndex);
                if (playerInfoFileContent != null) {
                    new File(playerFolderPath + "/info.txt").delete();
                }

                new File(playerFolderPath).delete();
            }
        } catch (Exception e) {
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        deleteTemporaryPlayersData(playerIndexesAndInfoFiles);

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