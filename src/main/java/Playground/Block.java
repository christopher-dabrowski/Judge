package Playground;

import lombok.Getter;
import javafx.util.Pair;

/**
 * Class tha represents 2x1 block
 */
public class Block {
    private final Pair<Part, Part> parts;

    public Block(int y1, int x1, int y2, int x2) {
        parts = new Pair<>(new Part(x1, y1), new Part(x2, y2));
    }

    @Override
    public String toString() {
        return String.format("%s,%s", parts.getKey(), parts.getValue());
    }

    private class Part {
        @Getter
        private final int x;
        @Getter
        private final int y;

        Part(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("{%d;%d}", x, y);
        }
    }
}
