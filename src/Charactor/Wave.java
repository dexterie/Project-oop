package Charactor;

import display.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Wave {
	    public boolean hasCollided = false;
		public int speed;
		public int x;
		public int y;
		Timer timeMove;
		public int level;

		public Wave(int x,int y,int speed,JPanel page,int level) {
			this.x = x;
			this.y = y;
			this.speed = speed;
			this.move(page);
			this.level = level;
		}

	    public void applyEffect(Sheep sheep, Game game) {
		  sheep.health -= 20;
		}
		
		public void move(JPanel page) {
				 this.timeMove = new Timer(speed,new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							if(x<=0) {
								x = (int) (1000+(300+Math.random()*1000));
							}
							x -= 30;
							page.repaint();
					}
				});
				 this.timeMove.start();
		}
		
		public BufferedImage getImage() {
			BufferedImage image = null;
			try {
				if(level == 1){
					image = ImageIO.read(new File("img\\tree.png"));
					return image;
				} else if (level == 2) {
					image = ImageIO.read(new File("img\\cactus.png"));
					return image;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
}
