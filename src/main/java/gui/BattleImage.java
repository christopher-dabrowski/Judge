package gui;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class BattleImage {
    //This is matrix that uses y x cordinates but image uses x y
    @Getter
    private BufferedImage bufferedImage;
    private Graphics2D graphics;
    private int size;
    private int blockWidth;
    private int blockHeight;
    private int spacingSize = 1;

    private Color background = Color.WHITE;
    private Color separationLines = Color.BLACK;

    public BattleImage(Dimension dimension, int size) {
        this.size = size;
        blockHeight = (dimension.height - (size + 1) * spacingSize) / size - 1;
        blockWidth = (dimension.width - (size + 1) * spacingSize) / size - 1;
        bufferedImage = new BufferedImage((blockWidth + spacingSize) * size + spacingSize, (blockHeight + spacingSize) * size + spacingSize, TYPE_INT_RGB);
        graphics = (Graphics2D) bufferedImage.getGraphics();
        fillSpace();
        drawSeparation();
    }

    public void take(int y, int x, Color color) {
        int positionX = (blockWidth + spacingSize) * x + spacingSize;
        int positionY = (blockHeight + spacingSize) * y + spacingSize;
        graphics.setColor(color);
        graphics.fillRect(positionX, positionY, blockWidth, blockHeight);
    }

    public void clear(int y, int x) {
        take(y, x, background);
    }

    private void drawSeparation() {
        //Drowing separation lines ( Y axis)
        graphics.setColor(separationLines);
        Stroke tmp = graphics.getStroke(); //In rder to set temporary stroke
        graphics.setStroke(new BasicStroke(spacingSize));
        for (int i = 0; i <= size; i++) {
            graphics.drawLine((blockWidth + spacingSize) * i, 0, (blockWidth + spacingSize) * i, bufferedImage.getHeight());
        }
        for (int i = 0; i <= size; i++) {
            graphics.drawLine(0, (blockHeight + spacingSize) * i, bufferedImage.getWidth(), (blockHeight + spacingSize) * i);
        }
        graphics.setStroke(tmp);
    }

    private void fillSpace() {
        //Filling space
        graphics.setColor(background);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
}
