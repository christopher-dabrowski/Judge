package game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mainlogic.Player;

@Getter
@AllArgsConstructor
public class GameResult {
    private Player player;
    private Setteling setteling;
}
