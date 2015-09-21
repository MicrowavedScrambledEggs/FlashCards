import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller implements ActionListener {
	
	private CardDesk cardDesk;
	private CardCrate cardCrate;
	private ViewManager viewManager;
	
	public static final String drawCommand = "Draw";
	public static final String flipCommand = "Flip";
	public static final String reshuffleCommand = "Reshuffle";
	public static final String addCommand = "Add >>";
	public static final String removeCommand = "<< Remove";
	public static final String addAllCommand = "Add All >>";
	public static final String removeAllCommand = "<html>Remove<br>&lt&lt All</html>";
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals(drawCommand)){
			cardDesk.draw();
			viewManager.displayText(cardDesk.viewCurrentCard());
		}
		if(action.equals(flipCommand)){
			cardDesk.flipCurrentCard();
			viewManager.displayText(cardDesk.viewCurrentCard());
			cardDesk.discardCurrentCard();
		}
		if(action.equals(reshuffleCommand)){
			cardDesk.resetGrandDeck();
		}
		if(action.equals(addCommand)){
			TopicBox topic = viewManager.getSelectedTopicToAdd();
			cardDesk.addTopic(topic);
			viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeck());
		}
		if(action.equals(removeCommand)){
			TopicBox topic = viewManager.getSelectedTopicToRemove();
			cardDesk.removeTopic(topic);
			viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeck());
		}
		if(action.equals(addAllCommand)){
			
		}
	}

}
