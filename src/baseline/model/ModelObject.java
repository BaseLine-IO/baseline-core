package baseline.model;


import baseline.utils.collections.SetParent;
import baseline.model.types.ModelObjectTypes;

public abstract class ModelObject {
	
	public abstract ModelObjectTypes getObjType();
	public abstract String getName();
	
	
	protected transient ModelObject parent;

	@SetParent
	public void setParent(Object p) {
		parent = (ModelObject) p;
	
	}
	
	public ModelObject getParent() {
		return parent;
	}
	

	@Override
	public boolean equals(Object obj){
		if(obj.getClass() != this.getClass()) return false;
	    return obj.hashCode() == this.hashCode();
	}
	


}
