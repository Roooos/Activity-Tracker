package ChangeTime;

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

public class EAInput extends JPanel {
	
	private static final long serialVersionUID = -4306205129896723333L;
	private EventListenerList listenerList = new EventListenerList();

	public EAInput() {

		setBorder(BorderFactory.createTitledBorder("Update"));

		// labels next to text box
		JLabel inputLabel = new JLabel("New value: ");

		// text boxes where user would type in data. size of text bar = 20
		final JTextField inputField = new JTextField(20);

		// button name
		JButton addBtn = new JButton("Edit");

		inputListener(addBtn,inputField);
		
		setGridLayout(inputLabel, inputField, addBtn);	
	}
	
	private void inputListener(JButton addBtn, JTextField inputField) {
		// listens for 'enter' button to be pressed
		addBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//input text
				String input = inputField.getText();
				int row = EABasicLayout.getTableObj().getRow();
				int col = EABasicLayout.getTableObj().getCol();
				String coords = row + ";" + col;
				
				if(row>=0 && col>=0 && checkInput(input, col)) {
					String output = input + ";" + coords;
					//sends string so it can be printed on screen
					fireDetailEvent(new DetailEvent(this, output));
				}
			}
		});	
	}
	
	//check if input is valid
	private boolean checkInput(String input, int col) {
		//input is invalid if nothing is entered or is larger in length than 20 (since ~20 char fit in each cell nicely at current size)
		if(input.length() == 0 || input.length() > 20) return false;
		//checks input for ';' as this will cause the code to run incorrectly if not caught
		for(int i = 0; i < input.length(); i++) {
			if(input.charAt(i) == ';') return false;
		}
		//checks if the column selected is the time column and therefore requires a double
		if(col == 1) {
			if(!isDouble(input)) return false;
		}
		//checks if the column selected is the date column and therefore requires a valid date
		if(col == 2) {
			if(!isDate(input)) return false;
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
	
	private boolean isDate(String date) {
		String[] dateParts = date.split("/");
		
		if(dateParts.length != 3) return false;
		
		for(int i = 0; i < 2; i++) {
			if(!isInt(dateParts[i])) {
				return false;
			}
		}
		
		int day = Integer.parseInt(dateParts[0]);
		int month = Integer.parseInt(dateParts[1]);
		//int year = Integer.parseInt(dateParts[2]);
		
		return day<=31 && month<=12;
	} 
	
	private void setGridLayout(JLabel inputLabel, JTextField inputField, JButton addBtn) {
		//creates grid layout
		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//first grid column: button lable position
		gc.gridx = 0;
		gc.gridy = 0;
		add(inputLabel, gc);
		//second grid column: input text box
		gc.gridx = 1;
		gc.gridy = 0;
		add(inputField, gc);
		//third grid column: "Edit" button
		gc.gridx = 2;
		gc.gridy = 0;
		add(addBtn, gc);
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
