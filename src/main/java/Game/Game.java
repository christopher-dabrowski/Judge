package Game;

import Communication.Messenger;
import MainLogic.Player;
import Playground.Playground;
import lombok.Getter;
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

    @Getter
    private Player winner;
    @Getter
    private Result result;

    //TODO ADD Loging moves
    public void start() {
        Messenger messenger = new Messenger(playerOne, playerTwo);
        try {
            messenger.openCommunication();
            if (messenger.sendPlaygroundSize(playground.getSize(), playerOne) > 500) {
                setWinner(playerTwo, Result.KONCKOUT);
                return;
            }
            if (messenger.sendPlaygroundSize(playground.getSize(), playerTwo) > 500) {
                setWinner(playerOne, Result.KONCKOUT);
                return;
            }

        } catch (IOException e) {

        }
        messenger.endCommunication();
    }

    private void setWinner(Player player, Result result) {
        winner = player;
        this.result = result;
    }
}
