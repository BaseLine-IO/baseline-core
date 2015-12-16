package baseline.newdiff;

import java.util.LinkedList;
import java.util.TreeSet;

import baseline.data.DataItem;
import baseline.data.DataManager;
import baseline.exceptions.DiffException;
import baseline.model.Data;

public class DiffData<T> extends Diff<T> implements DiffStrategy<T>{
	
	public DiffData() {
		
	}

	
	@Override
	public void compare(DiffLog log) throws DiffException{
		Log = log;
		
		TreeSet<DataItem> source = null;
		TreeSet<DataItem> target = null;
			try {
				source = DataManager.loadCompareTree((Data) Right);
			} catch (Exception e) {
				DiffException newex = new DiffException(e);
				throw newex;
			} 

			try {
				target = DataManager.loadCompareTree((Data) Left);
			} catch (Exception e) {
				DiffException newex = new DiffException(e);
				throw newex;
			}
			LinkedList<Integer> update_list = new LinkedList<Integer>();
			for(DataItem d : source){
				if(!target.add(d)){
					if(d.all != target.ceiling(d).all) update_list.add(d.id);
				}else{
					target.remove(d);
				}
			}
			if(!update_list.isEmpty()){
				Log.UPDATE(Right, update_list);
			}
			source.removeAll(target);
			if(!source.isEmpty()){
				LinkedList<Integer> insert_list = new LinkedList<Integer>();
				for(DataItem d : source){
					insert_list.add(d.id);
				}
				
				Log.INSERT(Right,insert_list);
			}
			
			
	
	}
}
