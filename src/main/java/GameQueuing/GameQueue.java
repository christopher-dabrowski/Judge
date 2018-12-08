package GameQueuing;

import MainLogic.Player;
import Playground.Playground;
import game.Game;
import game.GameResult;
import game.Setteling;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
public class GameQueue {
    @NonNull
    private List<Player> playerList;

    @NonNull

    public Map<Player, GameStatistics> morituriTeSalutant() {
        Map<Player, GameStatistics> answer = new HashMap<>(30);
        Random rng = new Random();
        for (int i = 0; i < playerList.size(); i++) {
            for (int j = i + 1; j < playerList.size(); j++) {
                Playground playground = new Playground();
                //TODO Consider moving this to playground constructor
                int size = rng.nextInt(51 - 13);
                size = size % 2 == 0 ? size + 1 : size;
                size += 13;
                playground.makeMap(size);
                playground.addObstacles();
                Playground clone = playground.clone();
                /*End of concerning code*/
                resolveOneGame(answer, i, j, playground);
                resolveOneGame(answer, j, i, clone);
            }
        }
        return answer;
    }

    private void resolveOneGame(Map<Player, GameStatistics> map, int first, int second, Playground playground) {
        Game game = new Game(playerList.get(first), playerList.get(second), playground);
        GameResult gameResult = game.play();
        map.putIfAbsent(gameResult.getPlayer(), new GameStatistics(gameResult.getPlayer()));
        addWinner(map, gameResult);
    }

    private void addWinner(Map<Player, GameStatistics> map, GameResult gameResult) {
        map.get(gameResult.getPlayer()).incrementWins();
        if (gameResult.getSetteling() != Setteling.TIMEOUT)
            map.get(gameResult.getPlayer()).incrementKnockouts();
    }

}
