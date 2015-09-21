import java.util.ArrayList;


public class CardDesk {
	
	private Deck grandDeck;
	private Deck discardPile;
	private Card currentCard;
	private ArrayList<TopicBox> topicsInDeck = new ArrayList<TopicBox>();
	
	public CardDesk(){
		grandDeck = new Deck();
		discardPile = new Deck();
	}
	
	public void addTopic(TopicBox topic){
		if(!topicsInDeck.contains(topic)){
			topicsInDeck.add(topic);
			grandDeck.addAll(topic.getTopicDeck());
		}
	}
	
	private void removeTopicFromDecks(String topicName){
		grandDeck.removeTopic(topicName);
		discardPile.removeTopic(topicName);
		if(currentCard != null && currentCard.getTopic().equals(topicName)){
			currentCard = null;
		}
	}
	
	public void removeTopic(TopicBox topic){
		topicsInDeck.remove(topic);
		removeTopicFromDecks(topic.getTopicName());
	}
	
	public void removeTopic(String topicName){
		removeTopicFromDecks(topicName);
		removeTopicBox(topicName);
	}
	
	private void removeTopicBox(String topicName) {
		TopicBox toRemove = null;
		for(TopicBox topic : topicsInDeck){
			if(topic.getTopicName().equals(topicName)){
				toRemove = topic;
				break;
			}
		}
		topicsInDeck.remove(toRemove);
	}
	
	public ArrayList<TopicBox> getTopicsInDeck(){
		return topicsInDeck;
	}
	
	public ArrayList<String> getTopicsInDeckNames(){
		ArrayList<String> topicsInDeckNames = new ArrayList<String>();
		for(TopicBox topic : topicsInDeck){
			topicsInDeckNames.add(topic.getTopicName());
		}
		return topicsInDeckNames;
	}

	/**
	 * Draws a random card from the grand deck. Sets that card to the current card
	 */
	public void draw(){
		Card drawn = null;
		do{
			int pick = (int) (Math.random() * grandDeck.size());
			drawn = grandDeck.get(pick);
		} while(drawn == null);
		this.currentCard = drawn;
	}
	
	public String viewCurrentCard(){
		if(currentCard == null){
			return "";
		}
		return currentCard.viewCard();
	}
	
	public void flipCurrentCard(){
		if(currentCard != null){
			currentCard.flip();
		}
	}
	
	public void discardCurrentCard(){
		if(currentCard != null){
			discardPile.add(currentCard);
			grandDeck.remove(currentCard);
			currentCard = null;
		}
	}
	
	public void resetGrandDeck(){
		grandDeck.addAll(discardPile);
		discardPile = new Deck();
		currentCard = null;
	}
	
}
