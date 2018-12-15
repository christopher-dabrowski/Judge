package game;

import communication.Messenger;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mainlogic.Player;
import playground.Playground;

import java.io.IOException;

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
    private GameResult innerPlay() {
        long timeTaken;
        //TODO find a way to avoid mistaking players
        //TODO make this function smaller
        //Send basic intel
        timeTaken = oneMove(String.valueOf(playground.getSize()), playerOne);
//        if (timeTaken > 500) return new GameResult(playerTwo, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION))
            return new GameResult(playerTwo, Setteling.CLASSIC);

        timeTaken = oneMove(String.valueOf(playground.getSize()), playerTwo);
//        if (timeTaken > 500) return new GameResult(playerOne, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION))
            return new GameResult(playerOne, Setteling.CLASSIC);

        //Send obstacles
        timeTaken = oneMove(playground.printObstacles(), playerOne);
//        if (timeTaken > 500) return new GameResult(playerTwo, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION))
            return new GameResult(playerTwo, Setteling.CLASSIC);

        timeTaken = oneMove(playground.printObstacles(), playerTwo);
//        if (timeTaken > 500) return new GameResult(playerOne, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION))
            return new GameResult(playerOne, Setteling.CLASSIC);
        //Start game
        while (true) {
            timeTaken = oneMove("START", playerOne);
            String answer = messenger.getAnswer();
//            if (timeTaken > 500) return new GameResult(playerTwo, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE))
                return new GameResult(playerTwo, Setteling.CLASSIC);

            timeTaken = oneMove(answer, playerTwo);
            answer = messenger.getAnswer();
//            if (timeTaken > 500) return new GameResult(playerOne, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE))
                return new GameResult(playerOne, Setteling.CLASSIC);
        }
    }

    public GameResult play() {
        messenger = new Messenger(playerOne, playerTwo);
        try {
            messenger.openCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameResult tmp = this.innerPlay();
        messenger.endCommunication();
        return tmp;
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
//            if (timeTaken > 500) { //Max time for one move
//                return timeTaken;
//            }
            timeTaken = System.currentTimeMillis() - startTime;
            Thread.yield();
        }
        return timeTaken;
    }

    /*
    TODO consider making it case insensitive
    TODO make distinguishable case when time out
    */
    private boolean isWrong(String answer, MessageType messageType) {
        switch (messageType) {
            case MOVE:
                String[] tokens = answer.split("[{},;]+");
                if (tokens.length != 5) return true;
                return !playground.take(Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4]));
            case INFORMATION:
                return !answer.equals("ok");
        }
        return false;
    }
}
