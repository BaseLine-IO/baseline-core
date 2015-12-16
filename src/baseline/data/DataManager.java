package baseline.data;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import baseline.database.DatabaseFabric;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

import sbt.datapipe.DataPipe;
import baseline.core.common.Input;
import baseline.core.types.InputTypes;
import baseline.model.Data;

public class DataManager {
	
		public static TreeSet<DataItem> loadCompareTree(Data data) throws Exception{
			prepareDataContent(data,null);
			
			final TreeSet<DataItem> tree = new TreeSet<DataItem>();
		
			Xml2DataReader reader = new Xml2DataReader(data.getURL(), "ID"); 
			
			reader.setEvent(new DataManagerEvent() {
				
				@Override
				public
				void onEntity(Integer id, Integer keyHash, HashMap<String, DataEntity> entity) {
					
					DataItem d = new DataItem();
					d.all = entity.values().hashCode();
					d.id = id;
					int i = 0;
					while(!tree.add(d)){
						i++;
						d.key = Hashing.combineOrdered(Arrays.asList(
								 HashCodes.fromInt(i),
								 HashCodes.fromInt(keyHash.hashCode())
						)).asInt(); 
					}
				}
			});
				
			reader.parse();
			return tree;
			
		}
		
		
		private static void prepareDataContent(Data data,File file) throws Exception{
			if(DatabaseFabric.checkURN(data.getURL())){
				if (file == null) file = File.createTempFile(data.getName(), ".xml");
				Data2XMLWriter w = new Data2XMLWriter(file.getPath());
				DatabaseFabric.fromURN(data.getURL()).select("select * from "+ data.getName()).fetch(w).close();
				data.setURL(file.getPath());
			}
		}

}
