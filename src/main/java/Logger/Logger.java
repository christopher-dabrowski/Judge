package Logger;

import FileManager.FileManager;
import Game.Game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static String LOGS_FOLDER_NAME = "game_logs";

    private final BufferedWriter output;
    private final String fileName; //<Player1>VS<Player2>.log

    /**
     * Starts to log game
     * @param game Game used to extract players IDs and obstacles
     * @throws IOException
     */
    public Logger(final Game game) throws IOException {
        File logsFolder = new File(FileManager.getProgramLocation() + "/game_logs");

        if (!logsFolder.exists())
            logsFolder.mkdir();

        fileName = String.format("%sVS%s.log", game.getPlayerOne().getIndexNumber(), game.getPlayerTwo().getIndexNumber());

        output = new BufferedWriter(new FileWriter(logsFolder + "/" + fileName));

        printInitialInfo(game);
    }

    private void printInitialInfo(final Game game) throws IOException {
        output.write("Board size: " + game.getPlayground().getSize() + "\n");
        output.write("Obstacles: " + game.getPlayground().printObstacles() + "\n");

        output.write("Player 1: " + game.getPlayerOne().getAlias() + "\n");
        output.write("Player 2: " + game.getPlayerTwo().getAlias() + "\n");

        output.write("Moves:\n");
    }

    /**
     * Closes file stream
     */
    public void close() throws IOException {
        output.close();
    }
}
