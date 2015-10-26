package baseline.data;

import java.util.LinkedList;

public class DataItem implements Comparable<DataItem>{
	public int key = 0; // Hash of primary key or unique index
	public int all = 0; // Hash of all data in row
	public int id = -1; // Number that representing unique row key in xml file
	
	@Override
	public int compareTo(DataItem o) {
		if(key == o.key) return 0;
		if(key > o.key) return 1; else return -1;
	}
	
	
	
	
}
