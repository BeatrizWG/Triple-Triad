package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings("serial")
public class InitialScreenView extends JPanel {
	private static final Color WHITE = new Color(255, 255, 255);
	private Image backgroundImage;

	public InitialScreenView(ActionListener startGameListener) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.CENTER;

		try {
			backgroundImage = ImageIO.read(getClass().getResourceAsStream("/backInicial.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JLabel labelInicio = new JLabel("PARA INICIAR, DIGITE O NOME DOS JOGADORES");
		labelInicio.setForeground(WHITE);
		labelInicio.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel labelJogador1 = new JLabel("JOGADOR 1:");
		labelJogador1.setForeground(WHITE);
		labelJogador1.setFont(new Font("Arial", Font.BOLD, 20));

		JTextField player1Field = new JTextField(20);
		player1Field.setOpaque(false);
		player1Field.setForeground(WHITE);
		player1Field.setBorder(BorderFactory.createLineBorder(WHITE));
		player1Field.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel labelJogador2 = new JLabel("JOGADOR 2:");
		labelJogador2.setForeground(WHITE);
		labelJogador2.setFont(new Font("Arial", Font.BOLD, 20));

		JTextField player2Field = new JTextField(20);
		player2Field.setOpaque(false);
		player2Field.setForeground(WHITE);
		player2Field.setBorder(BorderFactory.createLineBorder(WHITE));
		player2Field.setFont(new Font("Arial", Font.BOLD, 15));

		JButton startButton = new JButton("INICIAR JOGO");
		startButton.setBackground(new Color(0, 0, 0, 0));
		startButton.setForeground(WHITE);
		startButton.setContentAreaFilled(false);
		startButton.setBorder(BorderFactory.createLineBorder(WHITE));
		startButton.setPreferredSize(new Dimension(150, 40));
		startButton.setFont(new Font("Arial", Font.BOLD, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(labelInicio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(labelJogador1, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		add(player1Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(labelJogador2, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		add(player2Field, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(startButton, gbc);

		startButton.addActionListener(e -> {
			String nomeJogador1 = player1Field.getText().trim();
			String nomeJogador2 = player2Field.getText().trim();

			if (nomeJogador1.isEmpty() || nomeJogador2.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, preencha os nomes de ambos os jogadores.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			} else {
				startGameListener.actionPerformed(new StartGameEvent(nomeJogador1, nomeJogador2));
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null) {

			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		}
	}

	public static class StartGameEvent extends ActionEvent {
		private final String player1Name;
		private final String player2Name;

		public StartGameEvent(String player1Name, String player2Name) {
			super(new Object(), 0, "");
			this.player1Name = player1Name;
			this.player2Name = player2Name;
		}

		public String getPlayer1Name() {
			return player1Name;
		}

		public String getPlayer2Name() {
			return player2Name;
		}
	}
}
