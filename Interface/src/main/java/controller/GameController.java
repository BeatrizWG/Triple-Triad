package controller;

import java.io.IOException;

import javax.swing.SwingUtilities;

import model.CardsReader;
import model.SoundServices;
import view.TripleTriadUI;

public class GameController {

	public void createWindow() {
		var cards = CardsReader.readCardsFromCSV();
		var soundServices = new SoundServices();
		soundServices.createSoundService("main-theme", "theme-start.wav");
		soundServices.createSoundService("selection", "selection.wav");
		soundServices.createSoundService("card-placed", "card-placed.wav", 0.7f);
		soundServices.createSoundService("error", "error.wav", 0.7f);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new TripleTriadUI(cards, soundServices);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}