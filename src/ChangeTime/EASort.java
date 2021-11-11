package ChangeTime;

public class EASort{
	
	String[][] table;
	
	public String[][] sortTimes(String[][] table, int num) {
		this.table = table;
		
		boolean swap = true;
		int row = table.length;
		
		do{
			swap = false;
			
			for(int i = 0; i < row-1; i++) {
				double value1 = Double.parseDouble(table[i][1]);
				double value2 = Double.parseDouble(table[i+1][1]);
				if(num == 1) {
					if(value1 < value2) {
						String[] temp = table[i];
						table[i] = table[i+1];
						table[i+1] = temp;
						swap = true;
					}
				}else {
					if(value1 > value2) {
						String[] temp = table[i];
						table[i] = table[i+1];
						table[i+1] = temp;
						swap = true;
					}
				}
			}
		}while(swap);
		return table;
	}
}