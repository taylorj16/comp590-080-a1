package sushigame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import comp401.sushi.AvocadoPortion;
import comp401.sushi.CrabPortion;
import comp401.sushi.EelPortion;
import comp401.sushi.IngredientPortion;
import comp401.sushi.Nigiri;
import comp401.sushi.RicePortion;
import comp401.sushi.Roll;
import comp401.sushi.SalmonPortion;
import comp401.sushi.Sashimi;
import comp401.sushi.SeaweedPortion;
import comp401.sushi.ShrimpPortion;
import comp401.sushi.Sushi;
import comp401.sushi.TunaPortion;

public class PlayerChefView extends JPanel implements ActionListener {

	private List<ChefViewListener> listeners;
	private int belt_size;
	private int plate_position;
	private Sashimi s;
	private Nigiri n;
	private JComboBox<Integer> posBox;
	private JComboBox<String> ingredientBox;
	private JComboBox<Double> portionBox;
	private JComboBox<String> plateColorBox;
	private JComboBox<Double> priceBox;
	private String plateColor;
	private List<IngredientPortion> ingrPorList;
	private IngredientPortion[] ingrPorArr;
	private double portionSize;
	private String[] ingredients;
	private String ingredient;
	private Double[] portions;
	private double goldPrice;
	private String plateCbox;
	private JButton sashimi_button;
	private JButton nigiri_button;
	private JButton roll_button;
	private double seaweedPortion;
	private double ricePortion;
	private double avocadoPortion;
	private double crabPortion;
	private double eelPortion;
	private double tunaPortion;
	private double shrimpPortion;
	private double salmonPortion;
	private IngredientPortion seaweed;
	private IngredientPortion avocado;
	private IngredientPortion rice;
	private IngredientPortion tuna;
	private IngredientPortion salmon;
	private IngredientPortion shrimp;
	private IngredientPortion crab;
	private IngredientPortion eel;

	public PlayerChefView(int belt_size) {
		this.belt_size = belt_size;
		listeners = new ArrayList<ChefViewListener>();
		ingrPorList = new ArrayList<IngredientPortion>();
		this.portionSize = 0.25;
		this.ingredient = "Seaweed";
		this.plateCbox = "Blue";
		this.goldPrice = 5.0;
		seaweedPortion = 0;
		avocadoPortion = 0;
		ricePortion = 0;
		crabPortion = 0;
		tunaPortion = 0;
		eelPortion = 0;
		shrimpPortion = 0;
		salmonPortion = 0;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		TitledBorder viewBorder = new TitledBorder("Sushi Factory");
		viewBorder.setTitleJustification(TitledBorder.CENTER);
		viewBorder.setTitlePosition(TitledBorder.TOP);
		setBorder(viewBorder);
		setBackground(Color.PINK);

		this.sashimi_button = new JButton("Make Sashimi!");

		sashimi_button.setActionCommand("sashimi");
		sashimi_button.addActionListener(this);
		sashimi_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(sashimi_button);

		this.nigiri_button = new JButton(" Make Nigiri!");
		nigiri_button.setActionCommand("nigiri");
		nigiri_button.addActionListener(this);
		nigiri_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(nigiri_button);

		this.roll_button = new JButton("Make Your Own Roll!");
		roll_button.setActionCommand("roll");
		roll_button.addActionListener(this);
		roll_button.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(roll_button);

		ButtonGroup group = new ButtonGroup();
		group.add(sashimi_button);
		group.add(nigiri_button);
		group.add(roll_button);
	}

	public void registerChefListener(ChefViewListener cl) {
		listeners.add(cl);
	}

	private void makeRedPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleRedPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGreenPlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleGreenPlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeBluePlateRequest(Sushi plate_sushi, int plate_position) {
		for (ChefViewListener l : listeners) {
			l.handleBluePlateRequest(plate_sushi, plate_position);
		}
	}

	private void makeGoldPlateRequest(Sushi plate_sushi, int plate_position, double price) {
		for (ChefViewListener l : listeners) {
			l.handleGoldPlateRequest(plate_sushi, plate_position, price);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("nigiri")) {

			chooseNigiri(e);
		} else if (e.getActionCommand().equals("sashimi")) {

			chooseSashimi(e);
		} else if (e.getActionCommand().equals("roll")) {

			chooseRoll(e);
		}

		typeSushiAction(e);
		createEvent(e);
		plateColorBox(e);
		setIngr(e);
		positionAction(e);
		setPortion(e);
		backEvent(e);

		if (e.getActionCommand().equals("blue") || e.getActionCommand().equals("green")
				|| e.getActionCommand().equals("red") || e.getActionCommand().equals("gold")) {
			plateColor = plateColorAction(e);
		}

		if (e.getActionCommand().equals("addIngr")) {

			setIngrPor(ingredient);
		}

		goldPrice(e);

	}

