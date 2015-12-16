package baseline.database.ddl;

import baseline.model.Index;
import baseline.model.ModelObject;

public class IndexDDL extends DDL {
	
	private Index index;

	public IndexDDL(ModelObject _Object) {
		super(_Object);
		index = (Index) _Object;
	}
	
	@Override
	public String CREATE() {
		String cols = join(index.getColumns().iterator());
		return Join("CREATE", ifTrue(index.IS_Unique(),"UNIQUE"),"INDEX",index.getName(), "ON", Parent.getName(), "(", cols, ")",delimiter);
	}

}
