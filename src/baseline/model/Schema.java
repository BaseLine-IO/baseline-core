package baseline.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import baseline.collections.IndexedList;
import baseline.newdiff.DiffThis;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Schema")
public class Schema extends ModelObject {
	@DiffThis
	@XmlElement(name="Table") public IndexedList<Table> Tables;
	@DiffThis
	@XmlElement(name="Data") public IndexedList<Data> Data;
	
	transient private String URL;
	
	public Schema() {
		Tables = new IndexedList<Table>(this);
		Data = new IndexedList<Data>(this);
	}
	
	public Schema(String uRL) {
		Tables = new IndexedList<Table>(this);
		Data = new IndexedList<Data>(this);
		URL = uRL;
		
	}
	
	public String URL(){
		return URL;
	}
	
	public Table getTable(String name){
		if(!Tables.containsKey(name)){
			Table t = new Table(name);
			t.setParent(this);
			Tables.add(new Table(name));
		}
		return Tables.find(name);
	}
	
	public Data getData(String tableName){
		if(!Data.containsKey(tableName)){
			Data t = new Data(tableName);
			t.setParent(this);
			Data.add(new Data(tableName));
		}
		return Data.find(tableName);
	}
	
	
	public final Data findData(String tableName){
		return Data.find(tableName);
	}
	
	public final Table findTable(String name){
		return Tables.find(name);
	}
	
	public final Set<Object> getTableNames(){
		return Tables.keySet();
	}

	@Override
	public ModelObjectTypes getObjType() {
		return ModelObjectTypes.SCHEMA;
	}

	@Override
	public String getName() {
		return null;
	}
	
}