	public void backEvent(ActionEvent e) {

		if (e.getActionCommand().equals("back")) {
			n = null;
			s = null;
			plate_position = 0;
			portionSize = 0.25;
			plateCbox = "Blue";
			ingrPorList.clear();
			ingredient = "Seaweed";
			goldPrice = 5.0;
			seaweedPortion = 0;
			avocadoPortion = 0;
			ricePortion = 0;
			crabPortion = 0;
			tunaPortion = 0;
			eelPortion = 0;
			shrimpPortion = 0;
			salmonPortion = 0;
			seaweed = null;
			avocado = null;
			rice = null;
			crab = null;
			tuna = null;
			eel = null;
			shrimp = null;
			salmon = null;

			removeAll();
			add(sashimi_button);
			add(nigiri_button);
			add(roll_button);
			repaint();
			revalidate();
		}
	}

	public void createEvent(ActionEvent e) {

		if (e.getActionCommand().equals("createN")) {

			if (n != null && plate_position != belt_size + 1 && plateColor != null) {
				// System.out.println("Enter first check");
				if (plateColor.equals("blue")) {
					makeBluePlateRequest(n, plate_position);
				} else if (plateColor.equals("red")) {
					makeRedPlateRequest(n, plate_position);
				} else if (plateColor.equals("green")) {
					makeGreenPlateRequest(n, plate_position);
				} else if (plateColor.equals("gold")) {
					makeGoldPlateRequest(n, plate_position, goldPrice);
				}

			} else if (n == null || plateColor == null) {
				JOptionPane.showMessageDialog(this, "Must choose Nigiri type AND plate color");
			}
		}

		if (e.getActionCommand().equals("createS")) {

			if (plateColor == null || s == null) {
				JOptionPane.showMessageDialog(this, "Must choose Sashimi type AND plate color.");
			} else if (s != null && plateColor != null) {
				// System.out.println("Enter second check");
				if (plateColor.equals("blue")) {
					makeBluePlateRequest(s, plate_position);
				} else if (plateColor.equals("red")) {
					makeRedPlateRequest(s, plate_position);
				} else if (plateColor.equals("green")) {
					makeGreenPlateRequest(s, plate_position);
				} else {
					makeGoldPlateRequest(s, plate_position, goldPrice);
				}
			}
		}

		if (e.getActionCommand().equals("createR")) {
			addToIngrList();
			Roll specialRoll = new Roll("Random Roll", ingrPorList.toArray(new IngredientPortion[ingrPorList.size()]));
			if (ingrPorList.size() == 0) {
				JOptionPane.showMessageDialog(null, "Must select at least one ingredient for roll.");
			} else {

				if (plateCbox.equals("Blue")) {
					makeBluePlateRequest(specialRoll, plate_position);
				} else if (plateCbox.equals("Red")) {
					makeRedPlateRequest(specialRoll, plate_position);
				} else if (plateCbox.equals("Green")) {
					makeGreenPlateRequest(specialRoll, plate_position);
				} else if (plateCbox.equals("Gold")) {
					makeGoldPlateRequest(specialRoll, plate_position, goldPrice);
				}

				// resets ingredient list and portions after creation
				ingrPorList.clear();
				seaweedPortion = 0;
				avocadoPortion = 0;
				ricePortion = 0;
				crabPortion = 0;
				tunaPortion = 0;
				eelPortion = 0;
				shrimpPortion = 0;
				salmonPortion = 0;
				seaweed = null;
				avocado = null;
				rice = null;
				crab = null;
				tuna = null;
				eel = null;
				shrimp = null;
				salmon = null;

			}

		}

	}

