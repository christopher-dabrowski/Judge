package filemanager;

import lombok.var;
import mainlogic.Player;
import parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class FileManager {
    private static String DEFAULT_PLAYERS_FOLDER_NAME = "players";

    /**
     * Imports players from default players folder
     *
     * @param errors Error messages from loading are appended here
     * @return Array of corectly loaded players
     *
     */
    public static ArrayList<Player> importPlayers(ArrayList<String> errors) {
        return importPlayers(DEFAULT_PLAYERS_FOLDER_NAME, errors);
    }

    /**
     * Imports players from players folder. Allows to specify players folder name
     *
     * @param playersFolderName Custom players folder name
     * @param errors            Error messages from loading are appended here
     * @return Array of corectly loaded players
     *
     */
    public static ArrayList<Player> importPlayers(String playersFolderName, ArrayList<String> errors) {
        ArrayList<Player> players = new ArrayList<>();

        FilenameFilter playerFolderFilter = (dir, name) -> name.matches("\\d{6}");
        FilenameFilter playerInfoFilter = (dir, name) -> name.equals("info.txt");

        File playersFolder = new File(playersFolderName);
        var listOfFolders = playersFolder.listFiles(playerFolderFilter);
        if (listOfFolders != null) {
            try {
                for (File playerFolder : listOfFolders) {
                    try {
                        var listOfFiles = playerFolder.listFiles(playerInfoFilter);
                        if (listOfFiles == null || listOfFiles.length != 1) //Brakuje pliku info.txt
                            throw new FileNotFoundException("Players folder missing info.txt");
                        File playerInfo = listOfFiles[0];
                        Player newPlayer = Parser.readPlayerInfo(playerInfo.getPath());

                        players.add(newPlayer);
                    } catch (FileNotFoundException e) {
                        errors.add("Folder " + playerFolder.getName() + " is missing info.txt file");
                    } catch (ParseException e) {
                        errors.add("Error when parsing info.txt form folder " + playerFolder.getName() + " Error: " + e.getMessage());
                    }
                }
            } catch (NullPointerException e) {
                //do sth
            }
        }
        return players;
    }

    public static String getProgramLocation() throws IOException {
        return new File(".").getCanonicalPath();
    }

    public static String getPlayersFolderLocation() throws IOException {
        String path = getProgramLocation() + "\\" + DEFAULT_PLAYERS_FOLDER_NAME;
        File file = new File(path);

        if (file.exists() && file.isDirectory())
            return path;
        else throw new IOException("Unable to locate players data folder");
    }
}
