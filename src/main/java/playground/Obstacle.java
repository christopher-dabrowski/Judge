package playground;

import lombok.Getter;

public class Obstacle {

    @Getter
    private final int x;
    @Getter
    private final int y;

    Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("{%d;%d}", y, x);
    }
}
