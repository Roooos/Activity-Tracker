package Goals;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class GTable extends JPanel{
	
	private static final long serialVersionUID = -3107812002420898415L;
	private int selectedRow, selectedCol;
	String[] columnNames;
	Object[][] data;
	JTable table;
	DefaultTableModel model;

	//default table constructor 
	public GTable() {
		super(new GridLayout(1, 0));
		columnNames = new String[] {"Service", "Goal", "Progress", "Delete goal"};
		data = new Object[][] {
			{"reddit", "6 hours per day", "0%", false},
			{"Youtube", "50 hours per week", "26%", false},
			{"facebook", "2 hours per day", "14%", false},
			{"facebook", "30 hours per week", "85%", false},
			{"twitter", "100 hours per month", "58%", false}
		
		};
		createTable();
	}
	
	public GTable(Object[][] data) {
		super(new GridLayout(1, 0));
		columnNames = new String[] {"Service", "Goal", "Progress", "Delete goal"};
		data = this.data;
		createTable();
	}
	
	//table constructor with string array as the parameter 
	public GTable(String[] columnNames, String[][] data) {
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
		table = new JTable(model) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 6205734870186095528L;

			@Override
	         public Class getColumnClass(int column) {
				 switch (column) {
				 	case 0:
				 		return String.class;
	                case 1:
	                    return String.class;
	                case 2:
	                    return String.class;
	                 default:
	                    return Boolean.class;
	                }
	            }
		  
			 public boolean isCellEditable(int row, int column){
			  if(column == 3) return true;
			  return false;
		  }
		  
			 public Component prepareRenderer(
				        TableCellRenderer renderer, int row, int column)
				    {
				        Component c = super.prepareRenderer(renderer, row, column);

				        if(column == 1) {//feel like it should be == 2 but that's wrong
							Color cellColour = new Color(getProgress(row)*2 + 55, 255-getProgress(row)*2, 50);
							setBackground(cellColour);
							
				        }else setBackground(Color.WHITE);
				       

				        return c;
				    }
		};
		
		table.setOpaque(true);
		
		//table size
		table.setPreferredScrollableViewportSize(new Dimension(450, 100));
		//highlights cells when they are selected
		table.setCellSelectionEnabled(true);
		
		//sets widths of columns
		TableColumn column = null;
		for (int i = 0; i < 4; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 1) column.setPreferredWidth(100);
			else column.setPreferredWidth(50);
		}
		
		ListSelectionModel rowSelMod = table.getSelectionModel();
		ListSelectionModel colSelMod = table.getColumnModel().getSelectionModel();
		
		//adds row and column listeners to the table
		addRowListen(rowSelMod);
		addColListen(colSelMod);
		
		//adds scroll bar if enough entries are added
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);		
	}
	
	//gets progress from table
	private int getProgress(int i){
		String str = (String) model.getValueAt(i, 2);
		//removes '%'
		str = str.replace("%", "");
		int value = Integer.parseInt(str);
		if(value > 100) value = 100;
		//returns int value of progress
		return value;
	}
	  
	public int getRow() {
		return selectedRow;
	}
	
	public int getCol() {
		return selectedCol;
	}
	
	/*
	//changes data in table
	public void setData(String newData, int row, int col){
		data[row][col] = newData;
		table.setValueAt(newData, row, col);
	}
	*/
	
	public void addRow(String col1, String col2, String col3) {
		model.addRow(new Object[]{col1, col2, col3});
	}
	
	public void deleteEntries(){
		int count =  table.getRowCount();
		for(int i = 0; i < table.getRowCount(); i++) {
			if((boolean) model.getValueAt(i, 3)) {////once got an error, haven't bee able to recreate it
				model.removeRow(i);
				i--;
				/////////add remove from database function
				
				
				
			}
		}
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
}

