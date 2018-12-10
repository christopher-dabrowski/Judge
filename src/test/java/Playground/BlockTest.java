package Playground;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTest {

    @Test
    public void toStringTest() {
        Block block = new Block(8,7,7,9);

        System.out.println(block.toString());
    }
}