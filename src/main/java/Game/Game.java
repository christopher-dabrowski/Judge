package Game;

import Communication.Messenger;
import MainLogic.Player;
import Playground.Playground;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class Game {
    @NonNull
    private Player playerOne;
    @NonNull
    private Player playerTwo;
    @NonNull
    private Playground playground;

    private Messenger messenger;

    //TODO add option of time out
    //TODO add logging moves
    //TODO consider caching the answer
    //TODO add endCommunication method in order to save CPU
    public GameResult play() {
        messenger = new Messenger(playerOne, playerTwo);
        long timeTaken;
        try {
            messenger.openCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO find a way to avoid mistaking players
        //Send basic intel about
        timeTaken = oneMove(String.valueOf(playground.getSize()), playerOne);
        if (isWrong(messenger.getAnswer(), timeTaken, MessageType.INFORMATION))
            return new GameResult(playerTwo, Setteling.CLASSIC);

        timeTaken = oneMove(String.valueOf(playground.getSize()), playerTwo);
        if (isWrong(messenger.getAnswer(), timeTaken, MessageType.INFORMATION))
            return new GameResult(playerOne, Setteling.CLASSIC);
//Send obstacles
        timeTaken = oneMove(playground.printObstacles(), playerOne);
        if (isWrong(messenger.getAnswer(), timeTaken, MessageType.INFORMATION))
            return new GameResult(playerTwo, Setteling.CLASSIC);

        timeTaken = oneMove(playground.printObstacles(), playerTwo);
        if (isWrong(messenger.getAnswer(), timeTaken, MessageType.INFORMATION))
            return new GameResult(playerOne, Setteling.CLASSIC);
//Start game
        while (true) {
            timeTaken = oneMove("START", playerOne);
            String answer = messenger.getAnswer();
            if (isWrong(answer, timeTaken, MessageType.MOVE))
                return new GameResult(playerTwo, Setteling.CLASSIC);

            timeTaken = oneMove(answer, playerTwo);
            answer = messenger.getAnswer();
            if (isWrong(answer, timeTaken, MessageType.MOVE))
                return new GameResult(playerOne, Setteling.CLASSIC);
        }
    }

    /*
    TODO consider increasing time measuring precision
    TODO find better fitting name
    */
    private long oneMove(String message, Player player) {
        long timeTaken = 0, startTime = System.currentTimeMillis();
        messenger.send(message, player);
        Thread.yield();
        while (!messenger.isDelivered()) {
            Thread.yield();
            if (timeTaken > 500) { //Max time for one move
                return timeTaken;
            }
            timeTaken = System.currentTimeMillis() - startTime;
            Thread.yield();
        }
        return timeTaken;
    }

    /*
    TODO consider making it case insensitive
    TODO make distinguishable case when time out
    */
    private boolean isWrong(String answer, long timeTaken, MessageType messageType) {
        if (timeTaken > 500) return false; //Hard coded value of maximum response time
        switch (messageType) {
            case MOVE:
                String[] tokens = answer.split("[{},;]");
                if (tokens.length != 4) return false;
                return playground.take(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]));
            case INFORMATION:
                return answer.equals("ok");
        }
        return false;
    }
}