	public void chooseNigiri(ActionEvent e) {

		if (e.getActionCommand().equals("nigiri")) {

			String sushiType = new String("Choose Nigiri Type");

			JPanel typePanel = new JPanel();
			JPanel platePanel = new JPanel(new FlowLayout());
			JPanel createPanel = new JPanel(new FlowLayout());
			JPanel boxPanel = new JPanel();
			JPanel pricePanel = new JPanel(new BorderLayout());
			JPanel backPanel = new JPanel(new FlowLayout());

			typePanel.setBackground(Color.CYAN);
			platePanel.setBackground(Color.MAGENTA);
			boxPanel.setBackground(Color.ORANGE);
			pricePanel.setBackground(Color.YELLOW);
			createPanel.setBackground(Color.GREEN);
			backPanel.setBackground(Color.PINK);

			// sets titles for JPanels
			TitledBorder typeBorder = new TitledBorder(sushiType);
			typeBorder.setTitleJustification(TitledBorder.CENTER);
			typeBorder.setTitlePosition(TitledBorder.TOP);
			typePanel.setBorder(typeBorder);

			TitledBorder plateBorder = new TitledBorder("Choose Plate Color");
			plateBorder.setTitleJustification(TitledBorder.CENTER);
			plateBorder.setTitlePosition(TitledBorder.TOP);
			platePanel.setBorder(plateBorder);

			TitledBorder boxBorder = new TitledBorder("Choose Position Number");
			boxBorder.setTitleJustification(TitledBorder.CENTER);
			boxBorder.setTitlePosition(TitledBorder.TOP);
			boxPanel.setBorder(boxBorder);

			TitledBorder priceBorder = new TitledBorder("Choose Gold Plate Price");
			priceBorder.setTitleJustification(TitledBorder.CENTER);
			priceBorder.setTitlePosition(TitledBorder.TOP);
			pricePanel.setBorder(priceBorder);

			// adding panels to frame

			removeAll();
			add(typePanel);
			add(platePanel);
			add(boxPanel);
			add(pricePanel);
			add(createPanel);
			add(backPanel);
			repaint();
			revalidate();

			// creating radio buttons for types of sashimi and nigiri
			JRadioButton eel = new JRadioButton("Eel");
			eel.setActionCommand("eel");
			eel.addActionListener(this);

			add(eel);

			JRadioButton tuna = new JRadioButton("Tuna");
			tuna.setActionCommand("tuna");
			tuna.addActionListener(this);

			add(tuna);

			JRadioButton salmon = new JRadioButton("Salmon");
			salmon.setActionCommand("salmon");
			salmon.addActionListener(this);

			add(salmon);

			JRadioButton shrimp = new JRadioButton("Shrimp");
			shrimp.setActionCommand("shrimp");
			shrimp.addActionListener(this);
			add(shrimp);

			JRadioButton crab = new JRadioButton("Crab");
			crab.setActionCommand("crab");
			crab.addActionListener(this);
			add(crab);

			ButtonGroup typeGroup = new ButtonGroup();
			typeGroup.add(eel);
			typeGroup.add(tuna);
			typeGroup.add(shrimp);
			typeGroup.add(salmon);
			typeGroup.add(crab);

			typePanel.setLayout(new FlowLayout());
			typePanel.add(eel);
			typePanel.add(tuna);
			typePanel.add(salmon);
			typePanel.add(shrimp);
			typePanel.add(crab);

			// creating radio buttons for choosing colored plates
			// putting them into button group
			JRadioButton blue = new JRadioButton("Blue Plate");
			blue.setActionCommand("blue");
			blue.addActionListener(this);
			add(blue);

			JRadioButton green = new JRadioButton("Green Plate");
			green.setActionCommand("green");
			green.addActionListener(this);
			add(green);

			JRadioButton red = new JRadioButton("Red Plate");
			red.setActionCommand("red");
			red.addActionListener(this);
			add(red);

			JRadioButton gold = new JRadioButton("Gold Plate");
			gold.setActionCommand("gold");
			gold.addActionListener(this);
			add(gold);

			ButtonGroup colors = new ButtonGroup();
			colors.add(blue);
			colors.add(gold);
			colors.add(red);
			colors.add(green);

			platePanel.setLayout(new FlowLayout());
			platePanel.add(blue);
			platePanel.add(green);
			platePanel.add(gold);
			platePanel.add(red);

			// button takes what's selected, creates the sushi, and closes the creator frame
			// will be in own action event method
			JButton create = new JButton("Create Nigiri!");
			create.setActionCommand("createN");
			create.addActionListener(this);

			// createPanel.setLayout(new BorderLayout());
			createPanel.add(create, BorderLayout.SOUTH);

			// create back to sushi homepage button
			JButton back = new JButton("Back to Sushi Factory Homepage");
			back.setActionCommand("back");
			back.addActionListener(this);
			backPanel.add(back, BorderLayout.SOUTH);

			// create JComboBox of position numbers
			this.posBox = new JComboBox<Integer>();

			for (int i = 0; i < belt_size; i++) {
				posBox.addItem(i);
			}
			this.posBox.setActionCommand("boxPos");
			this.posBox.addActionListener(this);
			// boxPanel.add(posBox, BorderLayout.CENTER);
			boxPanel.setLayout(new FlowLayout());
			boxPanel.add(posBox);
			// boxPanel.setPreferredSize(new Dimension(200, 50));

			// create box for prices
			this.priceBox = new JComboBox<Double>();

			for (double i = 5.00; i <= 10.00; i += 0.25) {
				priceBox.addItem(i);
			}
			priceBox.addActionListener(this);
			pricePanel.setLayout(new FlowLayout());
			pricePanel.add(priceBox, BorderLayout.CENTER);
			// pricePanel.setPreferredSize(new Dimension(200, 50));

		}

	}

