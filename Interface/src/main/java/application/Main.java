package application;

import controller.GameController;

public class Main {
	public static void main(String[] args) {
		var gameController = new GameController();
		gameController.createWindow();
	}
}
