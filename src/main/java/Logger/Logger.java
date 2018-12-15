package Logger;

import FileManager.FileManager;
import Game.Game;
import Playground.Block;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for logging game between two players while it's being played
 *
 * Example log file:
 * File name: 123456VS987654.log
 * File content:
 * Board size: 15
 * Obstacles: {1;2},{3;4},{5,7}
 * Player 1: ToBolekxx
 * Player 2: SuperGracz232
 * Moves:
 * {7;8},{7;8}
 * {0,0},{1;0}
 */
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

    public void logMove(Block blockPlaced) throws IOException {
        output.write(blockPlaced.toString() + "\n");
    }

    public void logMove(int y1, int x1, int y2, int x2) throws IOException {
        Block blockPlaced = new Block(y1, x1, y2, x2);

        output.write(blockPlaced.toString() + "\n");
    }

    /**
     * Closes file stream
     */
    public void close() throws IOException {
        output.close();
    }
}
