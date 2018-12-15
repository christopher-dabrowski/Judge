package playground;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void toStringTest() {
        Block block = new Block(8,7,7,9);
        String expected = "{7;8},{9;7}";

        if (!expected.equals(block.toString()))
            fail();

        //System.out.println(block.toString());
    }

    @Test
    public void BlockConstructFromString() {
        Block block = new Block("{54;11},{11;72}");

        String expected = "{54;11},{11;72}";
        if (!expected.equals(block.toString()))
            fail();

        try {
            new Block("{12,24};{22;12}"); //Colon and semicolon swapped
            fail("Expected exception to be thrown");
        }
        catch (IllegalArgumentException e) {
        }

        //System.out.println(block.toString());
    }
}