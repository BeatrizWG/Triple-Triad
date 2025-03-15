package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class EndScreenView extends JPanel {
	private static final Color WHITE = new Color(255, 255, 255);
	private Image backgroundImage;

	public EndScreenView(String player, int totalPoints) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);

		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/backInicial.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel labelFinal = new JLabel("FIM DE JOGO!");
		labelFinal.setForeground(WHITE);
		labelFinal.setFont(new Font("Arial", Font.BOLD, 35));
		labelFinal.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel labelJPlayer = new JLabel(player);
		labelJPlayer.setForeground(WHITE);
		labelJPlayer.setFont(new Font("Arial", Font.BOLD, 25));
		labelJPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel labelPoints = new JLabel("Total de pontos: " + totalPoints);
		labelPoints.setForeground(WHITE);
		labelPoints.setFont(new Font("Arial", Font.BOLD, 25));
		labelPoints.setAlignmentX(Component.CENTER_ALIGNMENT);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(180, 10, 10, 10);
		add(labelFinal, gbc);

		gbc.gridy = 1;
		gbc.insets = new Insets(10, 10, 10, 10); 
		add(labelJPlayer, gbc);

		gbc.gridy = 2;
		gbc.insets = new Insets(10, 10, 180, 10);
		add(labelPoints, gbc);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}
}
