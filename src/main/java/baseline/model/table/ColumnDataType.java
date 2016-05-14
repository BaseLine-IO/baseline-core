package baseline.model.table;

import baseline.model.ModelObject;
import baseline.model.types.ModelObjectTypes;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;




public class ColumnDataType extends ModelObject {
	
	String Name;
	Integer Size;   // for ALL
	Integer Scale; // for NUMBER
	String  Units; // CHAR or BYTE
	
	
	@Override
	public ModelObjectTypes getObjType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int hashCode(){
		return Hashing.goodFastHash(32).hashString(
					Joiner.on(".").useForNull("-1").join(Name,Size,Scale,Units)
				,Charsets.UTF_8).asInt();
	}

	public Integer getSize() {
		return Size;
	}

	public void setSize(Integer size) {
		Size = size;
	}

	public Integer getScale() {
		return Scale;
	}

	public void setScale(Integer scale) {
		Scale = scale;
	}

	public String getUnits() {
		return Units;
	}

	public void setUnits(String units) {
		Units = units;
	};

}
