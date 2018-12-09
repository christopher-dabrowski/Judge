package playground;

import lombok.Getter;

public class Tile {
    @Getter
    private boolean taken = false;

    public void take() {
        taken = true;
    }

}
