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

    @Override
    public String toString() {
        String separation = "       |       ";
        return String.format("%10s%s%10s%s%4s%s%9s", player.getAlias(), separation, player.getIndexNumber(), separation, wins, separation, knockouts);
    }
}
