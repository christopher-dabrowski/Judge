package Parser;

import MainLogic.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Parser {
    public static Player readPlayerInfo(String fileName) throws FileNotFoundException, Exception {

        Scanner input = new Scanner(new FileReader(fileName));
        String alias, name, surname, lunchCommand;

        if (input.hasNext())
            alias = input.nextLine();
        else throw new Exception("Not enough player information");

        if (input.hasNext()) {
            name = input.next();
            if (!name.matches("\\p{L}+")) throw new Exception("Invalid player name");
        } else throw new Exception("Not enough player information");

        if (input.hasNext()) {
            surname = input.next();
            if (!surname.matches("\\p{L}+")) throw new Exception("Invalid player surname");
        } else throw new Exception("Not enough player information");

        //Finish line with name and surname
        if (input.hasNext()) {
            input.nextLine();
        } else throw new Exception("Not enough player information");

        if (input.hasNext()) {
            lunchCommand = input.nextLine();
            if (lunchCommand.length() <= 0) throw new Exception("Invalid lunch command");
        } else throw new Exception("Not enough player information");

        input.close();

        return new Player(alias, name, surname, lunchCommand);
    }
}
