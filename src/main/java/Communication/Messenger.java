package Communication;

import MainLogic.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class Messenger {
    private ExtendedPlayer extendedPlayer1;
    private ExtendedPlayer extendedPlayer2;

    @Getter
    private long timeOfLastMove;

    public Messenger(Player one, Player two) {
        extendedPlayer1 = new ExtendedPlayer(one);
        extendedPlayer2 = new ExtendedPlayer(two);
    }

    public void openCommunication() throws IOException {
        Runtime runTime = Runtime.getRuntime();
        //TODO Make possible to distinguish which player had problems executing
        extendedPlayer1.playerProcess = runTime.exec(extendedPlayer1.player.getLunchCommand());
        extendedPlayer1.playerPrintStream = new PrintStream(extendedPlayer1.playerProcess.getOutputStream(), true);
        extendedPlayer1.playerOutputStream = new InputStreamReader(extendedPlayer1.playerProcess.getInputStream());
        //TODO I don't like this. It should be simplified, maybe put in player?
        extendedPlayer2.playerProcess = runTime.exec(extendedPlayer2.player.getLunchCommand());
        extendedPlayer2.playerPrintStream = new PrintStream(extendedPlayer2.playerProcess.getOutputStream(), true);
        extendedPlayer2.playerOutputStream = new InputStreamReader(extendedPlayer2.playerProcess.getInputStream());
    }

    //    TODO Making it work
    public long sendPlaygroundSizePlayer(int n, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        long start = System.currentTimeMillis();
        thisExtendedPlayer.playerPrintStream.print(n);
        thisExtendedPlayer.playerPrintStream.flush();
        char[] toProcess = new char[2];
        thisExtendedPlayer.playerOutputStream.read(toProcess, 0, toProcess.length);
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println(toProcess[0] + "  " + toProcess[1]);
        if (!(toProcess[0] == 'o' && toProcess[1] == 'k'))
            throw new IOException("Answer was incorrect");
        timeOfLastMove = timeTaken;
        return timeTaken;
    }

    public long sendObstaclesPlayer1(long[] obstacles, Player player) {
        return 0;
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

