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

import static org.junit.Assert.*;

public class FileManagerTest {
    private static String tempFolderName = "TEMP";
    private HashMap<String, String> playerIndexesAndInfoFiles = null;

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    private void importPlayersSetup() {
        new File(tempFolderName).mkdir();

        playerIndexesAndInfoFiles = new HashMap<String, String>();
        playerIndexesAndInfoFiles.put("123456", "aliasAA" + System.lineSeparator() +"Amadeusz Arogund" + System.lineSeparator() + "superAAAA.exe");
        playerIndexesAndInfoFiles.put("987654", "BARB" + System.lineSeparator() + "Barnabius Zawacki" + System.lineSeparator() + "liczydlo300.js -run");
        playerIndexesAndInfoFiles.put("999998", null);
        playerIndexesAndInfoFiles.put("999999", "ciekawaNazwa" + System.lineSeparator() + "mo333 " + System.lineSeparator() + "" + System.lineSeparator() + "");

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

    @Test
    public void importPlayers() {
        importPlayersSetup();

        try {
            ArrayList<String> errors = new ArrayList<String>();
            ArrayList<Player> players = FileManager.importPlayers(tempFolderName, errors);

            assertEquals(2, players.size());
            assertArrayEquals(new String[] {"Folder 999998 is missing info.txt file", "Error when parsing info.txt form folder 999999 Error: Invalid player name"}, errors.toArray());

//            for (Player player : players) {
//                System.out.println(player);
//            }

//            System.out.println("Errors:");
//            for (String s : errors)
//                System.out.println(s);
        } catch (Exception e) {
            fail();
        }

        importPlayersTearDown();
    }

    private void deleteTemporaryPlayersData(HashMap<String, String> playerIndexesAndInfoFiles) {
        try {
            for (String playerIndex : playerIndexesAndInfoFiles.keySet()) {
                String playerFolderPath = tempFolderName + "/" + playerIndex;

                String playerInfoFileContent = playerIndexesAndInfoFiles.get(playerIndex);
                if (playerInfoFileContent != null) {
                    if (!new File(playerFolderPath + "/info.txt").delete())
                        fail();
                }

                if (!new File(playerFolderPath).delete())
                    fail();
            }
        } catch (Exception e) {
            fail();
        }
    }

    public void importPlayersTearDown() {
        deleteTemporaryPlayersData(playerIndexesAndInfoFiles);

        if (!new File(tempFolderName).delete())
            fail();
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