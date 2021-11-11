package Goals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GBasicLayout extends JFrame{
	
	private static final long serialVersionUID = 6212491297103981473L;
	public GInput addGoalInput;
	private static GTable table;
	
	public GBasicLayout(String title) {
		super(title);
		
		setLayout(new BorderLayout());	
		
		//area that displays text
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// button name
		JButton addBtn = new JButton("Delete");
		
		add(scrollPane);
		
		//prevents text area form being edited
		textArea.setEditable(false);
		
		setTable(new GTable());
		addGoalInput = new GInput();		
		
		//listens for sent text
		addGoalInput.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent event) {
				String userInput = event.getText();
				String[] inputParts = userInput.split(";");
				String name, goal;
				String progress = getProgress() + "%";
				
				if(inputParts.length >= 3) {
					name = inputParts[0];
					goal = inputParts[1] + " hours per " + inputParts[2];
						
					getTable().addRow(name, goal, progress);
					textArea.append(name + ": "+ goal + "\n");
				}else {
					textArea.append(userInput);
				}
				
				
			}
		});
		
		// listens for 'delete' button to be pressed
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getTable().deleteEntries();
				
			}
		});
		
		scrollPane.setPreferredSize(new Dimension(450, 50));
		//size of box surrounding input text box
		addGoalInput.setPreferredSize(new Dimension(450, 100));
		
		//creates grid layout
		setLayout(new GridBagLayout());	
				
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0;
		gc.weighty = 0.5;
		
		//// first column (labels)
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		add(addGoalInput, gc);
		// second row
		gc.gridx = 0;
		gc.gridy = 1;
		add(getTable(), gc);
		gc.anchor = GridBagConstraints.LINE_END;
		// third row
		gc.gridx = 0;
		gc.gridy = 2;
		add(addBtn, gc);
		// forth row
		gc.gridx = 0;
		gc.gridy = 3;
		add(scrollPane, gc);
	}

	public int getProgress() {
		//get progress from database as int (rounded)
		// = (actual time per specified time / goal time) * 100
		//eg if user has spent 12 hours on reddit over the last week and has goal for 24 hours per week then progress is 50%
		Random rand = new Random();
		int  progress = rand.nextInt(100);
		return progress;
	}
	
	public static GTable getTable() {
		return table;
	}

	public void setTable(GTable table) {
		GBasicLayout.table = table;
	}
}
