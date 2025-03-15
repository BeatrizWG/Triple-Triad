package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.CardData;
import model.Player;
import model.SoundServices;

@SuppressWarnings("serial")
public class TripleTriadUI extends JFrame {

	private Board board;
	private GameLog gameLog;
	private PlayerCards p1;
	private PlayerCards p2;
	private PlayerCards playAtual;
	private JPanel scorePanel;
	private final int glW;
	private final int glH;
	private final int plW;
	private final int plH;
	private final int spW;
	private final int spH;
	private final Dimension SCREEN_SIZE = new Dimension(800, 700);
	private final SoundServices soundServices;
	private static final Random random = new Random();

	public TripleTriadUI(List<CardData> cards, SoundServices soundServices) throws IOException {

		this.soundServices = soundServices;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				soundServices.getSoundService("main-theme").close();
			}
		});

		setTitle("Triple Triad");
		setSize(SCREEN_SIZE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		plW = (int) (getWidth() * 0.17);
		plH = getHeight();
		glW = getWidth();
		glH = (int) (getHeight() * 0.12);
		spW = getWidth();
		spH = (int) (getHeight() * 0.04);

		InitialScreenView initialScreen = new InitialScreenView(e -> {
			if (e instanceof InitialScreenView.StartGameEvent) {
				InitialScreenView.StartGameEvent event = (InitialScreenView.StartGameEvent) e;
				String nomeJogador1 = event.getPlayer1Name();
				String nomeJogador2 = event.getPlayer2Name();
				initializeGame(cards, nomeJogador1, nomeJogador2);
			}
		});

		add(initialScreen, BorderLayout.CENTER);
		soundServices.getSoundService("main-theme").playThenLoop("theme-loop.wav");
		setVisible(true);
	}

	private void initializeGame(List<CardData> cards, String nomeJogador1, String nomeJogador2) {

		remove(getContentPane().getComponent(0));
		revalidate();
		repaint();

		gameLog = new GameLog();
		gameLog.setPreferredSize(new Dimension(glW, glH));
		gameLog.setBorder(BorderFactory.createTitledBorder("Log de Jogadas"));

		JPanel boardWrapper = new JPanel(new BorderLayout());

		board = new Board("/back.png", cards);
		boardWrapper.add(board, BorderLayout.CENTER);
		boardWrapper.setBorder(BorderFactory.createTitledBorder("Campo"));

		List<CardData> cardsP1 = new ArrayList<>();
		List<CardData> cardsP2 = new ArrayList<>();

		chooseCards(cards, cardsP1, cardsP2);

		Player play1 = new Player(nomeJogador1, cardsP1, Color.decode("#4a6143"));
		Player play2 = new Player(nomeJogador2, cardsP2, Color.decode("#d0a64f"));

		p1 = new PlayerCards(this, play1, plW, plH);
		p2 = new PlayerCards(this, play2, plW, plH);

		playAtual = p1;

		scorePanel = new ScorePanel(p1.getPlayer(), p2.getPlayer());
		scorePanel.setPreferredSize(new Dimension(spW, spH));

		JPanel centerPanel = new JPanel(new BorderLayout(0, 0));
		centerPanel.add(scorePanel, BorderLayout.NORTH);
		centerPanel.add(boardWrapper, BorderLayout.CENTER);
		centerPanel.add(gameLog, BorderLayout.SOUTH);

		add(centerPanel, BorderLayout.CENTER);
		add(p1, BorderLayout.WEST);
		add(p2, BorderLayout.EAST);

		JButton confirmTurnButton = new JButton("Iniciar turno");
		confirmTurnButton.addActionListener(e -> {
			confirmFlipAction(playAtual);
		});

		JButton hideCardsButton = new JButton("Esconder cartas");
		hideCardsButton.addActionListener(e -> {
			hideCards(playAtual);
		});

		JButton changeCardsButton = new JButton("Trocar Cartas");
		changeCardsButton.addActionListener(e -> {
			changeCards(cardsP1, cardsP2, changeCardsButton, centerPanel, hideCardsButton, confirmTurnButton);
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		buttonPanel.add(changeCardsButton);
		buttonPanel.add(confirmTurnButton);
		buttonPanel.add(hideCardsButton);

		add(buttonPanel, BorderLayout.SOUTH);

		confirmTurnButton.setEnabled(false);
		hideCardsButton.setEnabled(false);

		setLocationRelativeTo(null);

		setVisible(true);

	}

	private void chooseCards(List<CardData> cards, List<CardData> cardsP1, List<CardData> cardsP2) {
		List<Integer> listCardsUsed = new ArrayList<>();

		for (int i = 0; i < 5; i++) {

			int valueC1 = random.nextInt(cards.size());
			while (listCardsUsed.contains(valueC1)) {
				valueC1 = random.nextInt(cards.size());
			}
			listCardsUsed.add(valueC1);
			cardsP1.add(cards.get(valueC1));

			int valueC2 = random.nextInt(cards.size());
			while (listCardsUsed.contains(valueC2)) {
				valueC2 = random.nextInt(cards.size());
			}
			listCardsUsed.add(valueC2);
			cardsP2.add(cards.get(valueC2));
		}
	}

	private void changeCards(List<CardData> cardsP1, List<CardData> cardsP2, JButton trocarCartasButton,
			JPanel centerPanel, JButton hideCardsButton, JButton confirmTurnButton) {

		Random random = new Random();
		CardData cartaP1 = cardsP1.remove(random.nextInt(cardsP1.size()));
		CardData cartaP2 = cardsP2.remove(random.nextInt(cardsP2.size()));

		cardsP1.add(cartaP2);
		cardsP2.add(cartaP1);

		gameLog.addEntry("Cartas trocadas: " + cartaP1.getName() + " com " + cartaP2.getName());

		updateCardsPanel();

		trocarCartasButton.setEnabled(false);
		confirmTurnButton.setEnabled(true);
		hideCardsButton.setEnabled(true);

		confirmFlipAction(playAtual);
	}

	private void updateCardsPanel() {

		remove(p1);
		remove(p2);

		p1 = new PlayerCards(this, p1.getPlayer(), plW, plH);
		p2 = new PlayerCards(this, p2.getPlayer(), plW, plH);

		add(p1, BorderLayout.WEST);
		add(p2, BorderLayout.EAST);

		revalidate();
		repaint();
	}

	private void hideCards(PlayerCards playAtual) {
		this.playAtual.flipAllCards(true);
	}

	private void confirmFlipAction(PlayerCards playAtual) {

		this.playAtual = (this.playAtual == p1) ? p2 : p1;
		playAtual.setCardsActive(true);
		gameLog.confirmFlipCards(this.playAtual);
		this.playAtual.flipAllCards(false);
	}

	public void endGame() {
		JOptionPane.showMessageDialog(this, "O tabuleiro está completo! O jogo acabou.", "Fim de Jogo",
				JOptionPane.INFORMATION_MESSAGE);

		int p1Score = board.countPlayerCards(p1.getPlayer());
		int p2Score = board.countPlayerCards(p2.getPlayer());

		this.dispose();

		JFrame finalFrame = new JFrame("Resultado Final");
		EndScreenView finalScreen;

		if (p1Score > p2Score) {
			finalScreen = new EndScreenView(p1.getPlayer().getName() + " venceu! ", p1Score);
		} else if (p2Score > p1Score) {
			finalScreen = new EndScreenView(p2.getPlayer().getName() + " venceu! ", p2Score);
		} else {
			finalScreen = new EndScreenView("O jogo empatou!", p2Score);
		}

		finalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		finalFrame.setContentPane(finalScreen);
		finalFrame.setSize(800, 600);
		finalFrame.setLocationRelativeTo(null);
		finalFrame.setVisible(true);
	}

	public Board getBoard() {
		return this.board;
	}

	public GameLog getGameLog() {
		return this.gameLog;
	}

	public PlayerCards getP1() {
		return this.p1;
	}

	public PlayerCards getP2() {
		return this.p2;
	}

	public ScorePanel getScorePanel() {
		return (ScorePanel) scorePanel;
	}

	public int getGlW() {
		return this.glW;
	}

	public int getGlH() {
		return this.glH;
	}

	public int getPlW() {
		return this.plW;
	}

	public int getPlH() {
		return this.plH;
	}

	public int getSpW() {
		return this.spW;
	}

	public int getSpH() {
		return this.spH;
	}

	public Dimension getSCREEN_SIZE() {
		return this.SCREEN_SIZE;
	}

	public SoundServices getSoundServices() {
		return this.soundServices;
	}

}