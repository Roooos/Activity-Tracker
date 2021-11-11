////////////////
////change sort label when sorting
////////////////


package ChangeTime;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class EABasicLayout extends JFrame{
	
	private static final long serialVersionUID = 6212491297103981473L;
	private static EATable table;
	private EASort sort;
	int i;
	
	public EABasicLayout(String title) {
		super(title);
		i=1;
		
		setLayout(new BorderLayout());	
		
		sort = new EASort();
		
		setTable(new EATable());
		
		// button name
		JButton sortBtn = new JButton("Sort by time (decreasing)");
		
		//listens for sent text
		// listens for 'sort' button to be pressed
		sortBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows = table.rowCount();
				for (int i = 0; i < rows; i++) {
				    table.deleteRow(0);
				}
				
				String[][] sorted = sort.sortTimes(getTableArr(), i);
				i*=-1;
				
				for(int i = 0; i < sorted.length; i++) {
					table.addRow(sorted[i][0], sorted[i][1], sorted[i][2]);	
				}
				table.setTableArr(sorted);
				
				if(i == 1) sortBtn.setText("Sort by time (decreasing)");
				else sortBtn.setText("Sort by time (increasing)");
			}
		});	
		
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
		add(table, gc);
		// second row
		gc.gridx = 0;
		gc.gridy = 1;
		add(sortBtn, gc);
	}
	
	public String[][] getTableArr() {
		return table.getTable();
	}
	
	public static EATable getTableObj() {
		return table;
	}
	
	public void setTable(EATable table) {
		EABasicLayout.table = table;
	}
}
