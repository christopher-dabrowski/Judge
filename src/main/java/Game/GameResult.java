package Game;

import MainLogic.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameResult {
    private Player player;
    private Setteling setteling;
}
