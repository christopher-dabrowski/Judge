package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BattleParser {
    private Scanner scanner;
    private int boardSize;

    public BattleParser(File file) throws FileNotFoundException {
        scanner = new Scanner(file);
    }

    public static void main(String[] args) {
        try {
            BattleParser battleParser = new BattleParser(new File("game_logs/123456VS987654.log"));
            System.out.println(battleParser.nextBoard());
            System.out.println(battleParser.nextObstacles());
            System.out.println(battleParser.nextPlayer());
            System.out.println(battleParser.nextPlayer());
            battleParser.skipInfo();
            System.out.println(Arrays.toString(battleParser.nextMove()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int nextBoard() {
        scanner.skip(".* ");
        boardSize = scanner.nextInt();
        return boardSize;
    }

    public List nextObstacles() {
        scanner.nextLine();
        Scanner obstaclesScanner = new Scanner(scanner.nextLine());
        obstaclesScanner.skip(".* ");
        obstaclesScanner.useDelimiter("[{};,]+");
        List<Integer> answer = new ArrayList<>(20);
        while (obstaclesScanner.hasNextInt())
            answer.add(obstaclesScanner.nextInt());
        return answer;
    }

    public String nextPlayer() {
        scanner.next();
        String answer = scanner.next("\".*\"");
        return answer;
    }

    public int[] nextMove() {
        scanner.useDelimiter("[{};,\\s]+");
        int[] answer = new int[4];
        for (int i = 0; i < 4; i++) {
            answer[i] = scanner.nextInt();
        }
        return answer;
    }

    public void skipInfo() {
        scanner.skip("[\\w\\s]+:\\s");
    }
}
