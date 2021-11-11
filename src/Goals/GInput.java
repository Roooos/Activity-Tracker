package Goals;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

import Goals.DetailEvent;

public class GInput extends JPanel {
	
	private static final long serialVersionUID = -4306205129896723333L;
	private EventListenerList listenerList = new EventListenerList();
	private int maxInLen = 20;
	String message;
	
	
	public GInput() {

		//default message fir if drop down isn't used
		message = "day";
		
		setBorder(BorderFactory.createTitledBorder("Add Goal"));
				
		// labels next to text box
		JLabel nameLabel = new JLabel("Name of service: ");
		JLabel timeLabel = new JLabel("Time (hrs): ");
		JLabel perLabel = new JLabel("per ");

		// text boxes where user would type in data. size of text bar = 15, and 10
		final JTextField nameField = new JTextField(15);
		final JTextField timeField = new JTextField(15);
		final JTextField perField = new JTextField(10);

		//adds drop down field
		String[] timePeriods = { "day", "week", "month", "year"};
		JComboBox timeSelect = new JComboBox(timePeriods);
		
		// button name
		JButton addBtn = new JButton("Enter");

		// listens for 'enter' button to be pressed
		addBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//input text
				String name = nameField.getText();
				String time = timeField.getText();
				String output = name + ";" + time + ";" + message;
				
				if(validInput(name, time)) {
					fireDetailEvent(new DetailEvent(this, output));
				}
			}
		});
		
		//listens for when drop down is used
		timeSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//message = selected item in drop down
				message = "" + ((JComboBox) e.getSource()).getSelectedItem();
			}
		});
		
		//creates grid layout
		setLayout(new GridBagLayout());
		
		////layout:
		GridBagConstraints gc = new GridBagConstraints();

		//affects how close text boxes/labels are vertically
		gc.weighty = 0.8;
		
		//aligns labels to the right
		gc.anchor = GridBagConstraints.LINE_END;
		//// first column (labels)
		//first row
		gc.gridx = 0;
		gc.gridy = 0;
		add(nameLabel, gc);
		//second row
		gc.gridx = 0;
		gc.gridy = 1;
		add(timeLabel, gc);
		//aligns text boxes to the left
		gc.anchor = GridBagConstraints.LINE_START;
		//third row
		gc.gridx = 2;
		gc.gridy = 1;
		add(perLabel, gc);
		
		//// second column (text boxes)
		// first row
		gc.gridx = 1;
		gc.gridy = 0;
		add(nameField, gc);
		//second row
		gc.gridx = 1;
		gc.gridy = 1;
		add(timeField, gc);
		//third row
		gc.gridx = 1;
		gc.gridy = 4;
		add(addBtn, gc);
		
		////third column
		//third row		
		gc.gridx = 3;
		gc.gridy = 1;
		add(timeSelect, gc);
		
	}
	
	//checks if string is a double
	private boolean isDouble(String num) {
		int decimal = 0;
		for(int i = 0; i < num.length(); i++) {
			//checks for decimal point
			if(num.charAt(i) == '.') {
				decimal++;
				//if more than one is found, the string is not a double
				if(decimal > 1) {
					return false;
				}
			}
			else if(Character.digit(num.charAt(i), 10) < 0) return false;
		}
		return true;
	}
	
	private boolean validInput(String name, String time) {
		return validName(name) && validTime(time);
	}
	
	private boolean validName(String name) {
		return checkLength(name, "name");
	}
	
	private boolean validTime(String time) {
		if(checkLength(time, "time")) {
			if(isDouble(time)) {
				 double doubleTime = Double.parseDouble(time);
				 if(doubleTime>=0 && doubleTime<=24) {
					 return true;
				 }
				 fireDetailEvent(new DetailEvent(this, "time entered is out of range\n"));
				 return false;
			}
			fireDetailEvent(new DetailEvent(this, "time entered is not valid\n"));
			return false;
		}
		fireDetailEvent(new DetailEvent(this, "time entered is not valid\n"));
		return false;
	}
	
	private boolean checkLength(String input, String inputType) {
		if(input.length() == 0) {
			fireDetailEvent(new DetailEvent(this, "input activity "+ inputType +"\n"));
			return false;
		}else if(input.length() > maxInLen) {
			fireDetailEvent(new DetailEvent(this, "activity " + inputType + " contain too many characters\n"));
			return false;
		}
		return true;
	}
	
	////////////haven't been able to find any other way of doing event listeners
	//////https://www.youtube.com/watch?v=YIbA5YH1UeA
	public void fireDetailEvent(DetailEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == DetailListener.class) {
				((DetailListener) listeners[i + 1]).detailEventOccurred(event);
			}
		}
	}

	// adds text that has been entered to list
	public void addDetailListener(DetailListener listener) {
		listenerList.add(DetailListener.class, listener);
	}
	//////////
}
