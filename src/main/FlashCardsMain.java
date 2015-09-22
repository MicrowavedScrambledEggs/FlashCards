package main;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import view.ErrorPanel;


public class FlashCardsMain {
	
	private static final String cardDirName = "Cards";//Name of folder that contains all the cards
	private static File cardDir;
	
	/**
	 * Searches in the parent of the directory the program is running in for the card directory
	 * @return True if successfully found Card folder
	 */
	private static boolean findCards() {
		try {
			//find the program's running directory and it's parent directory
			File runningDirectory = new File(FlashCardsMain.class.getProtectionDomain()
					.getCodeSource().getLocation().toURI().getPath());
			runningDirectory = runningDirectory.getParentFile();

			//searches through the parent directory for the card folder
			File[] runDirectoryContents = runningDirectory.listFiles();
			for(int i = 0; i < runDirectoryContents.length; i++){
				if(runDirectoryContents[i].getName().equals(cardDirName)){
					cardDir = runDirectoryContents[i];
					return true;
				}
			}
			//if it couldn't find the card folder, displays an error on the UI
			displayError("<html><b>ERROR:</b> Card directory not in running directory"
					+ "<br>Make sure folder \"" + cardDirName + "\" is in same folder as "
					+ "FlashCards program</html>");
			return false;

		} catch (URISyntaxException e) {
			displayError("<html><b>ERROR:</b> when finding program directory to find "
					+ "cards:<br>" + e.getMessage() +"</html>");
			return false;
		}

	}
	
	private static void displayError(String errorMsg) {
		JFrame errorFrame = new JFrame("FlashCards ERROR");
		errorFrame.add(new ErrorPanel(errorMsg));
		errorFrame.pack();
		errorFrame.setVisible(true);
	}

	public static void main(String[] args){
		if(findCards()){
			new Controller(cardDir);
		}
	}

}
