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
    long timeTaken;

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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private String innerSend(String n, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        //Precise time measuring
        long time = System.nanoTime();
        thisExtendedPlayer.playerPrintStream.println(n);
        String temporary = thisExtendedPlayer.bufferedReader.readLine();
        timeTaken = System.nanoTime() - time;

//        StringBuilder stringBuilder = new StringBuilder(17);
//        for (char c : answer) {
//            if (c != 0 && c != '\r' && c != '\n')
//                stringBuilder.append(c);
//        }
//        System.out.println(stringBuilder.toString());
//        return stringBuilder.toString();
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

    public void openCommunication() throws IOException {
        //TODO Make possible to distinguish which player had problems executing
        extendedPlayer1.playerProcess = Runtime.getRuntime().exec(extendedPlayer1.player.getLunchCommand());
        extendedPlayer1.playerPrintStream = new PrintStream(extendedPlayer1.playerProcess.getOutputStream(), true);
        extendedPlayer1.playerOutputStream = new InputStreamReader(extendedPlayer1.playerProcess.getInputStream(), "US-ASCII");
        extendedPlayer1.bufferedReader = new BufferedReader(extendedPlayer1.playerOutputStream);

        extendedPlayer2.playerProcess = Runtime.getRuntime().exec(extendedPlayer2.player.getLunchCommand());
        extendedPlayer2.playerPrintStream = new PrintStream(extendedPlayer2.playerProcess.getOutputStream(), true);
        extendedPlayer2.playerOutputStream = new InputStreamReader(extendedPlayer2.playerProcess.getInputStream(), "US-ASCII");
        extendedPlayer2.bufferedReader = new BufferedReader(extendedPlayer2.playerOutputStream);

        //Starting broker thread
        thread.setDaemon(true);
        thread.start();
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