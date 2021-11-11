package AddActivity;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class AAWindow {
	
	static JFrame window;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//creates new window and assigns it a name
				window = new AABasicLayout("Add activity");
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
	
	public static void message(String message) {
		JOptionPane.showMessageDialog(window, message);
	}
}
