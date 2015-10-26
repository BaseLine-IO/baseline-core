package baseline.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import baseline.collections.Indexable;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

@XmlAccessorType(XmlAccessType.FIELD)
public class Index  extends ModelObject implements Indexable{
	@XmlAttribute String Name;
	@XmlAttribute String Type;
	@XmlAttribute(name="Unique") Boolean IS_Unique = false;
	@XmlAttribute(name="ASC") Boolean IS_AscDescend = true;
	@XmlElement(name="Column") protected LinkedList<String> Columns;
	
	public Index(String name) {
		Name = name;
		Columns = new LinkedList<String>();
	}
	
	public Index() {

	}
	
	public void setIndex(
			String type,
			Boolean is_Unique,
			Boolean is_AscDescend
		){
				Type = type;
				IS_Unique = is_Unique;
				IS_AscDescend = is_AscDescend;
		};
		
	public void addColumn(String columnName, String expr){
		if(expr != null){
			Columns.add(expr);
		}else{
			Columns.add(columnName);
		}
	}

	public final String getName() {
		return Name;
	}

	public final String getType() {
		return Type;
	}

	public final Boolean IS_Unique() {
		return IS_Unique;
	}

	public final Boolean IS_AscDescend() {
		return IS_AscDescend;
	}

	public final LinkedList<String> getColumns() {
		return Columns;
	}	
	
	public int hashCode(){
		return Hashing.combineOrdered(Arrays.asList(
				Hashing.goodFastHash(32).hashString(Name+Type+(IS_Unique?"+":"-")+(IS_AscDescend?"+":"-"),Charsets.UTF_8),
				HashCodes.fromInt(new HashSet(this.Columns).hashCode())
		)).asInt();
	}

	

	@Override
	public ModelObjectTypes getObjType() {
		return ModelObjectTypes.INDEX;
	}

	@Override
	public String getKeyForIndex() {
		return Name;
	}

	
}
