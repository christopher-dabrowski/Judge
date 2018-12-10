package Logger;

import FileManager.FileManager;
import Game.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class Logger {
    private static String LOGS_FOLDER_NAME = "game_logs";

    private final BufferedWriter output;
    private final String fileName; //<Player1>VS<Player2>.log

    public Logger(final Game game) throws IOException {
        File logsFolder = new File(FileManager.getProgramLocation() + "/game_logs");

        if (!logsFolder.exists())
            logsFolder.mkdir();

        //fileName = String.format("%sVS%s.log", game.)
    }
}
