package AddActivity;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

public class AAInput extends JPanel {

	private static final long serialVersionUID = -7794849179803567024L;
	private EventListenerList listenerList = new EventListenerList();
	private int maxInLen = 20;

	public AAInput() {

		setBorder(BorderFactory.createTitledBorder("Tracking"));

		// labels next to text box
		JLabel nameLabel = new JLabel("Name of service: ");
		JLabel timeLabel = new JLabel("Time spent (hours): ");
		JLabel dateLabel = new JLabel("Date (dd/mm/yyyy): ");
		JLabel colourLabel = new JLabel("Colour (hex): ");

		// text boxes where user would type in data. size of text bar = 20
		final JTextField nameField = new JTextField(20);
		final JTextField timeField = new JTextField(20);
		final JTextField dateField = new JTextField(20);
		final JTextField colourField = new JTextField(20);

		// button name
		JButton addBtn = new JButton("Enter");

		// listens for 'enter' button to be pressed
		addBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//input text
				String name = nameField.getText();
				String time = timeField.getText();
				String date = dateField.getText();
				String colour = colourField.getText();
				String output = date + ";" + name + ";" + time + ";" +  colour;
				
				if(validInput(name, time, date, colour)) {
					//sends string so it can be printed on screen
					fireDetailEvent(new DetailEvent(this, output));
				}
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
		// first row
		gc.gridx = 0;
		gc.gridy = 0;
		add(nameLabel, gc);
		// second row
		gc.gridx = 0;
		gc.gridy = 1;
		add(timeLabel, gc);
		//third row
		gc.gridx = 0;
		gc.gridy = 2;
		add(dateLabel, gc);
		//forth row
		gc.gridx = 0;
		gc.gridy = 3;
		add(colourLabel, gc);

		//aligns text boxes to the left
		gc.anchor = GridBagConstraints.LINE_START;
		//// second column (text boxes)
		// first row
		gc.gridx = 1;
		gc.gridy = 0;
		add(nameField, gc);
		// second row
		gc.gridx = 1;
		gc.gridy = 1;
		add(timeField, gc);
		//third row
		gc.gridx = 1;
		gc.gridy = 2;
		add(dateField, gc);
		//forth row
		gc.gridx = 1;
		gc.gridy = 3;
		add(colourField, gc);
		//fifth row
		gc.gridx = 1;
		gc.gridy = 4;
		add(addBtn, gc);
	}

	private boolean validInput(String name, String time, String date, String colour) {
		return validName(name) && validTime(time) && validDate(date) && validColour(colour);
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
		return false;
	}
	
	private boolean validDate(String date) {
		if(checkLength(date, "date")) {
		
			String[] dateParts = date.split("/");
			
			//checks if date is made up of 3 parts
			if(dateParts.length != 3) {
				fireDetailEvent(new DetailEvent(this, "date entered is not of the form dd/mm/yyyy\n"));
				return false;
			}
			
			//checks if each part is an int
			for(int i = 0; i < 2; i++) {
				if(!isInt(dateParts[i])) {
					fireDetailEvent(new DetailEvent(this, "date entered is not valid\n"));
					return false;
				}
			}
			
			//splits date input into parts
			int day = Integer.parseInt(dateParts[0]);
			int month = Integer.parseInt(dateParts[1]);
			int year = Integer.parseInt(dateParts[2]);
			
			//checks is year is valid
			if(year<1000) {
				fireDetailEvent(new DetailEvent(this, "year is too small, should be entered as yyyy\n"));
				return false;
			}
			//checks if day and month numbers are valid
			if(day<=31 && month<=12) {
				return true;
			}
		}
		fireDetailEvent(new DetailEvent(this, "date entered is not valid\n"));
		return false;
	}
	
	private boolean validColour(String colour) {
		//check length is 6 || 0
		if(!(colour.length() == 0 || colour.length() == 6)) {
			fireDetailEvent(new DetailEvent(this, "colour entered is not valid\n"));
			return false;
		}
		
		
		//check each char is valid hex char
		
		
		
		return true;
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
	
	//checks if string is an int
	private boolean isInt(String num) {
		for(int i = 0; i < num.length(); i++) {
			if(Character.digit(num.charAt(i), 10) < 0) return false;
		}
		return true;
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

	// removes text that has been entered to list
	public void removeDetailListener(DetailListener listener) {
		listenerList.remove(DetailListener.class, listener);
	}
	//////////
}
