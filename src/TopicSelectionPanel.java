import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	private File[] topicList;//array for list of topic directories

	private JPanel topicButtonPanel;
	private JList topicsAvailable;
	private JList topicsSelected;
	private int divisionUnit = 18;
	private int listProportion = 7;
	private int buttonProportion = 4;
	private JScrollPane availableScroll;
	private JScrollPane selectedScroll;
	private DefaultListModel<String> selectedTopicsStrings;
	private DefaultListModel<File> selectedTopicsDirectories;
	private Deck deck;

	private final String addCommand = "Add >>";
	private final String removeCommand = "<< Remove";
	private final String addAllCommand = "Add All >>";
	private final String removeAllCommand = "<html>Remove<br>&lt&lt All</html>";
	private JButton removeAll;
	private JButton addAll;
	private JButton remove;
	private JButton add;

	public TopicSelectionPanel(Deck deck){
		this.deck = deck;
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
		this.topicsAvailable = new JList();
		this.availableScroll = new JScrollPane(topicsAvailable);
		availableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//availableScroll.setPreferredSize(new Dimension(topicListWidth, topicCardHeight));

		setUpTopicSelectionButtons();

		//Builds the JList for the selected topics
		this.topicsSelected = new JList();
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

		this.add = new JButton(addCommand);
		this.remove =  new JButton(removeCommand);
		this.addAll = new JButton(addAllCommand);
		this.removeAll = new JButton(removeAllCommand);
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

	/**
	 * Clears the selected topic JList and empties the deck
	 */
	private void removeAllTopics() {
		selectedTopicsStrings.removeAllElements();
		selectedTopicsDirectories.removeAllElements();
		deck = new Deck();
		removeAll.setEnabled(false);
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
		this.topicsAvailable.setSize(listDimension);
		this.topicsAvailable.setPreferredSize(listDimension);
		this.topicsSelected.setSize(listDimension);
		this.topicsSelected.setPreferredSize(listDimension);
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
	 * Sets the topics available JList to contain and display the names of the topics of the
	 * selected paper
	 * @param paperTopics array of topic folders
	 */
	public void displayTopics(File[] paperTopics){
		//build array of topic directories for paper
		this.topicList = paperTopics;

		//Build array of topic names, from topic directory array
		this.topicNameList = new String[topicList.length];
		for(int i = 0; i < topicList.length; i++){
			topicNameList[i] = topicList[i].getName();
		}

		this.topicsAvailable.setListData(topicNameList);
		this.addAll.setEnabled(true);
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
			selectedTopicsDirectories = new DefaultListModel<File>();
			topicsSelected.setModel(selectedTopicsStrings);
		}

		String topicName = topicNameList[topicIndex];
		if(!selectedTopicsStrings.contains(topicName)){
			//if selected topic is not already in the selected topics JList
			//Adds the topic to the selected topic's JList, and its cards to the deck
			selectedTopicsStrings.addElement(topicName);
			selectedTopicsDirectories.addElement(topicList[topicIndex]);
			deck.loadTopicCards(topicList[topicIndex]);
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
		String topicName = selectedTopicsStrings.get(topicIndex);

		//Remove that topic from the selected topic JList
		selectedTopicsStrings.remove(topicIndex);
		selectedTopicsDirectories.remove(topicIndex);

		if(selectedTopicsStrings.isEmpty()){
			//Clears the deck completely, now that there are no topics
			deck = new Deck();
			removeAll.setEnabled(false);
		} else {
			//Creates a replacement deck with all the cards from that topic removed
			deck = deck.removeTopic(topicName);

		}
	}


}
