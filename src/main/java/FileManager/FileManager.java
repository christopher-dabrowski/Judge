package FileManager;

import MainLogic.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private static String PLAYERS_FOLDER_NAME = "players";

    //ToDo write importPlayers()
    public static Player[] importPlayers() {
        throw new NotImplementedException();
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
