package parser;

import filemanager.FileManager;
import mainlogic.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

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
            new File(temFileName).createNewFile();
        }
        catch (IOException e) {
            fail();
        }

    }

    @Test
    public void readPlayerInfo() {
        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println("fajny_program.exe");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            fail();
        }

        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        //if (player == null) fail();

        if (!player.toString().equals("Kserkses Aleksander Dobrowolski fajny_program.exe")) fail();
    }

    @Test
    public void appendJavaPath() throws IOException {
        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println("java -jar proGram.jar");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            fail();
        }

        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        if (!player.getFullLunchCommand().matches("java -jar [A-Z]:\\\\.*\\\\\\d{6}\\\\.*.jar"))
            fail();
    }

    @Test
    public void javaPathAlreadyProvided() throws IOException {
        String initialCommand = "java -jar D:\\folder\\proGram.jar";

        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println(initialCommand);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            fail();
        }

        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        if (!player.getFullLunchCommand().equals(initialCommand)) //Path shouldn't be changed
            fail();
    }

    @Test
    public void appendExePath() {
        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println("gamer.exe");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            fail();
        }

        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        if (!player.getFullLunchCommand().matches("[A-Z]:\\\\.*\\\\\\d{6}\\\\.*.exe"))
            fail();
    }

    @Test
    public void edePathAlreadyProvided() {
        String initialPath = "F:\\folder1\\folder22\\gamer.exe";

        try {
            PrintWriter writer = new PrintWriter(temFileName, "UTF-8");
            writer.println("Kserkses");
            writer.println("Aleksander Dobrowolski");
            writer.println(initialPath);
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            fail();
        }

        Player player = null;

        try {
            player = Parser.readPlayerInfo(temFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        if (!player.getFullLunchCommand().equals(initialPath))
            fail();
    }

    @After
    public void tearDown() {
        File tempFile = new File(temFileName);
        if (!tempFile.delete()) //Wasn't able to delete file
            fail();

        File tempPlayerFolder = new File(tempPlayerFolderName);
        if (!tempPlayerFolder.delete())
            fail();

        File tempFolder = new File(tempFolderName);
        if (!tempFolder.delete())
            fail();
    }

    @Test
    public void isJavaProgram() {
        if (!Parser.isJavaProgram("java -jar programName.jar"))
            fail();
        if (Parser.isJavaProgram("steam.exe"))
            fail();
    }

    @Test
    public void isExeProgram() {
        if (Parser.isExeProgram("java -jar programName.jar"))
            fail();
        if (!Parser.isExeProgram("steam.exe"))
            fail();
    }
}