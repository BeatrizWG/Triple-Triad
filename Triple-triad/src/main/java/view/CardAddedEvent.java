package view;

import model.PlayerCardData;

public class CardAddedEvent {

	private PlayerCardData card;
	private PlayerCardData top;
	private PlayerCardData bottom;
	private PlayerCardData left;
	private PlayerCardData right;

	public CardAddedEvent(final PlayerCardData card, final PlayerCardData top, final PlayerCardData bottom,
			final PlayerCardData left, final PlayerCardData right) {
		this.card = card;
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}

	public PlayerCardData getCard() {
		return this.card;
	}

	public void setCard(final PlayerCardData card) {
		this.card = card;
	}

	public PlayerCardData getTop() {
		return this.top;
	}

	public void setTop(final PlayerCardData top) {
		this.top = top;
	}

	public PlayerCardData getBottom() {
		return this.bottom;
	}

	public void setBottom(final PlayerCardData bottom) {
		this.bottom = bottom;
	}

	public PlayerCardData getLeft() {
		return this.left;
	}

	public void setLeft(final PlayerCardData left) {
		this.left = left;
	}

	public PlayerCardData getRight() {
		return this.right;
	}

	public void setRight(final PlayerCardData right) {
		this.right = right;
	}
}