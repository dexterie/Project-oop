package display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import Element.EleButton;
import Element.EleLabel;

public class WinMenu extends JPanel {

    private static final long serialVersionUID = 1L;
    public long point;

    public WinMenu(long point, ActionListener main) {
        try {
            this.point = point;
            this.setBackground(new Color(98, 241, 69));
            this.setBounds(0, 0, 1000, 600);
            this.setFocusable(true);
            this.setLayout(null);

            EleLabel status = new EleLabel("You Win!", 40, 400, 100, 200, 100);
            status.setForeground(Color.white);

            EleLabel showPoint = new EleLabel("Score : " + this.point, 30, 400, 200, 200, 100);
            showPoint.setForeground(Color.white);

            EleButton restart = new EleButton("Restart", 15, 380, 300, 200, 50);
            restart.setActionCommand("restart");
            restart.addActionListener(main);

            EleButton next = new EleButton("Next", 15, 380, 370, 200, 50);
            next.setActionCommand("next");
            next.addActionListener(main);

            this.add(showPoint);
            this.add(status);
            this.add(restart);
            this.add(next);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
