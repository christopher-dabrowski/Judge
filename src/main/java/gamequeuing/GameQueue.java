package gamequeuing;

import game.Game;
import game.GameResult;
import game.Setteling;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mainlogic.Player;
import playground.Playground;

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
        for (Player player : playerList) {
            answer.put(player, new GameStatistics(player));
        }
        Random rng = new Random();
        for (int outer = 0; outer < playerList.size(); outer++) {
            for (int inner = outer + 1; inner < playerList.size(); inner++) {
                Playground playground = new Playground();
                //TODO Consider moving this to playground constructor
                int size = rng.nextInt(51 - 13);
                size = size % 2 == 0 ? size : size + 1;
                size += 13;
                playground.makeMap(size);
                playground.addObstacles();
                Playground clone = playground.clone();
                /*End of concerning code*/
                resolveOneGame(answer, outer, inner, playground);
                resolveOneGame(answer, inner, outer, clone);
            }
        }
        return answer;
    }

    private void resolveOneGame(Map<Player, GameStatistics> map, int first, int second, Playground playground) {
        Game game = new Game(playerList.get(first), playerList.get(second), playground);
        GameResult gameResult = game.play();
        addWinner(map, gameResult);
    }

    private void addWinner(Map<Player, GameStatistics> map, GameResult gameResult) {
        map.get(gameResult.getPlayer()).incrementWins();
        if (gameResult.getSetteling() != Setteling.TIMEOUT)
            map.get(gameResult.getPlayer()).incrementKnockouts();
    }

}
