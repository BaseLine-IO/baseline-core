package baseline.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.google.common.base.Joiner;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

import baseline.collections.Indexable;
import baseline.newdiff.DiffData;
import baseline.newdiff.DiffThis;

@XmlAccessorType(XmlAccessType.FIELD)
public class Data extends ModelObject implements Indexable{
	
	   @DiffThis(DiffData.class) @XmlAttribute 
	   public String TableName;
	   @XmlAttribute 
	   private String URL;

	public Data() {

	}
	
	
	public Data(String tableName) {
		TableName = tableName;
	}

	

	public String getURL() {
		return URL;
	}



	public void setURL(String uRL) {
		URL = uRL;
	}



	@Override
	public ModelObjectTypes getObjType() {
		
		return ModelObjectTypes.DATA;
	}
	
	

	@Override
	public String getName() {
		return TableName;
	}

	@Override
	public String getKeyForIndex() {
		return TableName;
	}
	
	public int hashCode(){	
		return Hashing.combineOrdered(Arrays.asList(
				 Hashing.goodFastHash(32).hashString(TableName+URL)
		)).asInt();
	}

}
