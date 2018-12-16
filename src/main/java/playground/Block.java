package playground;

import javafx.util.Pair;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class tha represents 2x1 block
 */
public class Block {
    private final Pair<Part, Part> parts;

    /**
     * Creat block from coordinates
     * @param y1 First tile of block
     * @param x1 First tile of block
     * @param y2 Second tile of block
     * @param x2 Second tile of block
     */
    public Block(int y1, int x1, int y2, int x2) {
        parts = new Pair<>(new Part(x1, y1), new Part(x2, y2));
    }

    /**
     * Create block from it's string representation
     * @param block Block as a string "{x1;y1},{x2;y2}"
     */
    public Block(String block) throws IllegalArgumentException {
        Pattern regex = Pattern.compile("\\{(\\d+);(\\d+)},\\{(\\d+);(\\d+)}");
        Matcher matcher = regex.matcher(block);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid block format");
        }

        int x1 = Integer.parseInt(matcher.group(1));
        int y1 = Integer.parseInt(matcher.group(2));
        int x2 = Integer.parseInt(matcher.group(3));
        int y2 = Integer.parseInt(matcher.group(4));

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
