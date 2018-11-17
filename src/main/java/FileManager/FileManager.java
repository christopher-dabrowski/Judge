package FileManager;

import MainLogic.Player;
import Parser.Parser;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    private static String PLAYERS_FOLDER_NAME = "players";

    //ToDo write importPlayers()
    public static ArrayList<Player> importPlayers() throws IOException {
        ArrayList<Player> players = new ArrayList<Player>();
        FilenameFilter playerInfoFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.equals("info.txt");
            }
        };

        File playersFolder = new File(getPlayersFolderLocation());
        for (File playerFolder : playersFolder.listFiles()) {
            //ToDo Check if folder name is correct
            //System.out.println(folder.toString());

            try {
                if (playerFolder.listFiles(playerInfoFilter).length != 1) //Brakuje pliku info.txt
                    throw new IOException("Players folder missing info.txt");

                File playerInfo = playerFolder.listFiles(playerInfoFilter)[0];
                //ToDo Test how it works!
                players.add(Parser.readPlayerInfo(playerInfo.getPath()));
            } catch (Exception e) {
                //ToDo handle error with loading player
                //Maybe return array with players that cosed errors
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
