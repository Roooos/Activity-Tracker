package ChangeTime;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class EATable extends JPanel{
	
	private static final long serialVersionUID = -3107812002420898415L;
	private int selectedRow, selectedCol;
	String[] columnNames;
	String[][] data;
	JTable table;
	DefaultTableModel model;

	//default table constructor 
	public EATable() {
		super(new GridLayout(1, 0));
		columnNames = new String[] {"Service", "Time spent (hrs)", "Date"};
		data = new String[][] {
			{"reddit", "6.2", "25/12/2017"},
			{"reddit", "8.0", "25/12/2017"},
			{"Youtube", "5.5", "24/12/2017"},
			{"reddit", "4.0", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "1.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "3.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "2.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "7.5", "24/12/2017"},
			{"facebook", "0.5", "24/12/2017"},
			{"facebook", "5.5", "24/12/2017"}
		};
		createTable();
	}
	
	
    
	//table constructor with string array as the parameter 
	public EATable(String[] columnNames, String[][] data) {
		super(new GridLayout(1, 0));
		this.columnNames = columnNames;
		this.data = data;
		createTable();
	}
	
	//creates the table
	public void createTable() {
		
		selectedRow = -1;
		selectedCol = -1;
		
		model =  new DefaultTableModel(data, columnNames);
		table = new JTable(model)
		{
		    /**
			 * 
			 */
			private static final long serialVersionUID = 4336822688064885222L;

			public boolean isCellEditable(int row, int column)
		    {
		        return false;
		    }
		};
		
		//table size
		table.setPreferredScrollableViewportSize(new Dimension(450, 180)); //size could maybe be added in constructor, but since it depends on the size of the window and texbox area I've left it out for now
		//highlights cells when they are selected
		table.setCellSelectionEnabled(true);
		
		ListSelectionModel rowSelMod = table.getSelectionModel();
		ListSelectionModel colSelMod = table.getColumnModel().getSelectionModel();
		
		//adds row and column listeners to the table
		addRowListen(rowSelMod);
		addColListen(colSelMod);
		
		//adds scroll bar if enough entries are added
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
	
	
	public String[][] getTable() {
		return data;
	}
	
	/////////
	private void addRowListen(ListSelectionModel rowSM) {
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
					selectedRow = -1;
				} else {
					selectedRow = lsm.getMinSelectionIndex();
				}
			}
		});		
	}
	
	////////
	private void addColListen(ListSelectionModel colSM) {
		colSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
					selectedCol = -1;
				} else {
					selectedCol = lsm.getMinSelectionIndex();
				}
			}
		});
	}
	
	public int getRow() {
		return selectedRow;
	}
	
	public int getCol() {
		return selectedCol;
	}
	
	public void addRow(String col1, String col2, String col3) {
		model.addRow(new Object[]{col1, col2, col3});
	}
	
	public void deleteRow(int i) {
		model.removeRow(i);
	}
	
	public int rowCount() {
		return table.getRowCount();
	}
	
	public void setData(String newData, int row, int col){
		data[row][col] = newData;
		table.setValueAt(newData, row, col);
	}
	
	public void setTableArr(String[][] newData) {
		data = newData;
	}
}