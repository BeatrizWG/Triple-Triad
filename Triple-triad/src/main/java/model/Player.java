package model;

import java.awt.Color;
import java.util.List;

public class Player {

	private String name;
	private int points = 5;
	private List<CardData> cards;
	private Color color;

	public Player(String name, List<CardData> cards, Color color) {
		if (cards.size() != 5)
			throw new RuntimeException("A mão do jogador, inicialmente, precisa conter exatamente 5 cartas!");
		this.cards = cards;
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public int getPoints() {
		return this.points;
	}

	public List<CardData> getCards() {
		return this.cards;
	}

	public Color getColor() {
		return this.color;
	}

	public void modifyScore(int valor) {
		this.points += valor;
	}
}