package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;
import sushigame.model.SushiGameModel;

public class ScoreboardWidget extends JPanel implements BeltObserver {

	private SushiGameModel game_model;
	private JLabel display;
	private JComboBox<String> scoreViewer;
	private String[] scoreViewOptions;
	private int option;

	public ScoreboardWidget(SushiGameModel gm) {
		game_model = gm;
		game_model.getBelt().registerBeltObserver(this);
		this.option = 0;

		display = new JLabel();
		display.setVerticalAlignment(SwingConstants.TOP);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		display.setText(makeScoreboardHTMLBalance());

		scoreViewOptions = new String[] { "View Current Balance", "View Amount Consumed", "View Amount Spoiled" };
		scoreViewer = new JComboBox<String>();
		for (int i = 0; i < scoreViewOptions.length; i++) {
			scoreViewer.addItem(scoreViewOptions[i]);
		}
		scoreViewer.addActionListener(new ActionLis());
		add(scoreViewer, BorderLayout.SOUTH);
	}

	private String makeScoreboardHTMLBalance() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs = game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length + 1];
		chefs[0] = game_model.getPlayerChef();
		for (int i = 1; i < chefs.length; i++) {
			chefs[i] = opponent_chefs[i - 1];
		}
		Arrays.sort(chefs, new HighToLowBalanceComparator());

		for (Chef c : chefs) {
			sb_html += c.getName() + " ($" + Math.round(c.getBalance() * 100.0) / 100.0 + ") <br>";
		}

		return sb_html;
	}

	private String makeScoreboardHTMLConsumed() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs = game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length + 1];
		chefs[0] = game_model.getPlayerChef();
		for (int i = 1; i < chefs.length; i++) {
			chefs[i] = opponent_chefs[i - 1];
		}
		Arrays.sort(chefs, new HighToLowFoodSold());

		for (Chef c : chefs) {
			sb_html += c.getName() + "(" + Math.round(c.getAmountConsumed() * 100.0) / 100.0 + "oz.) <br>";
		}
		return sb_html;
	}

	private String makeScoreboardHTMLSpoiled() {
		String sb_html = "<html>";
		sb_html += "<h1>Scoreboard</h1>";

		// Create an array of all chefs and sort by balance.
		Chef[] opponent_chefs = game_model.getOpponentChefs();
		Chef[] chefs = new Chef[opponent_chefs.length + 1];
		chefs[0] = game_model.getPlayerChef();
		for (int i = 1; i < chefs.length; i++) {
			chefs[i] = opponent_chefs[i - 1];
		}
		Arrays.sort(chefs, new LowToHighFoodSpoiled());

		for (Chef c : chefs) {
			sb_html += c.getName() + " (" + Math.round(c.getAmountSpoiled() * 100.0) / 100.0 + "oz.) <br>";
		}
		return sb_html;
	}

	public void refresh() {
		if (option == 0) {
			display.setText(makeScoreboardHTMLBalance());
			display.setFont(new Font("Serif", Font.ITALIC, 24));
		}

		if (option == 1) {
			display.setText(makeScoreboardHTMLConsumed());
			display.setFont(new Font("Serif", Font.ITALIC, 24));
		}

		if (option == 2) {
			display.setText(makeScoreboardHTMLSpoiled());
			display.setFont(new Font("Serif", Font.ITALIC, 24));
		}

		// if(option.equals("balance")) {
		// display.setText(makeScoreboardHTMLBalance());
		// }else if (option.equals("consumed")) {
		// display.setText(makeScoreboardHTMLConsumed());
		// } else if (option.equals("spoiled")) {
		// display.setText(makeScoreboardHTMLSpoiled());
		// }else {
		// display.setText(makeScoreboardHTMLBalance());
		// }
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		if (e.getType() == BeltEvent.EventType.ROTATE) {
			refresh();
		}
	}

	class ActionLis implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource() == scoreViewer) {
				JComboBox cb = (JComboBox) e.getSource();
				String options = (String) cb.getSelectedItem();

				if (options.equals("View Current Balance")) {
					option = 0;
					refresh();
				} else if (options.equals("View Amount Consumed")) {
					option = 1;
					refresh();
				} else if (options.equals("View Amount Spoiled")) {
					option = 2;
					refresh();
				}
			}

		}

	}

}
