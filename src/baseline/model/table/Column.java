package baseline.model.table;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import baseline.collections.Indexable;
import baseline.model.ModelObject;
import baseline.model.ModelObjectTypes;
import baseline.model.Table;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;


@XmlAccessorType(XmlAccessType.PROPERTY)
public class Column  extends ModelObject implements Indexable  {
	String Name;
	public ColumnDataType Type;
	public String Ref;
	transient Integer OrderNum;
	public Boolean NULLABLE = null;
	

	public String DefaultValue;
	public String Comment;

	public Column(String name) {
			      Name = name;
			      Type = new ColumnDataType();
			      Type.setParent(this);
	}
	
	public Column() {
		Type = new ColumnDataType();
	    Type.setParent(this);
	}
	
	
	public void setColumn(
			String type,
			Integer orderNum,
			Integer size,  // for ALL
			Integer scale, // for NUMBER
			String  units, // CHAR or BYTE
			Boolean is_NULLABLE,
			String defaultValue,
			String comment
			
		){
		 Type.setName(type);
         OrderNum = orderNum;
         Type.setSize(size);
         Type.setScale(scale);
         Type.setUnits(units);
         if(is_NULLABLE == null) NULLABLE = true; else NULLABLE = is_NULLABLE;
         if(defaultValue != null) DefaultValue = defaultValue.trim();
         Comment =  comment;
	}
    
	@XmlAttribute
	public final String getName() {
		return Name;
	}
	
	public void setName(String name){
		Name = name;
	}
	
	@XmlAttribute
	public String getRef() {
		return Ref;
	}
	
	public void setRef( String _ref){
		Ref = _ref;
	}
	
	@XmlAttribute
	public String getType() {
		return Type.getName();
	}
	
	@XmlAttribute
	public final Integer getSize() {
		return Type.getSize();
	}
	
	@XmlAttribute
	public final Integer getScale() {
		return Type.getScale();
	}
	
	@XmlAttribute
	public final String getUnits() {
		return Type.getUnits();
	}
	
	public void setType(String name) {
		Type.setName(name);
	}

	public void setSize(Integer size) {
		Type.setSize(size);
	}

	public void setScale(Integer scale) {
		Type.setScale(scale);
	}

	public void setUnits(String units) {
		Type.setUnits(units);
	};

	
	public Boolean IS_NULLABLE() {
		return NULLABLE;
	}
	
	@XmlAttribute
	public Boolean getNullable(){
		return NULLABLE;
	}
	
	public void setNullable(Boolean nullable){
		NULLABLE = nullable;
	}
	
	@XmlElement(name = "DefaultValue")
	public String getDefaultValue() {
		return DefaultValue;
	}
	
	public void setDefaultValue(String defaultvalue){
		DefaultValue = defaultvalue;
	} 
	
	@XmlElement(name = "Comment")
	public  String getComment() {
		return Comment;
	}
	
	public void setComment(String comment){
		Comment = comment;
	}
	
	public int hashCode(){
		return Hashing.combineOrdered(Arrays.asList(
				 Hashing.goodFastHash(32).hashString(Joiner.on(".").useForNull("-1").join(Name,Ref,NULLABLE,DefaultValue,Comment)),
				 HashCodes.fromInt(Type.hashCode())
		)).asInt();
	}
	
	

	@Override
	public ModelObjectTypes getObjType() {
		return ModelObjectTypes.COLUMN;
	}

	@Override
	public String getKeyForIndex() {
		return Name;
	}

	
}