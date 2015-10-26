package baseline.data;

import java.util.HashMap;

public interface DataManagerEvent {
	public abstract void onEntity(Integer id,Integer keyHash, HashMap<String,DataEntity> entity);
}
