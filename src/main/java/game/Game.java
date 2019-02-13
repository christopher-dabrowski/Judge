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
    private final long waitTime = 700; //ms

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

    private GameResult innerPlay() throws IOException{
        //TODO find a way to avoid mistaking players
        //TODO make this function smaller
        // Attempting to refactor code
        Player[] actualPlayers = {playerOne, playerTwo};
        int index = 0;
        //
        long timeTaken;

        //Send basic intel
        for (int i = 0; i < 2; i++) {
            timeTaken = oneMove(String.valueOf(playground.getSize()), actualPlayers[index]);
            index++;
            index %= 2; // index now points to the next player
            if (timeTaken > maxWait) return new GameResult(actualPlayers[index], Setteling.TIMEOUT);
            if (isWrong(messenger.getAnswer(), MessageType.INFORMATION)) {
                logger.logCommunicationState(false);
                return new GameResult(actualPlayers[index], Setteling.CLASSIC);
            }
            logger.logCommunicationState(true);
        }

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

        timeTaken = oneMove("START", playerOne);
            String answer = messenger.getAnswer();
            if (timeTaken > maxWait) return new GameResult(playerTwo, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE)) {
                logger.logPlayerError();
                return new GameResult(playerTwo, Setteling.CLASSIC);
            }
            logger.logMove(new Block(answer));
        while (true) {
            timeTaken = oneMove(answer, playerTwo);
            answer = messenger.getAnswer();
            if (timeTaken > maxWait) return new GameResult(playerOne, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE)) {
                logger.logPlayerError();
                return new GameResult(playerOne, Setteling.CLASSIC);
            }
            if (playground.isFull())
                return new GameResult(playerTwo, Setteling.CLASSIC);
            logger.logMove( new Block(answer));

            timeTaken = oneMove(answer, playerOne);
            answer = messenger.getAnswer();
            if (timeTaken > maxWait) return new GameResult(playerTwo, Setteling.TIMEOUT);
            if (isWrong(answer, MessageType.MOVE)) {
                logger.logPlayerError();
                return new GameResult(playerTwo, Setteling.CLASSIC);
            }
            if (playground.isFull())
                return new GameResult(playerOne, Setteling.CLASSIC);
            logger.logMove(new Block(answer));

        }
    }

    public GameResult play() {
        messenger = new Messenger(playerOne, playerTwo);
        try {
            messenger.openCommunication(playerOne);
        } catch (IOException e) {
            return new GameResult(playerTwo, Setteling.TIMEOUT);
        }
        try {
            messenger.openCommunication(playerTwo);
        } catch (IOException e) {
            return new GameResult(playerOne, Setteling.TIMEOUT);
        }
        try {
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
    private long oneMove(String message, Player player) {
        long timeTaken = 0;
        long startTime = System.currentTimeMillis();
        messenger.send(message, player);
        Thread.yield();
        while (!messenger.isDelivered()) {
            Thread.yield();
            if (timeTaken > waitTime) {
                return timeTaken * 1000 * 1000;
            }
            timeTaken = System.currentTimeMillis() - startTime;
            Thread.yield();
        }
        return messenger.timeOfLastAction();
    }

    /*
    TODO consider making it case insensitive
    */
    private boolean isWrong(String answer, MessageType messageType) {
        switch (messageType) {
            case MOVE:
                String[] tokens = answer.split("[{},;]+");
                if (tokens.length != 5) return true;
                return !playground.take(Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4]));
            case INFORMATION:
                return answer == null || !answer.toLowerCase().equals("ok");
        }
        return false;
    }
}