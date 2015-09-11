import java.util.*;

import java.awt.Color;
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
    /**Prints on graphics pane the cards topic*/
    public void printCard(){
//        UI.clearGraphics();
//        UI.drawString(this.topic, 100, 70);
//        UI.drawString(this.subTopic, 100, 100);
    }
    /**Prints on graphics pane the cards contents*/
    public void flipCard(){
//        UI.clearGraphics();
        int printY = 50;
        int lnGap = 17;
        try{
            Scanner sc =  new Scanner(this.cardFile);
            sc.nextLine();
            sc.nextLine();
            while(sc.hasNext()){
//                UI.drawString(sc.nextLine(), 50, printY);
                printY += lnGap;
            }
            sc.close();
        }
        catch(IOException e){
//            UI.println("Card flipping failed: " + e);
        }
    }
}
