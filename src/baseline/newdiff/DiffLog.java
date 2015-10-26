package baseline.newdiff;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class DiffLog {
	private HashSet<DiffLogItem> diffLog = new HashSet<DiffLogItem>();
	
	public void ADD(Object obj){
		diffLog.add(new DiffLogItem(DiffTypes.ADD, obj, obj, " "));
	}
	
	public void MODIFY(Object obj,String field){
		diffLog.add(new DiffLogItem(DiffTypes.MODIFY, obj, obj, field));
	}
	
	public void REMOVE(Object obj){
		diffLog.add(new DiffLogItem(DiffTypes.REMOVE, obj, obj, " "));
	}
	
	public void INSERT(Object obj, List<Integer> listids){
		diffLog.add(new DiffLogItem(DiffTypes.INSERT, obj, listids));
	}
	
	public void UPDATE(Object obj, List<Integer> listids){
		diffLog.add(new DiffLogItem(DiffTypes.UPDATE, obj, listids));
	}
	
	public Object[] toArray() {
		return diffLog.toArray();
	}


	public <T> T[] toArray(T[] a) {
		return diffLog.toArray(a);
	}
	
	public Iterator<DiffLogItem> iterator() {
		return diffLog.iterator();
	}
	
	public int size() {
		return diffLog.size();
	}
}
