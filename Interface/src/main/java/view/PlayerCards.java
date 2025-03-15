package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Player;
import model.PlayerCardData;

@SuppressWarnings("serial")
public class PlayerCards extends JPanel {

	private Player player;
	private int handSize = 5;
	private boolean cardsActive = true;
	private int selectedIndex;

	public PlayerCards(TripleTriadUI father, Player player, int width, int height) {
		this.player = player;
		selectedIndex = -1;

		setLayout(new GridLayout(5, 1));
		setPreferredSize(new Dimension(width, height));

		for (int i = 0; i < handSize; i++) {
			var index = i;
			var cardData = player.getCards().get(index);
			var playerCardData = new PlayerCardData(cardData, player, 0);
			var cardComponent = new CardComponent(playerCardData, e -> {
				if (!cardsActive || playerCardData.isFlipped())
					return;
				father.getGameLog().addLogMessage(String.format("A carta \'%s\' foi selecionada!", cardData.getName()));

				selectedIndex = player.getCards().indexOf(cardData);
				updateBorders();
			});
			add(cardComponent);
		}
		setBorder(BorderFactory.createTitledBorder(getPlayer().getName()));

		father.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (!contains(e.getPoint()) && selectedIndex != -1 && cardsActive) {
					selectedIndex = -1;
					updateBorders();
				}
			}
		});

		father.getBoard().addPositionListener((row, col) -> {
			var selected = getSelected();
			if (selected != null) {

				if (!father.getBoard().addCard(selected, row, col)) {
					father.getGameLog()
							.addLogMessage(String.format("A posição [%d, %d] já possui uma carta!", row, col));
				} else {

					removeSelected();
					setCardsActive(false);

					checkCapture(father, selected, row, col);
					father.getBoard().applyTypeBonus(father, selected);

					if (father.getBoard().isFull()) {
						father.getGameLog().addLogMessage("Fim de jogo!");
						father.endGame();
					}

				}
			}
		});
	}

	public void checkCapture(TripleTriadUI father, PlayerCardData selected, int row, int col) {

		checkCards(father, selected, row - 1, col, "N");
		checkCards(father, selected, row + 1, col, "S");
		checkCards(father, selected, row, col - 1, "W");
		checkCards(father, selected, row, col + 1, "E");
	}

	private void checkCards(TripleTriadUI father, PlayerCardData placedCard, int row, int col, String direction) {

		if (row < 0 || row >= 3 || col < 0 || col >= 3) {
			return;
		}

		PlayerCardData adjacentCard = father.getBoard().getCard(row, col);

		if (adjacentCard == null || adjacentCard.getOwner() == placedCard.getOwner()) {
			return;
		}

		boolean capture = false;
		switch (direction) {
		case "N":
			capture = placedCard.getCardData().getUp() > adjacentCard.getCardData().getDown();
			break;
		case "S":
			capture = placedCard.getCardData().getDown() > adjacentCard.getCardData().getUp();
			break;
		case "E":
			capture = placedCard.getCardData().getRight() > adjacentCard.getCardData().getLeft();
			break;
		case "W":
			capture = placedCard.getCardData().getLeft() > adjacentCard.getCardData().getRight();
			break;
		}

		if (capture) {

			adjacentCard.getOwner().modifyScore(-1);
			placedCard.getOwner().modifyScore(1);

			adjacentCard.setOwner(placedCard.getOwner());

			father.getGameLog().addLogMessage("Carta " + adjacentCard.getCardData().getName() + " foi capturada!");
			father.getScorePanel().updateScores();
		}
	}

	public void setCardsActive(boolean enabled) {
		this.cardsActive = enabled;
	}

	private void updateBorders() {
		for (int i = 0; i < handSize; i++) {
			var cardComponent = (CardComponent) getComponent(i);
			cardComponent.setCardIsSelected(i == selectedIndex);
		}
		revalidate();
		repaint();
	}

	public PlayerCardData getSelected() {
		if (selectedIndex != -1) {
			return ((CardComponent) getComponent(selectedIndex)).getInfo();
		}
		return null;
	}

	public boolean removeSelected() {
		var selected = getSelected();
		if (selected == null)
			return false;
		CardComponent selectedCard = (CardComponent) getComponent(selectedIndex);
		player.getCards().removeIf(card -> card.equals(selectedCard.getInfo().getCardData()));
		selectedIndex = -1;
		handSize--;
		remove(selectedCard);
		revalidate();
		repaint();
		return true;
	}

	private List<PlayerCardData> getAllPlayerCardData() {
		List<PlayerCardData> cardDataList = new ArrayList<>();
		for (int i = 0; i < getComponentCount(); i++) {
			CardComponent cardComponent = (CardComponent) getComponent(i);
			cardDataList.add(cardComponent.getInfo());
		}
		return cardDataList;
	}

	public void processAllPlayerCardData(BiConsumer<Integer, PlayerCardData> action) {
		var cards = getAllPlayerCardData();
		for (int i = 0; i < cards.size(); i++) {
			action.accept(i, cards.get(i));
		}
	}

	public void flipAllCards(boolean hidden) {
		processAllPlayerCardData((index, cardData) -> {
			cardData.setFlipped(hidden);
		});
		revalidate();
		repaint();
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getHandSize() {
		return this.handSize;
	}

	public boolean isCardsActive() {
		return this.cardsActive;
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}
}
