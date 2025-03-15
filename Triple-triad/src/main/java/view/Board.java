package view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import model.CardData;
import model.Player;
import model.PlayerCardData;
import view.listeners.CardAddedListener;
import view.listeners.PositionListener;

@SuppressWarnings("serial")
public class Board extends BackgroundPanel {

	private CardComponent[][] boardButtons;
	private List<CardData> allCards;
	private List<CardAddedListener> cardAddedListeners;
	private List<PositionListener> positionListeners;

	public Board(String filename, List<CardData> allCards) {
		super(filename);
		boardButtons = new CardComponent[3][3];
		setLayout(new GridLayout(3, 3));
		this.allCards = allCards;
		this.cardAddedListeners = new ArrayList<>();
		this.positionListeners = new ArrayList<>();
		initializeBoard();
	}

	public void addCardAddedListener(CardAddedListener listener) {
		cardAddedListeners.add(listener);
	}

	public void addPositionListener(PositionListener listener) {
		positionListeners.add(listener);
	}

	public boolean addCard(PlayerCardData cardData, int row, int col) {

		if (boardButtons[row][col].getInfo() != null)
			return false;

		boardButtons[row][col].setInfo(cardData);
		configureCardAddedListeners(cardData, row, col);
		redrawCards();
		return true;
	}

	private void redrawCards() {
		removeAll();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				add(boardButtons[i][j]);
			}
		}
		revalidate();
		repaint();
	}

	private void configureCardAddedListeners(PlayerCardData card, int row, int col) {
		CardAddedEvent event = createCardAddedEvent(card, row, col);
		cardAddedListeners.forEach(listener -> listener.onCardAdded(event));
	}

	private void notifyPositionListeners(int row, int col) {
		positionListeners.forEach(listener -> listener.onPositionClicked(row, col));
	}

	private CardAddedEvent createCardAddedEvent(PlayerCardData card, int row, int col) {
		PlayerCardData top = (row > 0) ? boardButtons[row - 1][col].getInfo() : null;
		PlayerCardData bottom = (row < 2) ? boardButtons[row + 1][col].getInfo() : null;
		PlayerCardData left = (col > 0) ? boardButtons[row][col - 1].getInfo() : null;
		PlayerCardData right = (col < 2) ? boardButtons[row][col + 1].getInfo() : null;
		return new CardAddedEvent(card, top, bottom, left, right);
	}

	public void resetBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (boardButtons[i][j] != null) {
					boardButtons[i][j].setInfo(null);
				}
			}
		}
		repaint();
	}

	private void initializeBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int row = i;
				final int col = j;
				var card = new CardComponent(null, e -> notifyPositionListeners(row, col), 10);
				boardButtons[i][j] = card;
				add(card);
			}
		}
		revalidate();
	}

	public boolean isFull() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (boardButtons[i][j].getInfo() == null) {
					return false;
				}
			}
		}
		return true;
	}

	public void applyTypeBonus(TripleTriadUI father, PlayerCardData selected) {

		String type = selected.getCardData().getType();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				PlayerCardData currentCard = boardButtons[i][j].getInfo();
				if (currentCard != null && (currentCard.getCardData().getType()).equalsIgnoreCase(type)
						&& currentCard != selected) {
					boardButtons[i][j].getInfo().getCardData().incrementValues();
					father.getGameLog()
							.addLogMessage("Bônus de tipo aplicado na carta " + currentCard.getCardData().getName());
				}
			}
		}
	}

	public PlayerCardData getCard(int row, int col) {
		return boardButtons[row][col].getInfo();
	}

	public CardComponent[][] getBoardCards() {
		return boardButtons;
	}

	public List<CardData> getAllCards() {
		return this.allCards;
	}

	public List<CardAddedListener> getCardAddedListeners() {
		return this.cardAddedListeners;
	}

	public List<PositionListener> getPositionListeners() {
		return this.positionListeners;
	}

	public int countPlayerCards(Player player) {
		int points = 0;
		for (int lin = 0; lin < 3; lin++) {
			for (int col = 0; col < 3; col++) {
				CardComponent cardComponent = boardButtons[lin][col];

				if (cardComponent != null && cardComponent.getInfo() != null) {
					PlayerCardData playerCardData = cardComponent.getInfo();

					if (playerCardData.getOwner().equals(player)) {
						points++;
					}
				}
			}
		}
		return points;
	}
}