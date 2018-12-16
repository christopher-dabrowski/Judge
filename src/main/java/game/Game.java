package game;

import communication.Messenger;
import logger.Logger;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mainlogic.Player;
import playground.Block;
import playground.Playground;

import java.io.IOException;

@RequiredArgsConstructor
public class Game {
    private final long maxWait = 500 * 1000 * 1000;//nm
    //    TODO get back to 800
    private final long waitTime = 800 * 100; //ms

    @NonNull
    @Getter
    private Player playerOne;
    @NonNull
    @Getter
    private Player playerTwo;
    @NonNull
    @Getter
    private Playground playground;

    private Logger logger;

    private Messenger messenger;

    //TODO add option of time out
    //TODO add logging moves
    //TODO consider caching the answer
    private GameResult innerPlay() throws IOException{
        //TODO find a way to avoid mistaking players
        //TODO make this function smaller
        //Send basic intel
        long timeTaken;
        timeTaken = oneMove(String.valueOf(playground.getSize()), playerOne);
        if (timeTaken > maxWait) return new GameResult(playerTwo, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION)) {
            logger.logCommunicationState(false);
            return new GameResult(playerTwo, Setteling.CLASSIC);
        }

        logger.logCommunicationState(true);

        timeTaken = oneMove(String.valueOf(playground.getSize()), playerTwo);
        if (timeTaken > maxWait) return new GameResult(playerOne, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION)) {
            logger.logCommunicationState(false); //Second made mistake
            return new GameResult(playerOne, Setteling.CLASSIC);
        }

        logger.logCommunicationState(true);

        //Send obstacles
        timeTaken = oneMove(playground.printObstacles(), playerOne);
        if (timeTaken > maxWait) return new GameResult(playerTwo, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION)) {
            logger.logCommunicationState(false);
            return new GameResult(playerTwo, Setteling.CLASSIC);
        }

        logger.logCommunicationState(true);

        timeTaken = oneMove(playground.printObstacles(), playerTwo);
        if (timeTaken > maxWait) return new GameResult(playerOne, Setteling.TIMEOUT);
        if (isWrong(messenger.getAnswer(), MessageType.INFORMATION)) {
            logger.logCommunicationState(false);
            return new GameResult(playerOne, Setteling.CLASSIC);
        }

        logger.logCommunicationState(true);
        //Start game
        while (true) {
            timeTaken = oneMove("START", playerOne);
            String answer = messenger.getAnswer();
            if (timeTaken > maxWait) return new GameResult(playerTwo, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE)) {
                logger.logPlayerError();
                return new GameResult(playerTwo, Setteling.CLASSIC);
            }
            logger.logMove(new Block(answer));

            timeTaken = oneMove(answer, playerTwo);
            answer = messenger.getAnswer();
            if (timeTaken > maxWait) return new GameResult(playerOne, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE)) {
                logger.logPlayerError();
                return new GameResult(playerOne, Setteling.CLASSIC);
            }
            logger.logMove( new Block(answer));
        }
    }

    public GameResult play() {
        messenger = new Messenger(playerOne, playerTwo);
        try {
            messenger.openCommunication();
            logger = new Logger(this);
            GameResult tmp = this.innerPlay();
            logger.close();
            messenger.endCommunication();
            return tmp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    TODO consider increasing time measuring precision
    TODO find better fitting name
    */
    private long oneMove(String message, Player player) {
        long timeTaken = 0;
        long startTime = System.currentTimeMillis();
        messenger.send(message, player);
        Thread.yield();
        while (!messenger.isDelivered()) {
            Thread.yield();
            if (timeTaken > waitTime) {
                return timeTaken;
            }
            timeTaken = System.currentTimeMillis() - startTime;
            Thread.yield();
        }
        return messenger.timeOfLastAction();
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