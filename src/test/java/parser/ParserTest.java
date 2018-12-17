package parser;

import mainlogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.fail;

public class ParserTest {
    private static String tempFolderName = "TEMP";
    private static String tempPlayerFolderName = tempFolderName + "/" + "123456";
    private static String temFileName = tempPlayerFolderName + "/" + "info.txt"; //Folder name is players index number

    @Before
    public void setUp() /*throws Exception*/ {
        new File(tempFolderName).mkdir();
        new File(tempPlayerFolderName).mkdir();

        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println("fajny_program.exe");
            writer.close();
        } catch (FileNotFoundException exception) {
            fail();
        } catch (UnsupportedEncodingException exception) {
            fail();
        }
    }

    @Test
    public void readPlayerInfo() {
        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        //if (player == null) fail();

        //ToDo Use variables
        if (!player.toString().equals("Kserkses Aleksander Dobrowolski fajny_program.exe")) fail();
    }

    @After
    public void tearDown() {
        File tempFile = new File(temFileName);
        if (!tempFile.delete()) fail(); //Wasn't able to delete file

        File tempPlayerFolder = new File(tempPlayerFolderName);
        if (!tempPlayerFolder.delete());

        File tempFolder = new File(tempFolderName);
        if (!tempFolder.delete()) fail(); //Wasn't able to delete folder
    }
}