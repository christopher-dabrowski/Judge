package Game;

import Communication.Messenger;
import MainLogic.Player;
import Playground.Playground;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class Game {
    @NonNull
    private Player playerOne;
    @NonNull
    private Player playerTwo;
    @NonNull
    private Playground playground;

    //TODO add option of time out
    //TODO ADD Logging moves
    public Player play() {
        Messenger messenger = new Messenger(playerOne, playerTwo);
        try {
            messenger.openCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        long timeTaken = 0;
        messenger.send(String.valueOf(playground.getSize()), playerOne);
//        Wait for message to be delivered
        Thread.yield();
        while (!messenger.isDelivered()) {
            Thread.yield();
            if (timeTaken > 500) { //Max time for one move
                messenger.endCommunication();
                return playerTwo;
            }
            timeTaken = System.currentTimeMillis() - startTime;
            Thread.yield();
        }
        System.out.println(messenger.getAnswer());
        if (isOk(messenger.getAnswer()))
            return playerOne;
        else
            return playerTwo;
    }

    private boolean isOk(String message) {
        return message.equals("ok");
    }
}
