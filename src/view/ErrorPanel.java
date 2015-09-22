package view;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple panel for displaying loading errors on the UI
 * 
 * @author Badi James
 *
 */
public class ErrorPanel extends JPanel {
	
	private String errorMsg;
	private JLabel errorLbl;
	
	public ErrorPanel(String errorMsg){
		this.errorMsg = errorMsg;
		this.errorLbl = new JLabel(errorMsg);
		add(errorLbl);
		this.setPreferredSize(new Dimension(400,300));
	}
	
}
