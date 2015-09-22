package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import view.ViewManager;
import cardDirectory.CardCrate;
import cardDirectory.PaperBox;
import cardLogic.CardDesk;


public class Controller implements ActionListener {
	
	private CardDesk cardDesk;
	private CardCrate cardCrate;
	private ViewManager viewManager;
	private PaperBox selectedPaper;
	
	public static final String drawCommand = "Draw";
	public static final String flipCommand = "Flip";
	public static final String reshuffleCommand = "Reshuffle";
	public static final String addCommand = "Add >>";
	public static final String removeCommand = "<< Remove";
	public static final String addAllCommand = "Add All >>";
	public static final String removeAllCommand = "<html>Remove<br>&lt&lt All</html>";
	
	public Controller(File cardDir){
		cardDesk = new CardDesk();
		cardCrate = new CardCrate(cardDir);
		viewManager = new ViewManager(cardCrate.paperNameList());
		viewManager.setActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equals(drawCommand)){
			cardDesk.draw();
			viewManager.setFlip(true);
			viewManager.displayText(cardDesk.viewCurrentCard());			
		}
		else if(action.equals(flipCommand)){
			executeFlip();
		}
		else if(action.equals(reshuffleCommand)){
			cardDesk.resetGrandDeck();
			if(!cardDesk.allCardsDiscarded()){
				viewManager.setDraw(true);
			}
		}
		else if(action.equals(addCommand)){
			executeAdd();
		}
		else if(action.equals(removeCommand)){
			executeRemove();
		}
		else if(action.equals(addAllCommand)){
			executeAddAll();
		}
		else if(action.equals(removeAllCommand)){
			executeRemoveAll();
		}
		//TODO: make more general, for UI's that don't use combo boxes to select papers
		else if(action.equals("comboBoxChanged")){
			executeSelectPaper();
		}
	}

	private void executeSelectPaper() {
		String paperSelected = viewManager.getSelectedPaper();
		selectedPaper = cardCrate.getPaper(paperSelected);
		viewManager.setTopicsToAddList(selectedPaper.topicNameList());
		viewManager.setAddAll(true);
	}

	private void executeRemoveAll() {
		cardDesk = new CardDesk();
		viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeckNames());
		viewManager.setDraw(false);
		viewManager.setReshuffle(false);
		viewManager.setRemoveAll(false);
	}

	private void executeAddAll() {
		cardDesk.addAllTopicsFromPaper(selectedPaper);
		viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeckNames());
		viewManager.setDraw(true);
		viewManager.setReshuffle(true);
		viewManager.setRemoveAll(true);
	}

	private void executeAdd() {
		String topic = viewManager.getSelectedTopicToAdd();
		cardDesk.addTopic(selectedPaper.getTopic(topic));
		viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeckNames());
		viewManager.setDraw(true);
		viewManager.setReshuffle(true);
		viewManager.setRemoveAll(true);
	}

	private void executeRemove() {
		String topic = viewManager.getSelectedTopicToRemove();
		cardDesk.removeTopic(topic);
		viewManager.setSelectedTopicsList(cardDesk.getTopicsInDeckNames());
		if(cardDesk.getTopicsInDeck().isEmpty()){
			cardDesk = new CardDesk();
			viewManager.setDraw(false);
			viewManager.setRemoveAll(false);
		}
	}

	private void executeFlip() {
		cardDesk.flipCurrentCard();
		viewManager.displayText(cardDesk.viewCurrentCard());
		cardDesk.discardCurrentCard();
		if(cardDesk.allCardsDiscarded()){
			viewManager.setDraw(false);
		}
		viewManager.setFlip(false);
	}

}
