package MainLogic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class PlayerTest {

    private Player player;
    private String indexNumber;
    private String alias;
    private String name;
    private String surname;
    private String lunchCommand;

    @Before
    public void setUp() {
        indexNumber = "123456";
        alias = "Jee";
        name = "Jan";
        surname = "Kowalski";
        lunchCommand = "super_program.exe";

        player = new Player(indexNumber, alias, name, surname, lunchCommand);
    }

    @Test
    public void getAlias() {
        if (!player.getAlias().equals(alias)) fail();
    }

    @Test
    public void getName() {
        if (!player.getName().equals(name)) fail();
    }

    @Test
    public void getSurname() {
        if (!player.getSurname().equals(surname)) fail();
    }

    @Test
    public void getLunchCommand() {
        if (!player.getLunchCommand().equals(lunchCommand)) fail();
    }

    @Test
    public void testToString() {
        if (!player.toString().equals("" + alias + " " + name + " " + surname + " " + lunchCommand))
            fail();
    }
}