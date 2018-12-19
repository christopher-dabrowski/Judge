package playground;

import lombok.Getter;

public class Tile {
    @Getter
    private boolean taken = false;

    public void take() {
        taken = true;
    }

    public Tile clone() {
        Tile clone = new Tile();
        clone.taken = this.taken;
        return clone;
    }

}
