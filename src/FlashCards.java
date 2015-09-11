import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * Loads flash cards
 * 
 * @author Badi James
 * @version 1.0
 */
public class FlashCards extends JFrame implements ActionListener,
	ListSelectionListener
{
    private Card currentCard;
    private ArrayList<Card> deck = new ArrayList<Card>();
    private File[] topicList;//array for list of topic directories
    private File[] paperList;//array for list of paper directories
    private String[] topicNameList;//array for list of topic directory Strings
    private String[] paperNameList;//array for list of paper directory Strings
    private File paperDir;//Directory of paper chosen
    private String paperTopicList = "";//name of file that contains the topic names of the paper chosen
    private File cardDir;//folder that contains all the cards for every paper
    
    private JButton draw;
    private JButton flip;
    private JButton reshuffle;//TODO implement this!!
    private JButton add;
    private JButton remove;
    private JButton addAll;
    private JButton removeAll;
    private JEditorPane cardDisplay;
    private JComboBox paperSelect;
    private JList topicsAvailable;
    private JList topicsSelected;
    private DefaultListModel<String> selectedTopicsStrings;
    private DefaultListModel<File> selectedTopicsDirectories;
    
    private final String drawCommand = "Draw";
    private final String flipCommand = "Flip";
    private final String reshuffleCommand = "Reshuffle";
    private final String addCommand = "Add >>";
    private final String removeCommand = "<< Remove";
    private final String addAllCommand = "Add All >>";
    private final String removeAllCommand = "<< Remove All";


    /**
     * Constructor for objects of class FlashCards. Sets up the buttons and initialises 
     * the card directory. Initialises the array of paper directories from the card 
     * directory's list of files. Starts the program off by asking the user to pick the 
     * paper they want to study
     */
    public FlashCards()
    {	
    	super("Flash Cards");
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.setLayout(new BorderLayout());
    	
    	setUpTopPanel();
    	setUpCardDisplay();
    	setUpTopicSelection();        
        setUpActionListener();
        
        this.pack();
        this.setVisible(true);
    }

    /**
     * Assigns event listeners to the buttons, combo boxes and lists  
     */
	private void setUpActionListener() {
		draw.addActionListener(this);
        flip.addActionListener(this);
        reshuffle.addActionListener(this);
        paperSelect.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);
        addAll.addActionListener(this);
        removeAll.addActionListener(this);
        paperSelect.addActionListener(this);
        topicsAvailable.addListSelectionListener(this);
        topicsSelected.addListSelectionListener(this);
	}
	
	/**
	 * Builds the topic selection panel with the JList of topics belonging to
	 * the selected paper, the buttons for adding and removing topics, and the
	 * JList of topics whose cards are currently in the deck
	 */
	private void setUpTopicSelection() {
		JPanel topicSelectionPanel = new JPanel();
        topicSelectionPanel.setLayout(new GridLayout(1,3));
        
        //Build the JList for the selected paper's topics 
        this.topicsAvailable = new JList();
        JScrollPane availableScroll = new JScrollPane(topicsAvailable);
        topicSelectionPanel.add(availableScroll);
        
        setUpTopicSelectionButtons(topicSelectionPanel);
        
        //Builds the JList for the selected topics
        this.topicsSelected = new JList();
        JScrollPane selectedScroll = new JScrollPane(topicsSelected);
        topicSelectionPanel.add(selectedScroll);
        
        add(topicSelectionPanel, BorderLayout.WEST);
	}
	
	/**
	 * Adds the buttons for choosing which topics to include in deck
	 * @param topicSelectionPanel Panel to add the buttons to
	 */
	private void setUpTopicSelectionButtons(JPanel topicSelectionPanel) {
		//creates a panel for the buttons
		JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,1));//vertical list
        
        this.add = new JButton(addCommand);
        this.remove =  new JButton(removeCommand);
        this.addAll = new JButton(addAllCommand);
        this.removeAll = new JButton(removeAllCommand);
        //initialises buttons to disabled (need to select a paper before they can do anything)
        add.setEnabled(false);
        remove.setEnabled(false);
        addAll.setEnabled(false);
        removeAll.setEnabled(false);
        
        buttonPanel.add(add);
        buttonPanel.add(remove);
        buttonPanel.add(addAll);
        buttonPanel.add(removeAll);
        topicSelectionPanel.add(buttonPanel);
	}
	
	/**
	 * Sets up an uneditable JEditorPane to display the text of the cards
	 */
	private void setUpCardDisplay() {
		this.cardDisplay = new JEditorPane();
    	this.cardDisplay.setEditable(false);
    	this.cardDisplay.setPreferredSize(new Dimension(400,400));
    	JScrollPane cardScroll = new JScrollPane(cardDisplay); 
    	add(cardScroll, BorderLayout.EAST);
	}
    
    private void setUpTopPanel(){
    	JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,4));
    	setUpPaperSelecter(topPanel);
    	this.draw = new JButton(drawCommand);
    	this.flip = new JButton(flipCommand);
    	this.reshuffle = new JButton(reshuffleCommand);
    	draw.setEnabled(false);
    	flip.setEnabled(false);
    	reshuffle.setEnabled(false);
    	topPanel.add(draw);
        topPanel.add(flip);
        topPanel.add(reshuffle);
        add(topPanel, BorderLayout.NORTH);
    }
    
    
    private void setUpPaperSelecter(JPanel topPanel) {
    	 this.cardDir = new File("Cards");
         this.paperList = this.cardDir.listFiles();
         this.paperNameList = new String[paperList.length+2];
         
         for(int i = 0; i < paperList.length; i++){
         	paperNameList[i] = paperList[i].getName();
         }
         paperNameList[paperList.length] = "----------------";
         paperNameList[paperList.length+1] = "Select the paper you want to study here";
         this.paperSelect = new JComboBox(paperNameList);
         this.paperSelect.setSelectedIndex(paperNameList.length - 1);
         topPanel.add(paperSelect);
	}
    
    /**
     * Goes through a paper directory, going into every topic folder, creates card objects from the text 
     * files and adds them to the deck
     */
    private void loadAllCardsForPaper(){
        for(int i = 0; i < this.topicList.length; i++){
            loadTopicCards(this.topicList[i]);
        }
    }
    
    /**
     * Goes through a topic folder, creates card objects from the text files and adds them to the deck
     */
    private void loadTopicCards(File topicDir){
        File[] topicCards = topicDir.listFiles();
        for(int i = 0; i < topicCards.length; i++){
            deck.add(new Card(topicCards[i]));
        }
    }
    
    /**
     * Draws a random card from the deck. Sets that card to the current card and prints the 'back' of it
     * (the card's name and the topic it belongs to) in the graphics area.
     */
    private void draw(){
    	int pick = (int) (Math.random() * this.deck.size());
    	while(this.deck.get(pick) == null){
    		pick = (int) (Math.random() * this.deck.size());
    	}
    	this.currentCard = this.deck.get(pick);
    	this.cardDisplay.setText(currentCard.getFront());  

    }
    
    /**
     * Prints the current cards info in the graphics area, then removes it from the deck.
     */
    private void flip(){
        if(this.currentCard == null){
            this.cardDisplay.setText("Need to draw a card first");
        } else {
            this.cardDisplay.setText(currentCard.getBack());
            this.deck.remove(this.currentCard); 
        }
    }
    
    public static void main(String[] args){
        new FlashCards();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(addCommand)){
			addTopic(topicsAvailable.getSelectedIndex());
		}
		else if(e.getActionCommand().equals(removeCommand)){
			removeTopic();
		}
		else if(e.getActionCommand().equals(removeAllCommand)){
			selectedTopicsStrings.removeAllElements();
			deck = new ArrayList<Card>();
			draw.setEnabled(false);
			reshuffle.setEnabled(false);
			removeAll.setEnabled(false);
		}
		else if(e.getActionCommand().equals(drawCommand)){
			draw();
			flip.setEnabled(true);
		}
		else if(e.getActionCommand().equals(flipCommand)){
			flip();
			flip.setEnabled(false);
			if(deck.isEmpty()){
				draw.setEnabled(false);
			}
		}
		else if(e.getActionCommand().equals(reshuffleCommand)){
			rebuildDeck();
			draw.setEnabled(true);
		}
		else if(e.getActionCommand().equals(addAllCommand)){
			for(int i = 0; i < topicList.length; i++){
				addTopic(i);
			}				
		}
		
		else {
			if(paperSelect.getSelectedIndex() < paperList.length){
				displayTopics(this.paperSelect.getSelectedIndex());
			}
		}
		
	}

	private void rebuildDeck() {
		if(!selectedTopicsDirectories.isEmpty()){
			deck = new ArrayList<Card>();
			for(int i = 0; i < selectedTopicsDirectories.getSize(); i++){
				loadTopicCards(selectedTopicsDirectories.get(i));
			}
		}
		
	}

	private void removeTopic() {
		int topicIndex = topicsSelected.getSelectedIndex();
		String topicName = (String) selectedTopicsStrings.get(topicIndex);
		selectedTopicsStrings.remove(topicIndex);
		selectedTopicsDirectories.remove(topicIndex);
		if(selectedTopicsStrings.isEmpty()){
			deck = new ArrayList<Card>();
			draw.setEnabled(false);
			reshuffle.setEnabled(false);
			removeAll.setEnabled(false);
		} else {
			ArrayList<Card> newDeck = new ArrayList<Card>(deck);
			for(Card c : deck){
				if(c.getTopic().equals(topicName)){
					newDeck.remove(c);
				}
			}
			deck = newDeck;
		}
	}

	private void addTopic(int topicIndex) {
		if(selectedTopicsStrings == null){
			selectedTopicsStrings = new DefaultListModel<String>();
			selectedTopicsDirectories = new DefaultListModel<File>();
			topicsSelected.setModel(selectedTopicsStrings); 
		}
		String topicName = topicNameList[topicIndex];
		if(!selectedTopicsStrings.contains(topicName)){
			selectedTopicsStrings.addElement(topicName);
			selectedTopicsDirectories.addElement(topicList[topicIndex]);
			loadTopicCards(topicList[topicIndex]);
			draw.setEnabled(true);
			reshuffle.setEnabled(true);
			removeAll.setEnabled(true);
		}
	}

	private void displayTopics(int selectedPaperIndex) {
		this.topicList = this.paperList[selectedPaperIndex].listFiles();
		this.topicNameList = new String[topicList.length];
         
         for(int i = 0; i < topicList.length; i++){
         	topicNameList[i] = topicList[i].getName();
         }
         
         this.topicsAvailable.setListData(topicNameList);
         this.addAll.setEnabled(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList list = (JList)e.getSource();
		if(list.equals(topicsAvailable)){
			if(list.getSelectedIndex() == -1){
				this.add.setEnabled(false);
			} else {
				this.add.setEnabled(true);
			}
		} else {
			if(list.getSelectedIndex() == -1){
				this.remove.setEnabled(false);
			} else {
				this.remove.setEnabled(true);
			}
		}
		
	}
}

