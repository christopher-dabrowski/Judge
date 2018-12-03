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

    private boolean isDelivered = false;
    @Getter
    private String answer;
    private boolean kill = false;
    private String toDeliver;
    private Player addressee;
    private boolean deliver = false;
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (kill) {
                    return;
                }
                try {
                    innerThreadSetup();
                } catch (IOException e) {
                    return;
                }
                Thread.yield();
                ;
            }
        }
    });

    //TODO Consider using one Messenger for each player instance
    public Messenger(Player one, Player two) {
        extendedPlayer1 = new ExtendedPlayer(one);
        extendedPlayer2 = new ExtendedPlayer(two);
    }

    synchronized public boolean isDelivered() {
        return isDelivered;
    }

    private synchronized void innerThreadSetup() throws IOException {
        if (deliver) {
            answer = innerSend(toDeliver, addressee);
            isDelivered = true;
            deliver = false;
        }
    }

    public void openCommunication() throws IOException {
        //TODO Make possible to distinguish which player had problems executing
        extendedPlayer1.playerProcess = Runtime.getRuntime().exec(extendedPlayer1.player.getLunchCommand());
        extendedPlayer1.playerPrintStream = new PrintStream(extendedPlayer1.playerProcess.getOutputStream(), true);
        extendedPlayer1.playerOutputStream = new InputStreamReader(extendedPlayer1.playerProcess.getInputStream());
        //TODO I don't like this. It should be simplified, maybe put in player?
        extendedPlayer2.playerProcess = Runtime.getRuntime().exec(extendedPlayer2.player.getLunchCommand());
        extendedPlayer2.playerPrintStream = new PrintStream(extendedPlayer2.playerProcess.getOutputStream(), true);
        extendedPlayer2.playerOutputStream = new InputStreamReader(extendedPlayer2.playerProcess.getInputStream());

        //Starting broker thread
        thread.setDaemon(true);
        System.out.println("Przed wystartowaniem wątku");
        thread.start();
        System.out.println("Po odpaleniu wątku");
        System.out.println(thread.isAlive());
    }

    public synchronized void send(String message, Player player) {
        toDeliver = message;
        addressee = player;
        isDelivered = false;
        deliver = true;
    }

    private String innerSend(String n, Player player) throws IOException {
        ExtendedPlayer thisExtendedPlayer = player == extendedPlayer1.player ? extendedPlayer1 : extendedPlayer2;
        thisExtendedPlayer.playerPrintStream.println(n);
        char[] answer = new char[4];
        if (thisExtendedPlayer.playerOutputStream.read(answer, 0, answer.length) != answer.length)
            throw new IOException();
        return answer[0] + "" + answer[1];
    }

    public void endCommunication() {
        extendedPlayer1.playerProcess.destroy();
        extendedPlayer2.playerProcess.destroy();
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
    }
}