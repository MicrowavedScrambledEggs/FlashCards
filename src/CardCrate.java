import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CardCrate {
	
	private File cardDirectory;
	private ArrayList<PaperBox> cardPapers = new ArrayList<PaperBox>();
	
	public CardCrate(File cardDirectory){
		this.cardDirectory = cardDirectory;
		buildPaperTopics();	
	}

	private void buildPaperTopics() {
		List<File> crateSubDirectoryList = Arrays.asList(cardDirectory.listFiles());
		for(File subDirectory : crateSubDirectoryList){
			cardPapers.add(new PaperBox(subDirectory));
		}
	}
	
	public ArrayList<String> paperNameList(){
		ArrayList<String> paperNameList = new ArrayList<String>();
		for(PaperBox paper : cardPapers){
			paperNameList.add(paper.getPaperName());
		}
		return paperNameList;
	}
	
	public PaperBox getPaper(int index){
		return cardPapers.get(index);
	}
	
}
