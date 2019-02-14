package sushigame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.Plate;
import comp401.sushi.Sashimi;
import sushigame.model.Belt;
import sushigame.model.BeltEvent;
import sushigame.model.BeltObserver;
import sushigame.model.Chef;

public class BeltView extends JPanel implements BeltObserver, ActionListener {

	private Belt belt;
	private JButton[] belt_labels;
	private int buttonNum;
	private JLabel display = new JLabel();
	private JPanel displayP = new JPanel(new FlowLayout());

	public BeltView(Belt b) {
		this.belt = b;
		belt.registerBeltObserver(this);
		setLayout(new GridLayout(belt.getSize(), 1));
		belt_labels = new JButton[belt.getSize()];
		for (int i = 0; i < belt.getSize(); i++) {
			JButton plabel = new JButton("");
			plabel.setMinimumSize(new Dimension(300, 20));
			plabel.setPreferredSize(new Dimension(300, 20));
			plabel.setMaximumSize(new Dimension(300, 20));
			plabel.setOpaque(true);
			plabel.setBackground(Color.WHITE);
			add(plabel);

			belt_labels[i] = plabel;
		}
		// displayP.setPreferredSize(new Dimension(300, 300));
		// add(displayP);
		// displayP.add(display);
		refresh();
	}

	@Override
	public void handleBeltEvent(BeltEvent e) {
		refresh();
	}

	private void refresh() {

		for (int i = 0; i < belt.getSize(); i++) {
			Plate p = belt.getPlateAtPosition(i);
			Chef c = null;

			// Null Plate check for Chef
			if (p != null) {
				c = p.getChef();
			}

			// sets each index to a label
			JButton plabel = belt_labels[i];

			// removes all action listeners
			ActionListener[] actions;
			actions = plabel.getActionListeners();
			for (int j = 0; j < actions.length; j++) {
				plabel.removeActionListener(actions[j]);
			}

			if (p == null) {
				plabel.setText("");
				plabel.setBackground(Color.WHITE);
				plabel.setActionCommand("Button " + i);
			} else {
				plabel.setText("Plate created by Chef " + c.getName());
				plabel.setActionCommand("Button " + i);
				switch (p.getColor()) {
				case RED:
					plabel.setBackground(Color.RED);
					plabel.setOpaque(true);
					plabel.setBorderPainted(false);
					break;
				case GREEN:
					plabel.setBackground(Color.GREEN);
					plabel.setOpaque(true);
					plabel.setBorderPainted(false);
					break;
				case BLUE:
					plabel.setBackground(Color.CYAN);
					plabel.setOpaque(true);
					plabel.setBorderPainted(false);
					break;
				case GOLD:
					plabel.setBackground(Color.YELLOW);
					plabel.setOpaque(true);
					plabel.setBorderPainted(false);
					break;
				}

			}

			plabel.addActionListener(this);
		}
		// remove(displayP);
		// repaint();
		// revalidate();

	}

	private static DecimalFormat df2 = new DecimalFormat(".##");

	private String plateInfo(Plate p) {
		IngredientPortion[] ingrPor;
		ingrPor = p.getContents().getIngredients();
		String ingrName = " There are ";
		String rollName = "";

		if (p.getContents() instanceof Sashimi) {

			return "This is " + p.getContents().getName() + ". ";

		} else if (p.getContents() instanceof Nigiri) {

			return "This is " + p.getContents().getName() + ".";

		} else {

			for (int i = 0; i < ingrPor.length; i++) {
				if (ingrPor.length == 1) {
					rollName = "<html>This is a " + p.getContents().getName() + ". ";
					ingrName = "There are " + ingrPor[0].getAmount() + " oz. of " + ingrPor[0].getIngredient().getName()
							+ ".<br>";
					break;
				}
				if (i == ingrPor.length - 1) {
					ingrName += " and " + df2.format(ingrPor[i].getAmount()) + " oz. of "
							+ ingrPor[i].getIngredient().getName() + ".<br> ";

				} else {

					rollName = "<html>This is a" + "  " + p.getContents().getName() + ". <br> ";
					ingrName += df2.format(ingrPor[i].getAmount()) + " oz. of " + ingrPor[i].getIngredient().getName()
							+ ",<br> ";

				}
			}
			return rollName + ingrName;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// JPanel displayP = new JPanel(new FlowLayout());

		for (int i = 0; i < belt.getSize(); i++) {
			if (e.getActionCommand().equals("Button " + i)) {

				if (belt.getPlateAtPosition(i) == null) {
					JOptionPane.showMessageDialog(this, "There is no plate at this position");
					break;
				} else {

					if (belt.getAgeOfPlateAtPosition(i) == 1) {
						this.display.setText(plateInfo(belt.getPlateAtPosition(i)) + " This sushi is "
								+ belt.getAgeOfPlateAtPosition(i) + " cycle old.");

						display.setFont(new Font("Serif", Font.ITALIC, 16));
						// display.setVerticalAlignment(JLabel.CENTER);
						// display.setHorizontalAlignment(JLabel.CENTER);
						JOptionPane.showMessageDialog(this, display);
					} else {
						this.display.setText(plateInfo(belt.getPlateAtPosition(i)) + " This sushi is "
								+ belt.getAgeOfPlateAtPosition(i) + " cycles old.");

						display.setFont(new Font("Serif", Font.ITALIC, 16));
						// display.setVerticalAlignment(JLabel.CENTER);
						// display.setHorizontalAlignment(JLabel.CENTER);
						JOptionPane.showMessageDialog(this, display);

					}
				}
			}
		}
	}
}
