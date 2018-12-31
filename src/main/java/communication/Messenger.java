package communication;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mainlogic.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class Messenger {
    private ExtendedPlayer extendedPlayer1;
    private ExtendedPlayer extendedPlayer2;

    private boolean isDelivered = false;
    @Getter
    private String answer;
    private boolean kill = false;
    private String toDeliver;
    private Player addressee;
    private boolean deliver = false;
    private long timeTaken;

    private Thread thread = new Thread(() -> {
        while (true) {
            if (kill) {
                return;
            }
            try {
                innerThreadSetup();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Thread.yield();
        }
    });

    private synchronized void innerThreadSetup() throws IOException {
        if (deliver) {
            answer = innerSend(toDeliver, addressee);
            isDelivered = true;
            deliver = false;
        }
    }

    public Messenger(Player one, Player two) {
        extendedPlayer1 = new ExtendedPlayer(one);
        extendedPlayer2 = new ExtendedPlayer(two);
    }

    private String innerSend(String n, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        //Precise time measuring
        long time = System.nanoTime();
        thisExtendedPlayer.playerPrintStream.println(n);
        String temporary = thisExtendedPlayer.bufferedReader.readLine();
        timeTaken = System.nanoTime() - time;
        return temporary;
    }

    public synchronized long timeOfLastAction() {
        long tmp = timeTaken;
        timeTaken = 0;
        return tmp;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public synchronized void send(String message, Player player) {
        toDeliver = message;
        addressee = player;
        isDelivered = false;
        deliver = true;
    }

    public void openCommunication(Player player) throws IOException {
        //TODO Make possible to distinguish which player had problems executing
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        thisExtendedPlayer.playerProcess = Runtime.getRuntime().exec(thisExtendedPlayer.player.getFullLunchCommand());
        thisExtendedPlayer.playerPrintStream = new PrintStream(thisExtendedPlayer.playerProcess.getOutputStream(), true);
        thisExtendedPlayer.playerOutputStream = new InputStreamReader(thisExtendedPlayer.playerProcess.getInputStream());
        thisExtendedPlayer.bufferedReader = new BufferedReader(thisExtendedPlayer.playerOutputStream);
        //Starting broker thread
        if (!thread.isAlive()) {
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void endCommunication() {
        extendedPlayer1.playerProcess.destroy();
        extendedPlayer2.playerProcess.destroy();
        try {
            extendedPlayer1.playerOutputStream.close();
            extendedPlayer1.playerPrintStream.close();
            extendedPlayer2.playerOutputStream.close();
            extendedPlayer2.playerPrintStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        extendedPlayer1 = null;
        extendedPlayer2 = null;
        kill = true;
        thread = null;
    }

    @RequiredArgsConstructor
    private class ExtendedPlayer {

        @NonNull
        private Player player;
        private Process playerProcess;
        private PrintStream playerPrintStream;
        private InputStreamReader playerOutputStream;
        private BufferedReader bufferedReader;
    }
}