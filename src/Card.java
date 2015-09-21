import java.util.*;
import java.io.*;
/**
 * Reads flash card files and makes Card objects out of them
 *
 * @author: Badi James
 * @version: 1.0 (don't know version conventions
 */
public class Card
{
	private String paper;
	private String topic;
	private String subTopic;
	private File cardFile;
	private boolean isFlipped;

	/**
	 * Constructor for objects of class Card
	 */
	public Card(File cardFile)
	{
		this.cardFile = cardFile;
		try{
			FileReader sc =  new FileReader(this.cardFile);
			BufferedReader rd = new BufferedReader(sc);
			this.paper = rd.readLine();
			this.topic = rd.readLine();
			this.subTopic = rd.readLine();
			sc.close();
		}
		catch(IOException e){
			System.out.println("Card creation failed: " + e);
		}
		catch(NoSuchElementException e){
			System.out.println(cardFile.getName() + " not formated propery: " + e);
			throw e;
		}
	}

	/**
	 * Gets the paper this flashcard is for
	 * */
	public String getPaper()
	{
		return this.paper;
	}

	/**Gets the topic of the card*/
	public String getTopic(){
		return this.topic;
	}

	/**Gets the subtopic of the card*/
	public String getSubtopic(){
		return this.subTopic;
	}
	
	public void flip(){
		isFlipped = !isFlipped;
	}
	
	public void setFlipped(boolean flipState){
		isFlipped = flipState;
	}
	
	public String viewCard(){
		if(isFlipped){
			return getBack();
		} else {
			return getFront();
		}
	}
	
	/**
	 * @return String containing the paper, topic and subtopic this card belongs to
	 */
	public String getFront(){
		return String.format("%s\n\n%s\n\n%s", this.paper, this.topic, this.subTopic);
	}

	public String getBack(){

		try{
			FileReader fr =  new FileReader(this.cardFile);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();
			br.readLine();
			String back = br.readLine();
			String toAdd = br.readLine();
			while(toAdd != null){
				back = String.format("%s\n%s", back, toAdd);
				toAdd = br.readLine();
			}
			fr.close();
			return back;
		}
		catch(IOException e){
			String toReturn = "ERROR: Reading of card file " + this.cardFile.getPath() 
					+ " failed: " + e;
			return toReturn;
		}
		catch(NoSuchElementException e){
			String toReturn = "ERROR: " + cardFile.getName() + " not formated propery: " + e;
			return toReturn;
		}
	}
}
