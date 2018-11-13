package Parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.fail;

public class ParserTest {
    private static String temFileName = "TEMPinfo.txt";

    @Before
    public void setUp() /*throws Exception*/ {
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
        Parser.readPlayerInfo("testFile.txt");
    }

    @After
    public void tearDown() {
        File tempFile = new File(temFileName);

        if (!tempFile.delete()) fail(); //Wasn't able to delete file
    }
}