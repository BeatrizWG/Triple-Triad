package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class GameLog extends JPanel {

	private JTextPane logArea;

	public GameLog() {
		setLayout(new BorderLayout());

		logArea = new JTextPane();
		logArea.setEditable(false);
		logArea.setContentType("text/html");

		JScrollPane scrollPane = new JScrollPane(logArea);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void addLogMessage(String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timestamp = LocalDateTime.now().format(formatter);

		Style style = logArea.addStyle("Bold", null);
		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.BLACK);

		StyledDocument doc = logArea.getStyledDocument();

		try {
			doc.insertString(doc.getLength(), "[" + timestamp + "] ", logArea.getStyle("Bold"));
			doc.insertString(doc.getLength(), message + "\n", null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		logArea.setCaretPosition(doc.getLength());
	}

	public void addEntry(String entry) {
		Document doc = logArea.getDocument();
		try {
			doc.insertString(doc.getLength(), entry + "\n", null);
			logArea.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void confirmFlipCards(PlayerCards playAtual) {
		JOptionPane.showMessageDialog(this,
				"O jogador " + playAtual.getPlayer().getName() + " pode iniciar o seu turno.", "Confirmar Virada",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
