package cardLogic;
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