	public void chooseSashimi(ActionEvent e) {

		if (e.getActionCommand().equals("sashimi")) {

			String sushiType = new String("Choose Sashimi Type");

			JPanel typePanel = new JPanel();
			JPanel platePanel = new JPanel(new FlowLayout());
			JPanel createPanel = new JPanel(new FlowLayout());
			JPanel boxPanel = new JPanel();
			JPanel pricePanel = new JPanel(new FlowLayout());
			JPanel backPanel = new JPanel(new FlowLayout());

			typePanel.setBackground(Color.CYAN);
			platePanel.setBackground(Color.MAGENTA);
			boxPanel.setBackground(Color.ORANGE);
			pricePanel.setBackground(Color.YELLOW);
			createPanel.setBackground(Color.GREEN);
			backPanel.setBackground(Color.PINK);

			// sets titles for JPanels
			TitledBorder typeBorder = new TitledBorder(sushiType);
			typeBorder.setTitleJustification(TitledBorder.CENTER);
			typeBorder.setTitlePosition(TitledBorder.TOP);
			typePanel.setBorder(typeBorder);

			TitledBorder plateBorder = new TitledBorder("Choose Plate Color");
			plateBorder.setTitleJustification(TitledBorder.CENTER);
			plateBorder.setTitlePosition(TitledBorder.TOP);
			platePanel.setBorder(plateBorder);

			TitledBorder boxBorder = new TitledBorder("Choose Position Number");
			boxBorder.setTitleJustification(TitledBorder.CENTER);
			boxBorder.setTitlePosition(TitledBorder.TOP);
			boxPanel.setBorder(boxBorder);

			TitledBorder priceBorder = new TitledBorder("Choose Gold Plate Price");
			priceBorder.setTitleJustification(TitledBorder.CENTER);
			priceBorder.setTitlePosition(TitledBorder.TOP);
			pricePanel.setBorder(priceBorder);

			// adding panels to frame
			// frame.add(mainPanel);
			// frame.setLocation(400, 400);
			removeAll();
			add(typePanel);
			add(platePanel);
			add(boxPanel);
			add(pricePanel);
			add(createPanel);
			add(backPanel);
			repaint();
			revalidate();

			// creating radio buttons for types of sashimi and nigiri
			JRadioButton eel = new JRadioButton("Eel");
			eel.setActionCommand("eelS");
			eel.addActionListener(this);
			add(eel);

			JRadioButton tuna = new JRadioButton("Tuna");
			tuna.setActionCommand("tunaS");
			tuna.addActionListener(this);
			add(tuna);

			JRadioButton salmon = new JRadioButton("Salmon");
			salmon.setActionCommand("salmonS");
			salmon.addActionListener(this);
			add(salmon);

			JRadioButton shrimp = new JRadioButton("Shrimp");
			shrimp.setActionCommand("shrimpS");
			shrimp.addActionListener(this);
			add(shrimp);

			JRadioButton crab = new JRadioButton("Crab");
			crab.setActionCommand("crabS");
			crab.addActionListener(this);
			add(crab);

			ButtonGroup typeGroup = new ButtonGroup();
			typeGroup.add(eel);
			typeGroup.add(tuna);
			typeGroup.add(shrimp);
			typeGroup.add(salmon);
			typeGroup.add(crab);

			typePanel.setLayout(new FlowLayout());
			typePanel.add(eel);
			typePanel.add(tuna);
			typePanel.add(salmon);
			typePanel.add(shrimp);
			typePanel.add(crab);

			// creating radio buttons for choosing colored plates
			// putting them into button group
			JRadioButton blue = new JRadioButton("Blue Plate");
			blue.setActionCommand("blue");
			blue.addActionListener(this);
			add(blue);

			JRadioButton green = new JRadioButton("Green Plate");
			green.setActionCommand("green");
			green.addActionListener(this);
			add(green);

			JRadioButton red = new JRadioButton("Red Plate");
			red.setActionCommand("red");
			red.addActionListener(this);
			add(red);

			JRadioButton gold = new JRadioButton("Gold Plate");
			gold.setActionCommand("gold");
			gold.addActionListener(this);
			add(gold);

			ButtonGroup colors = new ButtonGroup();
			colors.add(blue);
			colors.add(gold);
			colors.add(red);
			colors.add(green);

			platePanel.setLayout(new FlowLayout());
			platePanel.add(blue);
			platePanel.add(green);
			platePanel.add(gold);
			platePanel.add(red);

			// button takes what's selected, creates the sushi, and closes the creator frame
			// will be in own action event method
			JButton create = new JButton("Create Sashimi!");
			create.setActionCommand("createS");
			create.addActionListener(this);

			// createPanel.setLayout(new BorderLayout());
			createPanel.add(create, BorderLayout.SOUTH);

			// create back to sushi homepage button
			JButton back = new JButton("Back to Sushi Factory Homepage");
			back.setActionCommand("back");
			back.addActionListener(this);
			backPanel.add(back, BorderLayout.SOUTH);

			// create JComboBox of position numbers
			this.posBox = new JComboBox<Integer>();

			for (int i = 0; i < belt_size; i++) {
				this.posBox.addItem(i);
			}

			this.posBox.setActionCommand("boxPos");
			this.posBox.addActionListener(this);
			boxPanel.setLayout(new FlowLayout());
			boxPanel.add(posBox);
			// boxPanel.setPreferredSize(new Dimension(200, 50));

			// create box for prices
			this.priceBox = new JComboBox<Double>();

			for (double i = 5.00; i <= 10.00; i += 0.25) {
				priceBox.addItem(i);
			}
			priceBox.addActionListener(this);
			pricePanel.setLayout(new FlowLayout());
			pricePanel.add(priceBox, BorderLayout.CENTER);
			// pricePanel.setPreferredSize(new Dimension(200, 50));

		}

	}

