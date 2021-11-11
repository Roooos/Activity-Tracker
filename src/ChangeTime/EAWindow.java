package ChangeTime;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EAWindow {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//creates new window and assigns it a name
				JFrame window = new EABasicLayout("View times");
				////window settings
				//size
				window.setSize(500, 400);
				//closes window when 'x' button is pressed
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//sets visible
				window.setVisible(true);
				//disables setResizability
				window.setResizable(true);
				//sets default position to centre
				window.setLocationRelativeTo(null);
			}
		});		
	}
}
