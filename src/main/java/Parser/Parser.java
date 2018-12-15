package Parser;

import MainLogic.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Scanner;

public class Parser {
    public static Player readPlayerInfo(String fileName) throws FileNotFoundException, ParseException {

        Scanner input = new Scanner(new FileReader(fileName));
        String indexNumber, alias, name, surname, lunchCommand;

        indexNumber = new File( new File(fileName).getParent() ).getName(); //Get name of parent of info.txt file

        if (input.hasNext())
            alias = input.nextLine();
        else throw new ParseException("No info found", 0);

        if (input.hasNext()) {
            name = input.next();
            if (!name.matches("\\p{L}+")) throw new ParseException("Invalid player name", 1);
        } else throw new ParseException("No player name found", 1);

        if (input.hasNext()) {
            surname = input.next();
            if (!surname.matches("\\p{L}+")) throw new ParseException("Invalid player surname", 2);
        } else throw new ParseException("No player surname found", 2);

        //Finish line with name and surname
        if (input.hasNext()) {
            input.nextLine();
        } else throw new ParseException("New line after surname expected", 2);

        if (input.hasNext()) {
            lunchCommand = input.nextLine();
            if (lunchCommand.length() <= 0) throw new ParseException("No lunch command", 3);
        } else throw new ParseException("No lunch command", 3);

        input.close();
        return new Player(indexNumber, alias, name, surname, lunchCommand);
    }
}
