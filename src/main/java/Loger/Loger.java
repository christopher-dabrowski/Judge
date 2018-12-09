package Loger;

import FileManager.FileManager;

import java.io.File;
import java.io.IOException;

public class Loger {
    private static String LOGS_FOLDER_NAME = "game_logs";

    public Loger() throws IOException {
        File logsFolder = new File(FileManager.getProgramLocation() + "/game_logs");

        if (!logsFolder.exists())
            logsFolder.mkdir();
    }
}
