import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Panel to contain UI features relating to topic selection for the deck
 * 
 * Main difference to a regular JPanel is that it is designed for specifically three 
 * components in one row with a 7:4:7 ratio for their widths
 * 
 * @author Badi James
 */
public class TopicSelectionPanel extends JPanel {
	
	private JPanel topicButtonPanel;
	private JScrollPane topicsAvailable;
	private JScrollPane topicsSelected;
	private int divisionUnit = 18;
	private int listProportion = 7;
	private int buttonProportion = 4;
	
	/**
	 * Constructor for class TopicSelectionPanel. Initialises a JPanel and then adds the
	 * three parameters to it along one row.
	 * 
	 * @param topicsAvailable ScrollPane containing list to display topics available
	 * @param buttonPanel Panel containing buttons for selecting topics
	 * @param topicsSelected ScrollPane containing list to display topics selected
	 */
	public TopicSelectionPanel(JScrollPane topicsAvailable, JPanel buttonPanel, 
			JScrollPane topicsSelected){
		super();
		setLayout(new BorderLayout());
		this.topicsAvailable = topicsAvailable;
		this.topicButtonPanel = buttonPanel;
		this.topicsSelected = topicsSelected;
		add(topicsAvailable, BorderLayout.WEST);
		add(buttonPanel, BorderLayout.CENTER);
		add(topicsSelected, BorderLayout.EAST);
	}
	
	@Override
	public void setPreferredSize(Dimension d){		
		super.setPreferredSize(d);
		readjustComponentsToNewWidth(d.width, d.height);
	}
	
	@Override
	public void setSize(Dimension d){
		super.setSize(d);
		readjustComponentsToNewWidth(d.width, d.height);
	}
	
	@Override
	public void setSize(int width, int height){
		super.setSize(width, height);
		readjustComponentsToNewWidth(width, height);
	}
	
	/**
	 * Enforces 7:4:7 component width ratio when this JPanel's dimensions change
	 * @param width new width of JPanel
	 * @param height new height of JPanel
	 */
	private void readjustComponentsToNewWidth(int width, int height) {
		int widthDivision = width/divisionUnit;
		int listWidth = widthDivision*listProportion;
		int buttonWidth = widthDivision*buttonProportion;
		Dimension listDimension = new Dimension(listWidth, height);
		Dimension buttonDimension = new Dimension(buttonWidth, height);
		this.topicsAvailable.setSize(listDimension);
		this.topicsAvailable.setPreferredSize(listDimension);
		this.topicsSelected.setSize(listDimension);
		this.topicsSelected.setPreferredSize(listDimension);
		this.topicButtonPanel.setSize(buttonDimension);
		this.topicButtonPanel.setPreferredSize(buttonDimension);
	}
	
	@Override
	public void repaint(){
		//Only adjust components with if the panel actually has a size
		if(this.getWidth() != 0 && this.getHeight() != 0){
			readjustComponentsToNewWidth(this.getWidth(), this.getHeight());
		}
		super.repaint();
	}

}
