package baseline.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import baseline.utils.collections.AllowedForIndexing;
import baseline.utils.collections.KeyForIndex;
import baseline.model.types.ModelObjectTypes;
import com.google.common.hash.Hashing;

import baseline.newdiff.DiffData;
import baseline.newdiff.DiffThis;

@XmlAccessorType(XmlAccessType.FIELD)
@AllowedForIndexing
public class Data extends ModelObject{
	
	   @DiffThis(DiffData.class)
	   @XmlAttribute

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
	@KeyForIndex
	public String getName() {
		return TableName;
	}

	
	public int hashCode(){	
		return Hashing.combineOrdered(Arrays.asList(
				 Hashing.goodFastHash(32).hashString(TableName+URL)
		)).asInt();
	}

}
