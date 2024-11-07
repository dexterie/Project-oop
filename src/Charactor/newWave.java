package Charactor;

import display.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class newWave extends Wave {

    public newWave(int x, int y, int speed, JPanel panel,int level) {
        super(x, y - 150, speed, panel,level);
    }
    @Override
    public BufferedImage getImage() {
        try {
            return ImageIO.read(new File("img/arrow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void applyEffect(Sheep sheep, Game game) {
        sheep.health -= 20;
        game.countdownTime -= 5;
    }
}