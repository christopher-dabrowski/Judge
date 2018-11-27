package Communication;

import MainLogic.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;


public class Messenger {
    private ExtendedPlayer extendedPlayer1;
    private ExtendedPlayer extendedPlayer2;

    @Getter
    private long timeOfLastMove;

    private void setUpExtendedPlayer(ExtendedPlayer extendedPlayer) throws IOException {
        extendedPlayer.playerProcess = Runtime.getRuntime().exec(extendedPlayer.player.getLunchCommand());
        extendedPlayer.playerPrintStream = new PrintStream(extendedPlayer.playerProcess.getOutputStream(), true);
        extendedPlayer.playerOutputStream = new InputStreamReader(extendedPlayer.playerProcess.getInputStream());
    }

    private boolean readOk(ExtendedPlayer extendedPlayer) throws IOException {
        char[] toProcess = new char[4];
        if (extendedPlayer.playerOutputStream.read(toProcess, 0, toProcess.length) != toProcess.length)
            throw new IOException();
        return !(toProcess[0] == 'o' && toProcess[1] == 'k');
    }

    public Messenger(Player one, Player two) {
        extendedPlayer1 = new ExtendedPlayer(one);
        extendedPlayer2 = new ExtendedPlayer(two);
    }

    public void openCommunication() throws IOException {
        //TODO Make possible to distinguish which player had problems executing
        setUpExtendedPlayer(extendedPlayer1);
        setUpExtendedPlayer(extendedPlayer2);
    }

    //    TODO Making it work
    public long sendPlaygroundSize(int n, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        long start = System.currentTimeMillis();
        thisExtendedPlayer.playerPrintStream.println(n);
        if (readOk(thisExtendedPlayer))
            throw new IOException("Answer was incorrect");
        long timeTaken = System.currentTimeMillis() - start;
        timeOfLastMove = timeTaken;
        return timeTaken;
    }

    // TODO Encapsulate obstacles in order to only use toString when sending communicates
    public long sendObstacles(long[] obstacles, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        long start = System.currentTimeMillis();
        //TODO
        thisExtendedPlayer.playerPrintStream.println(Arrays.toString(obstacles));
        char[] toProcess = new char[4];
        if (thisExtendedPlayer.playerOutputStream.read(toProcess, 0, toProcess.length) != toProcess.length)
            throw new IOException();

        timeOfLastMove = System.currentTimeMillis() - start;

        return System.currentTimeMillis() - start;
    }

    public void endCommunication() {
        extendedPlayer1.playerProcess.destroy();
        extendedPlayer2.playerProcess.destroy();
        extendedPlayer1 = null;
        extendedPlayer2 = null;
    }

    @RequiredArgsConstructor
    private class ExtendedPlayer {
        @NonNull
        private Player player;
        private Process playerProcess;
        private PrintStream playerPrintStream;
        private InputStreamReader playerOutputStream;
    }
}

