package gamequeuing;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mainlogic.Player;

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