	public void typeSushiAction(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "eel":
			//
			n = new Nigiri(Nigiri.NigiriType.EEL);
			break;
		case "tuna":
			//
			n = new Nigiri(Nigiri.NigiriType.TUNA);
			break;
		case "shrimp":

			n = new Nigiri(Nigiri.NigiriType.SHRIMP);
			break;
		case "crab":

			n = new Nigiri(Nigiri.NigiriType.CRAB);
			break;
		case "salmon":

			n = new Nigiri(Nigiri.NigiriType.SALMON);
			break;
		case "eelS":

			s = new Sashimi(Sashimi.SashimiType.EEL);
			break;
		case "tunaS":

			s = new Sashimi(Sashimi.SashimiType.TUNA);
			break;
		case "shrimpS":

			s = new Sashimi(Sashimi.SashimiType.SHRIMP);
			break;
		case "crabS":

			s = new Sashimi(Sashimi.SashimiType.CRAB);
			break;
		case "salmonS":

			s = new Sashimi(Sashimi.SashimiType.SALMON);
			break;
		default:
			break;

		}

	}

	public void positionAction(ActionEvent e) {

		if (e.getSource() == this.posBox) {

			JComboBox cb = (JComboBox) e.getSource();
			int pos = (int) cb.getSelectedItem();
			this.plate_position = pos;

		}

	}

	public String plateColorAction(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "blue":
			// System.out.println("plateColorAction method hit");
			return "blue";
		case "green":
			// System.out.println("plateColorAction method hit");
			return "green";
		case "gold":
			// System.out.println("plateColorAction method hit");
			return "gold";
		case "red":
			// System.out.println("plateColorAction method hit");
			return "red";
		default:
			// System.out.println("plateColorAction method hit, null returned");
			return null;

		}
	}

	public void chooseRoll(ActionEvent e) {

		JPanel ingrPorPanel = new JPanel(new FlowLayout());
		JPanel platePanel = new JPanel();
		JPanel positionPlatePanel = new JPanel();
		JPanel pricePanel = new JPanel();
		JPanel createPanel = new JPanel();
		JPanel backPanel = new JPanel(new FlowLayout());

		ingrPorPanel.setBackground(Color.CYAN);
		platePanel.setBackground(Color.MAGENTA);
		positionPlatePanel.setBackground(Color.ORANGE);
		pricePanel.setBackground(Color.YELLOW);
		createPanel.setBackground(Color.GREEN);
		backPanel.setBackground(Color.PINK);
		// setBackground(Color.)

		// adding panels to frame

		removeAll();
		add(ingrPorPanel);
		add(platePanel);
		add(positionPlatePanel);
		add(pricePanel);
		add(createPanel);
		add(backPanel);
		repaint();
		revalidate();

		// set titles for JPanels
		// TitledBorder rollBorder = new TitledBorder("Choose Ingredients & Portions");
		// ingrBorder.setTitleJustification(TitledBorder.CENTER);
		// ingrBorder.setTitlePosition(TitledBorder.TOP);
		// ingrPorPanel.setBorder(ingrBorder);

		TitledBorder ingrBorder = new TitledBorder("Choose Ingredients & Portions for Roll");
		ingrBorder.setTitleJustification(TitledBorder.CENTER);
		ingrBorder.setTitlePosition(TitledBorder.TOP);
		ingrPorPanel.setBorder(ingrBorder);

		// TitledBorder portionBorder = new TitledBorder("Choose Portion Size");
		// portionBorder.setTitleJustification(TitledBorder.CENTER);
		// portionBorder.setTitlePosition(TitledBorder.TOP);
		// portionPanel.setBorder(portionBorder);

		TitledBorder plateBorder = new TitledBorder(" Plate Color");
		plateBorder.setTitleJustification(TitledBorder.CENTER);
		plateBorder.setTitlePosition(TitledBorder.TOP);
		platePanel.setBorder(plateBorder);

		TitledBorder positionBorder = new TitledBorder(" Plate Position");
		positionBorder.setTitleJustification(TitledBorder.CENTER);
		positionBorder.setTitlePosition(TitledBorder.TOP);
		positionPlatePanel.setBorder(positionBorder);

		TitledBorder priceBorder = new TitledBorder(" Gold Plate Price");
		priceBorder.setTitleJustification(TitledBorder.CENTER);
		priceBorder.setTitlePosition(TitledBorder.TOP);
		pricePanel.setBorder(priceBorder);

		// creating create button
		JButton create = new JButton("Create Roll!");
		create.setActionCommand("createR");
		create.addActionListener(this);

		createPanel.setLayout(new FlowLayout());
		createPanel.add(create);

		// create back to sushi homepage button
		JButton back = new JButton("Back to Sushi Factory Homepage");
		back.setActionCommand("back");
		back.addActionListener(this);
		backPanel.add(back, BorderLayout.SOUTH);

		// making add ingredient button
		JButton addIngr = new JButton("Add");
		addIngr.setToolTipText("Add ingredient to your roll");
		addIngr.setActionCommand("addIngr");
		addIngr.addActionListener(this);
		ingrPorPanel.add(addIngr);

		// create ingredient combo box
		this.ingredientBox = new JComboBox<String>();
		this.ingredients = new String[] { "Seaweed", "Avocado", "Rice", "Eel", "Crab", "Shrimp", "Tuna", "Salmon" };
		ingredientBox.setModel(new DefaultComboBoxModel<String>(ingredients));
		ingredientBox.addActionListener(this);
		ingrPorPanel.add(ingredientBox);

		// create portion combo box
		this.portionBox = new JComboBox<Double>();
		this.portions = new Double[] { 0.25, 0.5, 0.75, 1.0, 1.25, 1.5 };

		for (int i = 0; i < portions.length; i++) {
			portionBox.addItem(portions[i]);
		}
		portionBox.addActionListener(this);
		ingrPorPanel.setLayout(new FlowLayout());
		ingrPorPanel.add(portionBox);
		// ingrPorPanel.setPreferredSize(new Dimension(200, 50));

		// create plate color box
		this.plateColorBox = new JComboBox<String>();
		String[] colors = new String[] { "Blue", "Gold", "Red", "Green" };

		for (int i = 0; i < colors.length; i++) {
			plateColorBox.addItem(colors[i]);
		}
		plateColorBox.addActionListener(this);
		platePanel.setLayout(new FlowLayout());
		platePanel.add(plateColorBox);

		// platePanel.setPreferredSize(new Dimension(200, 50));

		// create box for positions
		this.posBox = new JComboBox<Integer>();

		for (int i = 0; i < belt_size; i++) {
			posBox.addItem(i);
		}
		posBox.addActionListener(this);
		positionPlatePanel.setLayout(new FlowLayout());
		positionPlatePanel.add(posBox);
		// positionPlatePanel.setPreferredSize(new Dimension(200, 50));

		// create box for prices
		this.priceBox = new JComboBox<Double>();

		for (double i = 5.00; i <= 10.00; i += 0.25) {
			priceBox.addItem(i);
		}
		priceBox.addActionListener(this);
		pricePanel.setLayout(new FlowLayout());
		pricePanel.add(priceBox);
		// pricePanel.setPreferredSize(new Dimension(200, 50));
	}

	public void goldPrice(ActionEvent e) {

		if (e.getSource() == this.priceBox) {
			JComboBox cb = (JComboBox) e.getSource();
			double price = (double) cb.getSelectedItem();
			goldPrice = price;

		}

	}

	public void setIngr(ActionEvent e) {
		if (e.getSource() == ingredientBox) {
			JComboBox cb = (JComboBox) e.getSource();
			String name = (String) cb.getSelectedItem();
			ingredient = name;

		}

	}

	public void setPortion(ActionEvent e) {

		if (e.getSource() == portionBox) {
			JComboBox cb = (JComboBox) e.getSource();
			double portion = (double) cb.getSelectedItem();
			portionSize = portion;

		}
	}

	public void setIngrPor(String s) {
		switch (s) {

		case "Avocado":
			avocadoPortion += portionSize;
			if (avocadoPortion > 1.5) {

				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				// this.ingrPorList.add(new AvocadoPortion(avocadoPortion));
				avocado = new AvocadoPortion(avocadoPortion);
			}
			break;
		case "Seaweed":
			seaweedPortion += portionSize;
			if (seaweedPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				seaweed = new SeaweedPortion(seaweedPortion);
			}
			break;
		case "Rice":
			ricePortion += portionSize;
			if (ricePortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				rice = new RicePortion(ricePortion);
			}
			break;
		case "Crab":
			crabPortion += portionSize;
			if (crabPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				crab = new CrabPortion(crabPortion);
			}
			break;
		case "Salmon":
			salmonPortion += portionSize;
			if (salmonPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				salmon = new SalmonPortion(salmonPortion);
			}
			break;
		case "Eel":
			eelPortion += portionSize;
			if (eelPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				eel = new EelPortion(eelPortion);
			}
			break;
		case "Tuna":
			tunaPortion += portionSize;
			if (tunaPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				tuna = new TunaPortion(tunaPortion);
			}
			break;
		case "Shrimp":
			shrimpPortion += portionSize;
			if (shrimpPortion > 1.5) {
				JOptionPane.showMessageDialog(this, "Can't have more than 1.5oz. for ingredient type " + s);
			} else {
				shrimp = new ShrimpPortion(shrimpPortion);
			}
			break;
		default:
			break;

		}
	}

	public void plateColorBox(ActionEvent e) {

		if (e.getSource() == plateColorBox) {
			JComboBox cb = (JComboBox) e.getSource();
			String color = (String) cb.getSelectedItem();
			this.plateCbox = color;
		}

	}

	private void addToIngrList() {

		if (avocado != null) {
			ingrPorList.add(avocado);
		}
		if (seaweed != null) {
			ingrPorList.add(seaweed);
		}
		if (rice != null) {
			ingrPorList.add(rice);
		}
		if (eel != null) {
			ingrPorList.add(eel);
		}
		if (crab != null) {
			ingrPorList.add(crab);
		}
		if (salmon != null) {
			ingrPorList.add(salmon);
		}
		if (tuna != null) {
			ingrPorList.add(tuna);
		}
		if (shrimp != null) {
			ingrPorList.add(shrimp);
		}
	}

}
