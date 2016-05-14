package baseline.model;


import baseline.model.types.ModelObjectTypes;
import baseline.utils.collections.SetParent;

public abstract class ModelObject {
	
	protected transient ModelObject parent;

	public abstract ModelObjectTypes getObjType();

	public abstract String getName();

	public ModelObject getParent() {
		return parent;
	}

	@SetParent
	public void setParent(Object p) {
		parent = (ModelObject) p;

	}
	
	@Override
	public boolean equals(Object obj){
		if(obj.getClass() != this.getClass()) return false;
	    return obj.hashCode() == this.hashCode();
	}
	


}
