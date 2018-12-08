package GameQueuing;

import MainLogic.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameStatistics {

    @NonNull
    @Getter
    private Player player;
    @Getter
    private int wins;
    @Getter
    private int knockouts;

    //IncrementsWinsAndGames
    public void incrementWins() {
        wins++;
    }

    public void incrementKnockouts() {
        knockouts++;
    }
}
