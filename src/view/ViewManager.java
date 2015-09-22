package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import main.Controller;


public class ViewManager extends JFrame {
	
	private JButton draw;
	private JButton flip;
	private JButton reshuffle;
	private JComboBox<String> paperSelect;
	private ArrayList<String> paperNameList;
	private TopicSelectionPanel topicSelection;
	private JScrollPane cardDisplayPane;
	private JEditorPane cardDisplay;
	private JSplitPane topicCardSplitPane;
	private int topicListWidth = 175;
	private int topicButtonWidth = 100;
	private int topicSelectionWidth = topicListWidth*2 + topicButtonWidth;
	private int cardDisplayWidth = 300;
	private int topicCardHeight = 500;

	public ViewManager(ArrayList<String> paperNameList) {
		super("Flash Cards");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		setUpTopPanel(paperNameList);
		setUpCardDisplayAndTopicSelection();
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Sets up the top panel with the combo box for paper selection, and the buttons for
	 * drawing and flipping cards, and reshuffling the deck
	 */
	private void setUpTopPanel(ArrayList<String> paperNameList){
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));//One row

		setUpPaperSelecter(topPanel, paperNameList);

		//Build and add buttons
		this.draw = new JButton(Controller.drawCommand);
		this.flip = new JButton(Controller.flipCommand);
		this.reshuffle = new JButton(Controller.reshuffleCommand);
		//Initialise these card and deck interaction buttons to disabled
		//(need to build a deck before these buttons can do anything)
		draw.setEnabled(false);
		flip.setEnabled(false);
		reshuffle.setEnabled(false);
		topPanel.add(draw);
		topPanel.add(flip);
		topPanel.add(reshuffle);

		add(topPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Builds the combo box for selecting papers and adds it to the given panel
	 * @param topPanel Panel to add combo box to
	 */
	private void setUpPaperSelecter(JPanel topPanel, ArrayList<String> paperNameList) {

		//Builds the String array for the combo box for the names of the paper folders
		this.paperNameList = paperNameList;

		//Cosmetic and for ease of use. Dud selections to show function of combo box
		paperNameList.add("----------------");
		paperNameList.add("Select the paper you want to study here");
		this.paperSelect = new JComboBox(paperNameList.toArray());
		this.paperSelect.setSelectedIndex(paperNameList.size() - 1);

		topPanel.add(paperSelect);
	}
	
	/**
	 * Sets up the JSplitPane containing the Topic selection lists and buttons on one side and
	 * the card display on the other side
	 */
	private void setUpCardDisplayAndTopicSelection() {
		setUpCardDisplay();
		topicSelection = new TopicSelectionPanel();
		topicSelection.setPreferredSize(new Dimension(this.topicSelectionWidth,
				this.topicCardHeight));
		topicCardSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, topicSelection,
				cardDisplayPane);
		topicCardSplitPane.setDividerLocation(topicSelectionWidth);
		add(topicCardSplitPane);
	}
	
	/**
	 * Sets up an uneditable JEditorPane to display the text of the cards
	 */
	private void setUpCardDisplay() {
		this.cardDisplay = new JEditorPane();
		this.cardDisplay.setEditable(false);
		this.cardDisplay.setPreferredSize(new Dimension(cardDisplayWidth, topicCardHeight));
		cardDisplayPane = new JScrollPane(cardDisplay);
		cardDisplayPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		cardDisplayPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	public void displayText(String viewCurrentCard) {
		cardDisplay.setText(viewCurrentCard);
		
	}

	public String getSelectedTopicToAdd() {
		return this.topicSelection.getSelectedTopicToAdd();
	}

	public String getSelectedTopicToRemove() {
		return this.topicSelection.getSelectedTopicToRemove();
	}

	public void setSelectedTopicsList(ArrayList<String> topicsInDeck) {
		topicSelection.setSelectedTopicsList(topicsInDeck);		
	}

	public String getSelectedPaper() {
		return (String) this.paperSelect.getSelectedItem();
	}

	public void setDraw(boolean b) {
		draw.setEnabled(b);
	}

	public void setFlip(boolean b) {
		flip.setEnabled(b);	
	}

	public void setRemoveAll(boolean b) {
		topicSelection.setRemoveAll(b);		
	}

	public void setAddAll(boolean b) {
		topicSelection.setAddAll(b);	
	}
	
	public void setActionListener(ActionListener actionListener){
		draw.addActionListener(actionListener);
		flip.addActionListener(actionListener);
		reshuffle.addActionListener(actionListener);
		paperSelect.addActionListener(actionListener);
		topicSelection.addActionListener(actionListener);
	}

	public void setTopicsToAddList(ArrayList<String> topicNameList) {
		topicSelection.setTopicsToAddList(topicNameList);	
	}

	public void setReshuffle(boolean b) {
		this.reshuffle.setEnabled(b);
	}

}
