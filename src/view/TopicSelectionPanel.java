package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Controller;

/**
 * Panel to contain UI features relating to topic selection for the deck
 *
 * Main difference to a regular JPanel is that it is designed for specifically three
 * components in one row with a 7:4:7 ratio for their widths
 *
 * @author Badi James
 */
public class TopicSelectionPanel extends JPanel implements ListSelectionListener{

	private String[] topicNameList;//array for list of topic directory Strings

	private JPanel topicButtonPanel;
	private JList<String> topicsAvailable;
	private JList<String> topicsSelected;
	private int divisionUnit = 18;
	private int listProportion = 7;
	private int buttonProportion = 4;
	private JScrollPane availableScroll;
	private JScrollPane selectedScroll;
	private DefaultListModel<String> selectedTopicsStrings;
	private DefaultListModel<String> topicsAvailableStrings;

	private JButton removeAll;
	private JButton addAll;
	private JButton remove;
	private JButton add;
	
	public TopicSelectionPanel(){
		setUpTopicSelection();
	}

	/**
	 * Constructor for class TopicSelectionPanel. Initialises a JPanel and then adds the
	 * three parameters to it along one row.
	 *
	 * @param topicsAvailable ScrollPane containing list to display topics available
	 * @param buttonPanel Panel containing buttons for selecting topics
	 * @param topicsSelected ScrollPane containing list to display topics selected
	 */
	public TopicSelectionPanel(JScrollPane topicsAvailable, JPanel buttonPanel,
			JScrollPane topicsSelected){
		super();
		this.availableScroll = topicsAvailable;
		this.topicButtonPanel = buttonPanel;
		this.selectedScroll = topicsSelected;
		layoutComponents();
	}

	private void layoutComponents() {
		setLayout(new BorderLayout());
		add(availableScroll, BorderLayout.WEST);
		add(topicButtonPanel, BorderLayout.CENTER);
		add(selectedScroll, BorderLayout.EAST);
	}

	/**
	 * Builds the topic selection panel with the JList of topics belonging to
	 * the selected paper, the buttons for adding and removing topics, and the
	 * JList of topics whose cards are currently in the deck
	 */
	private void setUpTopicSelection() {

		//Build the JList for the selected paper's topics
		this.topicsAvailable = new JList<String>();
		this.topicsAvailable.addListSelectionListener(this);
		this.topicsAvailableStrings = new DefaultListModel<String>();
		this.topicsAvailable.setModel(topicsAvailableStrings);
		this.availableScroll = new JScrollPane(topicsAvailable);
		availableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//availableScroll.setPreferredSize(new Dimension(topicListWidth, topicCardHeight));

		setUpTopicSelectionButtons();

		//Builds the JList for the selected topics
		this.topicsSelected = new JList<String>();
		this.topicsSelected.addListSelectionListener(this);
		this.selectedTopicsStrings = new DefaultListModel<String>();
		this.topicsSelected.setModel(selectedTopicsStrings);
		this.selectedScroll = new JScrollPane(topicsSelected);
		selectedScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//selectedScroll.setPreferredSize(new Dimension(topicListWidth, topicCardHeight));

		layoutComponents();
	}

	/**
	 * Adds the buttons for choosing which topics to include in deck
	 */
	private void setUpTopicSelectionButtons() {
		//creates a panel for the buttons
		topicButtonPanel = new JPanel();
		topicButtonPanel.setLayout(new GridLayout(0,1));//vertical list

		this.add = new JButton(Controller.addCommand);
		this.remove =  new JButton(Controller.removeCommand);
		this.addAll = new JButton(Controller.addAllCommand);
		this.removeAll = new JButton(Controller.removeAllCommand);
		//initialises buttons to disabled (need to select a paper before they can do anything)
		add.setEnabled(false);
		remove.setEnabled(false);
		addAll.setEnabled(false);
		removeAll.setEnabled(false);

		topicButtonPanel.add(add);
		topicButtonPanel.add(remove);
		topicButtonPanel.add(addAll);
		topicButtonPanel.add(removeAll);
		//topicButtonPanel.setPreferredSize(new Dimension(topicButtonWidth, topicCardHeight));
	}

