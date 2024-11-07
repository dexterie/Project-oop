package display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Display extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private Dimension size = new Dimension(1000,600);
		
	public Display() {
		this.setting();
		Game game = new Game();
		this.getContentPane().add(new Game());
		game.requestFocusInWindow();
	}
	
	private void setting() {
		this.setTitle("Sheep HOP HOP");
		this.setSize(size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(280,100);
		this.setVisible(true);
	}
	
	private void removeContent() {
		this.getContentPane().removeAll();
		this.getContentPane().repaint();
	}

	public void endGame(long point) {
		removeContent();
		this.getContentPane().add(new Menu(point,this));
	}

	public void showWinMenu(long point) {
		removeContent();
		this.getContentPane().add(new WinMenu(point, this));
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("restart")) {
			removeContent();
			Game game = new Game();
			this.getContentPane().add(game);
			game.requestFocusInWindow();
		}  if(e.getActionCommand().equals("next")) {
			removeContent();
			Game game = new Game();
			game.nextLevel();
			this.getContentPane().add(game);
			game.requestFocusInWindow();
		}
	}

}
