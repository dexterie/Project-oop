package display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Charactor.*;
import Element.Element;
import event.Event;

public class Game extends JPanel implements KeyListener{

	private static final long serialVersionUID = 1L;
	public int countdownTime = 60;
	private long lastTimeUpdate = System.currentTimeMillis();
	private static int speed = 50,sheepSize = 50 ,waveHeight = 40;
	private static int base=400,xStart = 1000;
	private long point = 0,lastPress=0;
	private long scoreTarget = 300;
	public int level = 1;
	private Image backgroundImage;
	
	private Sheep sheep = new Sheep(100,base-50);
	static Display display;
//	------------------Wave SIze ----------------------------
	private Wave[] waveSet = makeWave(4);
//--------------------Cloud--------------------------------
	private Environment[] envSet = makeEnv(2,Environment.CLOUD);
	private Environment building = new Environment(xStart-100,base-150,this,Environment.BUILDING,4);
	
		public Game(){
		this.setBounds(0,0,1000,600);
		this.addKeyListener(this);
		this.setLayout(null);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	@Override
	public void paint(Graphics g) {
			try {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				this.drawBackground(g2);

				//---Stage---
				g2.setFont(Element.getFont(30));
				g2.setColor(Color.white);
				g2.drawString("Stage :" + level ,20,80);
				//---POINT----
				g2.setFont(Element.getFont(30));
				g2.setColor(Color.white);
				g2.drawString("Point : "+point +"/"+ scoreTarget,750,40);
				//---Time---
				if (System.currentTimeMillis() - lastTimeUpdate >= 1000) {
					countdownTime--;
					lastTimeUpdate = System.currentTimeMillis();
				}
				if(countdownTime == 0){
					display.endGame(this.point);
					return;
				}
				g2.setFont(Element.getFont(30));
				g2.setColor(Color.white);
				g2.drawString("Time : " +countdownTime ,750 ,80);
				//--- sheep --
				g2.setColor(Color.RED);
				drawDogHealth(g2);

				// --- Check Level Up ---
				checkLevelUp();

				//---HIT BOX---
				//g2.drawRect(sheep.x,sheep.y,sheepSize,sheepSize);
				g2.drawImage(sheep.getImage(),sheep.x,sheep.y,sheepSize*2,sheepSize*2, null);
				//----Wave----
				for(Wave item : waveSet) {
					drawWave(item,g2);
				}
				this.point+=1;

				if (point == scoreTarget) {
					display.showWinMenu(this.point);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public void checkLevelUp() {
		if (point >= scoreTarget) {
			level++;
			setLevel(level);
			point = 0;
		}
	}
	private void drawBackground(Graphics2D g2) throws IOException {
			try {
				if(level == 1){
					g2.drawImage(ImageIO.read(new File("img\\sky.png")),0,0,2000,1000, null);
					g2.drawImage(building.getImage(),building.x,building.y,500,200,null);
					g2.drawImage(ImageIO.read(new File("img\\dir.png")),0,base+10,2000,220, null);
					for(Environment item:envSet) {
						g2.drawImage(item.getImage(),item.x,item.y,250,160, null);
					}
				} else if(level == 2){
					g2.drawImage(ImageIO.read(new File("img\\sky1.png")),0,0,2000,1000, null);
					g2.drawImage(building.getImage(),building.x,building.y,500,200,null);
					g2.drawImage(ImageIO.read(new File("img\\dir2.png")),0,base+10,2000,220, null);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void drawDogHealth(Graphics2D g2) {
		try {
			g2.drawImage(ImageIO.read(new File("img\\heart.png")),10,20, 20,20,null);
			g2.setStroke(new BasicStroke(18.0f));
			g2.setColor(new Color(241, 98, 69));
			g2.drawLine(60, 30,60+sheep.health,30);
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(6.0f));
			g2.drawRect(50,20, 120,20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private Wave[] makeWave(int size) {
		Wave[] waveSet = new Wave[size];
		int far = 300;

		for (int i = 0; i < size; i++) {
			if (level == 2 && i % 2 == 0) {
				waveSet[i] = new newWave(xStart + far, base+150, speed, this, level);
			} else {
				waveSet[i] = new Wave(xStart + far, base, speed, this, level);
			}
			far += 500;
		}
		return waveSet;
	}



	
	private Environment[] makeEnv(int size,int eType){
		Environment[] envSet = new Environment[size];
		int far = 0;
		for(int i=0;i<size;i++) {
			envSet[i] = new Environment(xStart+far,20,this,eType,10);
			far+=600;
		}
		return envSet;
	}

	private void drawWave(Wave wave, Graphics2D g2) {
		g2.drawImage(wave.getImage(), wave.x, (wave.y - waveHeight), 40, waveHeight + 10, null);

		if (Event.checkHit(sheep, wave, sheepSize, waveHeight)) {
			if (!wave.hasCollided) {
				g2.setColor(new Color(241, 98, 69));
				g2.fillRect(0, 0, 1000, 1000);

				wave.applyEffect(sheep, this);
				wave.hasCollided = true;
			}
		} else {
			wave.hasCollided = false;
		}


		if (sheep.health <= 0) {
			display.endGame(this.point);
			sheep.health = new Sheep().health;
			this.point = 0;
		}
	}


	public void setLevel(int level){
			this.level = level;
			if(level == 1){
				speed = 50;
				waveHeight = 40;
				waveSet = makeWave(4);
			} else if(level == 2){
				speed = 70;
				waveHeight = 50;
				waveSet = makeWave(5);
			}
	}


	public void nextLevel(){
			this.level++;
			this.point = 0;
			this.scoreTarget = level * 300;
			this.countdownTime += 60;
			waveSet = makeWave(4 + level);
	}



	@Override
	public void keyPressed(KeyEvent e) {
		if(System.currentTimeMillis() - lastPress > 600) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
				    sheep.jump(this);
					lastPress = System.currentTimeMillis();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//---
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//---
	}
	
	public static void main(String[] arg) {
			display = new Display();
			display.setVisible(true);
	}
}