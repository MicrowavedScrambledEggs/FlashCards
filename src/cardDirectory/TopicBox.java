package cardDirectory;
import java.io.File;

import cardLogic.Card;
import cardLogic.Deck;


public class TopicBox {
	
	private File topicDir;

	public TopicBox(File topicDir) {
		super();
		this.topicDir = topicDir;
	}
	
	public String getTopicName(){
		return this.topicDir.getName();
	}
	
	public Deck getTopicDeck(){
		Deck topicDeck = new Deck();
		File[] topicCards = topicDir.listFiles();
		for(int i = 0; i < topicCards.length; i++){
			topicDeck.add(new Card(topicCards[i]));
		}
		return topicDeck;
	}
	
}
