package Parser;

import MainLogic.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Parser {
    public static Player readPlayerInfo(String fileName) throws FileNotFoundException, Exception {

        Scanner input = new Scanner(new FileReader(fileName));
//        while (input.hasNext()) {
//            System.out.println(input.next());
//        }
        //ToDo Check if file is corect ex. use regex
        String alias, name, surname, lunchCommand;
        if (input.hasNext())
            alias = input.next();
        else throw new Exception("Not enough player information");
        if (input.hasNext())
            name = input.next();
        else throw new Exception("Not enough player information");
        if (input.hasNext())
            surname = input.next();
        else throw new Exception("Not enough player information");
        if (input.hasNext())
            lunchCommand = input.next();
        else throw new Exception("Not enough player information");

        input.close();

        //throw new NotImplementedException();
        return new Player(alias, name, surname, lunchCommand);
    }
}
