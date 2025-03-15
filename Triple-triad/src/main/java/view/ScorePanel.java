package view;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Player;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

	private JLabel scoreLabel;
	private Player p1;
	private Player p2;

	public ScorePanel(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
		createScorePanel();
	}

	private void createScorePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scoreLabel = new JLabel(String.format("%d : %d", p1.getPoints(), p2.getPoints()));
		scoreLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		setBorder(new EmptyBorder(5, 0, 0, 0));
		add(scoreLabel);
	}

	public void updateScores() {
		scoreLabel.setText(String.format("%d : %d", p1.getPoints(), p2.getPoints()));
	}

	public JLabel getScoreLabel() {
		return this.scoreLabel;
	}

	public Player getP1() {
		return this.p1;
	}

	public Player getP2() {
		return this.p2;
	}
}