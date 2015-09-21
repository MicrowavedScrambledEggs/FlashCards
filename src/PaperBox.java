import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PaperBox {
	
	private File paperDirectory;
	private ArrayList<TopicBox> paperTopics = new ArrayList<TopicBox>();
	
	public PaperBox(File paperDirectory){
		this.paperDirectory = paperDirectory;
		buildPaperTopics();	
	}

	private void buildPaperTopics() {
		List<File> paperSubDirectoryList = Arrays.asList(paperDirectory.listFiles());
		for(File subDirectory : paperSubDirectoryList){
			paperTopics.add(new TopicBox(subDirectory));
		}
	}
	
	public ArrayList<String> topicNameList(){
		ArrayList<String> topicNameList = new ArrayList<String>();
		for(TopicBox topic : paperTopics){
			topicNameList.add(topic.getTopicName());
		}
		return topicNameList;
	}
	
	public TopicBox getTopic(int index){
		return paperTopics.get(index);
	}
	
	public String getPaperName(){
		return paperDirectory.getName();
	}
	
}
