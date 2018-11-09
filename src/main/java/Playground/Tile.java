package Playground;

import lombok.Getter;

public class Tile {
    @Getter
    private boolean taken = false;

    public void taken() {
        taken = true;
    }

}
