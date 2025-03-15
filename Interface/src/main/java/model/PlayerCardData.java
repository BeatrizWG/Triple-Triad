package model;

public class PlayerCardData {

	private CardData cardData;
	private Player owner;
	private int modifier;
	private boolean flipped;

	public PlayerCardData(final CardData cardData, final Player owner, final int modifier) {
		this.cardData = cardData;
		this.owner = owner;
		this.modifier = modifier;
		this.flipped = true;
	}

	public CardData getCardData() {
		return this.cardData;
	}

	public Player getOwner() {
		return this.owner;
	}

	public int getModifier() {
		return this.modifier;
	}

	public boolean isFlipped() {
		return this.flipped;
	}

	public void setCardData(final CardData cardData) {
		this.cardData = cardData;
	}

	public void setOwner(final Player owner) {
		this.owner = owner;
	}

	public void setModifier(final int modifier) {
		this.modifier = modifier;
	}

	public void setFlipped(final boolean flipped) {
		this.flipped = flipped;
	}
}