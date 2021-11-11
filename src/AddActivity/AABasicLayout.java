package AddActivity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class AABasicLayout extends JFrame{
	
	private static final long serialVersionUID = -2623284721099494039L;
	private AAInput addInput;
	private JTextArea textArea;
	
	public AABasicLayout(String title) {
		super(title);
		
		setLayout(new BorderLayout());
		
		//area that displays text
		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		add(scrollPane);
		
		textArea.setVisible(true);
		//prevents text area form being edited
		textArea.setEditable(false);
		
		addInput = new AAInput();
		
		//listens for sent text
		addInput.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent event) {
				String text = event.getText();
				String[] parts = text.split(";");
				String date, name, time, colour;
				
				if(parts.length >= 3) {
					date = parts[0];
					name = parts[1];
					time = parts[2];
					if(parts.length == 4) {
						colour = parts[3];
						text = date + " : " + name + ": " + time + " hours (" + colour + ")\n";

					}else {
						text = date + " : " + name + ": " + time + " hours\n";
					}
					motivate(date, name, time);
				}
				
				//sends text to display area
				textArea.append(text);
			}
		});				
		
		scrollPane.setPreferredSize(new Dimension(400, 100));
		addInput.setPreferredSize(new Dimension(400, 200));		
		
		//creates grid layout
		setLayout(new GridBagLayout());	
				
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = 0;
		gc.weighty = 0.5;
		
		//// first column (labels)
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		add(scrollPane, gc);
		// second row
		gc.gridx = 0;
		gc.gridy = 1;
		add(addInput, gc);
		
	}
	
	private void motivate(String date, String name, String time) {
		//int progress = getProgress();/////////////////////////////////////////
		Random rand = new Random();
		int  progress = rand.nextInt(100);
		
		
		
		
		if(progress >= 100) {
			failMessage(name);
		}
		else {
			succMessage(name, progress);
		}
	}
	
	private void failMessage(String name) {
		String message = "You fool. You've failed horrribly at you goal for " + name + "\n";
		
		AAWindow.message(message);
	}
	
	private void succMessage(String name, int progress) {
		String message;
		if(progress < 50) message = "Well done my dude, you are currently at " + progress + "% of you're goal\n";
		else if (progress < 80) message = "Uh oh, be careful. You are close to failing your goal. Reduce your useage of " + name + "\n";
		else message = "Oman";
		
		AAWindow.message(message);
	}
}
