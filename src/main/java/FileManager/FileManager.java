package FileManager;

import MainLogic.Player;
import Parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FileManager {
    private static String PLAYERS_FOLDER_NAME = "players";

    /**
     * Imports players from players folder
     *
     * @param errors Error messages from loading are appended here
     * @return Array of corectly loaded players
     * @throws IOException When unable to find players folder
     */
    public static ArrayList<Player> importPlayers(ArrayList<String> errors) throws IOException {
        ArrayList<Player> players = new ArrayList<Player>();

        FilenameFilter playerFolderFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.matches("\\d{6}");
            }
        };
        FilenameFilter playerInfoFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals("info.txt");
            }
        };

        File playersFolder = new File(getPlayersFolderLocation());
        for (File playerFolder : playersFolder.listFiles(playerFolderFilter)) {

            try {
                if (playerFolder.listFiles(playerInfoFilter).length != 1) //Brakuje pliku info.txt
                    throw new FileNotFoundException("Players folder missing info.txt");

                File playerInfo = playerFolder.listFiles(playerInfoFilter)[0];
                players.add(Parser.readPlayerInfo(playerInfo.getPath()));
            } catch (FileNotFoundException e) {
                errors.add("Folder " + playerFolder.getName() + " is missing info.txt file");
            } catch (ParseException e) {
                errors.add("Error when parsing info.txt form folder " + playerFolder.getName() + " Error: " + e.getMessage());
            }
        }

        return players;
    }

    public static String getProgramLocation() throws IOException {
        return new File(".").getCanonicalPath();
    }

    public static String getPlayersFolderLocation() throws IOException {
        String path = getProgramLocation() + "\\" + PLAYERS_FOLDER_NAME;
        File file = new File(path);

        if (file.exists() && file.isDirectory())
            return path;
        else throw new IOException("Unable to locate players data folder");
    }
}
