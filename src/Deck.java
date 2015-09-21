import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;


public class Deck extends ArrayList<Card> {

	public Deck(){
		super();
	}

	public Deck(Collection<Card> cardCol) {
		super(cardCol);
	}

	public Deck(List<File> topicFiles) {
		super();
		for(File topicDir : topicFiles){
			loadTopicCards(topicDir);
		}
	}

	/**
	 * Goes through a topic folder, creates card objects from the text files and adds them to
	 * the deck
	 * @param topicDir Folder for topic
	 */
	public void loadTopicCards(File topicDir){
		File[] topicCards = topicDir.listFiles();
		for(int i = 0; i < topicCards.length; i++){
			add(new Card(topicCards[i]));
		}
	}

	/**
	 * Rebuilds the deck with the selected topics. Used for re-adding all the flipped cards
	 * of the current deck
	 */
	public static Deck rebuildDeck(Enumeration<File> selectedTopicsDirectories) {
		Deck rebuiltDeck = new Deck();
		while(selectedTopicsDirectories.hasMoreElements()){
			rebuiltDeck.loadTopicCards(selectedTopicsDirectories.nextElement());
		}
		return rebuiltDeck;
	}

	public Deck removeTopic(String topicName) {
		Deck newDeck = new Deck(this);
		for(Card c : this){
			if(c.getTopic().equals(topicName)){
				newDeck.remove(c);
			}
		}
		return newDeck;
	}

}
