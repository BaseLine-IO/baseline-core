package baseline.newdiff;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.common.collect.BoundType;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import sbt.datapipe.DataPipe;
import baseline.core.Project;
import baseline.core.common.Input;
import baseline.core.common.InputTypes;
import baseline.data.Data2XMLWriter;
import baseline.data.DataEntity;
import baseline.data.DataItem;
import baseline.data.DataManager;
import baseline.data.Xml2DataReader;
import baseline.exceptions.DiffException;
import baseline.model.table.Column;
import baseline.model.Data;
import baseline.model.Schema;

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