	@Override
	public void setPreferredSize(Dimension d){
		super.setPreferredSize(d);
		readjustComponentsToNewWidth(d.width, d.height);
	}

	@Override
	public void setSize(Dimension d){
		super.setSize(d);
		readjustComponentsToNewWidth(d.width, d.height);
	}

	@Override
	public void setSize(int width, int height){
		super.setSize(width, height);
		readjustComponentsToNewWidth(width, height);
	}

	/**
	 * Enforces 7:4:7 component width ratio when this JPanel's dimensions change
	 * @param width new width of JPanel
	 * @param height new height of JPanel
	 */
	private void readjustComponentsToNewWidth(int width, int height) {
		int widthDivision = width/divisionUnit;
		int listWidth = widthDivision*listProportion;
		int buttonWidth = widthDivision*buttonProportion;
		Dimension listDimension = new Dimension(listWidth, height);
		Dimension buttonDimension = new Dimension(buttonWidth, height);
		availableScroll.setSize(listDimension);
		availableScroll.setPreferredSize(listDimension);
		selectedScroll.setSize(listDimension);
		selectedScroll.setPreferredSize(listDimension);
		this.topicButtonPanel.setSize(buttonDimension);
		this.topicButtonPanel.setPreferredSize(buttonDimension);
	}

	@Override
	public void repaint(){
		//Only adjust components with if the panel actually has a size
		if(this.getWidth() != 0 && this.getHeight() != 0){
			readjustComponentsToNewWidth(this.getWidth(), this.getHeight());
		}
		super.repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		//Enables or disables add and remove buttons depending of what was selected in what
		//list
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

	/**
	 * Adds the topic in the topics available JList at the given index to the selected topics
	 * JList, and the topic's cards to the deck
	 * @param topicIndex Index of selected topic in the topics available JList
	 */
	public void addTopic(int topicIndex) {
		if(selectedTopicsStrings == null){
			//if this is the first time a topic has been selected
			//initialises the list models for the selected topics JList
			selectedTopicsStrings = new DefaultListModel<String>();
			topicsSelected.setModel(selectedTopicsStrings);
		}

		String topicName = topicNameList[topicIndex];
		if(!selectedTopicsStrings.contains(topicName)){
			//if selected topic is not already in the selected topics JList
			//Adds the topic to the selected topic's JList, and its cards to the deck
			selectedTopicsStrings.addElement(topicName);
			removeAll.setEnabled(true);
		}
	}

	/**
	 * Removes the currently selected topic in the selected topic JList from the selected
	 * topic JList. Removes all cards from that topic from the deck
	 */
	public void removeTopic() {
		//Get the selected topic's name
		int topicIndex = topicsSelected.getSelectedIndex();

		//Remove that topic from the selected topic JList
		selectedTopicsStrings.remove(topicIndex);
		if(selectedTopicsStrings.isEmpty()){
			//Clears the deck completely, now that there are no topics
			removeAll.setEnabled(false);
		}
	}

	public String getSelectedTopicToAdd() {
		return this.topicsAvailable.getSelectedValue();
	}

	public String getSelectedTopicToRemove() {
		return this.topicsSelected.getSelectedValue();
	}

	public void setSelectedTopicsList(ArrayList<String> topicsInDeck) {
		this.selectedTopicsStrings = new DefaultListModel<String>();
		for(String topic : topicsInDeck){
			selectedTopicsStrings.addElement(topic);
		}	
		this.topicsSelected.setModel(selectedTopicsStrings);
	}

	public void setRemoveAll(boolean b) {
		this.removeAll.setEnabled(b);
	}

	public void setAddAll(boolean b) {
		this.addAll.setEnabled(b);
	}

	public void addActionListener(ActionListener actionListener) {
		add.addActionListener(actionListener);
		remove.addActionListener(actionListener);
		addAll.addActionListener(actionListener);
		removeAll.addActionListener(actionListener);
	}

	public void setTopicsToAddList(ArrayList<String> topicNameList) {
		this.topicsAvailableStrings = new DefaultListModel<String>();
		for(String topic : topicNameList){
			topicsAvailableStrings.addElement(topic);
		}
		this.topicsAvailable.setModel(topicsAvailableStrings);
	}


}
