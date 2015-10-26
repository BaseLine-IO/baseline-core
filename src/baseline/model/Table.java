package baseline.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

import baseline.collections.Indexable;
import baseline.collections.IndexedList;
import baseline.model.constraint.Check;
import baseline.model.constraint.ConstraintTypes;
import baseline.model.constraint.ForeignKey;
import baseline.model.constraint.PrimaryKey;
import baseline.model.constraint.Unique;
import baseline.model.table.Column;
import baseline.model.table.TableTypes;
import baseline.newdiff.DiffData;
import baseline.newdiff.DiffThis;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

@XmlAccessorType(XmlAccessType.FIELD)
public class Table extends ModelObject implements Indexable{
	@XmlAttribute String Name;
	@XmlAttribute public TableTypes TableType;
	public String Comment;
	
	@DiffThis	@XmlElement(name="Column") public IndexedList<Column> Columns = new IndexedList<Column>(this);
	@DiffThis	@XmlElement(name="Index") public IndexedList<Index> Indexes = new IndexedList<Index>(this);
	@XmlElements({
				@XmlElement(name="Unique", type=baseline.model.constraint.Unique.class),
				@XmlElement(name="Check", type=baseline.model.constraint.Check.class),
				@XmlElement(name="PrimaryKey", type=baseline.model.constraint.PrimaryKey.class),
				@XmlElement(name="ForeignKey", type=baseline.model.constraint.ForeignKey.class)
	})
	@DiffThis	 public IndexedList<Constraint> Constraints = new IndexedList<Constraint>(this);
	
	
	public Map<String,Object> getParamMap(){
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("Name", this.Name);
		m.put("Type", this.TableType.name());
		m.put("Comment",this.Comment);
		m.put("Constraints", this.Constraints);
		m.put("Columns", this.Columns);
		m.put("Indexes", this.Indexes);
		return m;
		
	}
	
	public Table(String name) {
		Name = name;
	}
	
	public Table() {

	}
	
	public void setTable(TableTypes tb,String comm){
		TableType = tb;
		Comment = comm;
	}
	
	public Column getColumn(String name){
		  if(!Columns.containsKey(name)){
			  Column col = new Column(name);
			  
			  Columns.add(col);
		  }
		  return Columns.find(name);
	}
	
	public final Column findColumn(String name) {
		 return Columns.find(name);
	}
	
	public final Index findIndex(String name) {
		 return Indexes.find(name);
	}
	
	public final Constraint findConstraint(String name) {
		 return Constraints.find(name);
	}
	
	public Index getIndex(String name){
		  if(!Indexes.containsKey(name)){
			  Index i = new Index(name);
			  i.setParent(this);
			  Indexes.add(i);
		  }
		  return Indexes.find(name);
	}
	
	public PrimaryKey getPrimaryKey(String name){
		return (PrimaryKey) getConstraint(name, ConstraintTypes.PRIMARY_KEY);
	}
	
	public ForeignKey getForeignKey(String name){
		return (ForeignKey) getConstraint(name, ConstraintTypes.FOREIGN_KEY);
	}
	
	
	public Check getCheck(String name){
		return (Check) getConstraint(name, ConstraintTypes.CHECK);
	}
	
	public Unique getUnique(String name){
		return (Unique) getConstraint(name, ConstraintTypes.UNIQUE);
	}
	
	public  Constraint getConstraint(String name){
		return getConstraint(name, null);
	}
	
	public List<Column> getPrimaryOrUniqueColumns(){
		Iterator<Constraint> iter = Constraints.iterator();
		while(iter.hasNext()){
			Constraint c = iter.next();
			if(c.getType().equals(ConstraintTypes.PRIMARY_KEY)) return c.Columns;
		}
		
		iter = Constraints.iterator();
		while(iter.hasNext()){
			Constraint c = iter.next();
			if(c.getType().equals(ConstraintTypes.UNIQUE)) return c.Columns;
		}
		
		return new LinkedList<Column>(this.Columns);
	}
	
	private Constraint getConstraint(String name, ConstraintTypes type){
		if(!Constraints.containsKey(name)){
			 Constraint c = Constraint.build(type, name);
			 c.setParent(this);
			Constraints.add(c);
		  }
		return  Constraints.find(name);
	}


	public final String getName() {
		return Name;
	}

	public final TableTypes getType() {
		return TableType;
	}

	public final String getComment() {
		return Comment;
	}
	
	public int hashCode(){	
		return Hashing.combineOrdered(Arrays.asList(
				 HashCodes.fromInt(this.paramsHashCode()),
				 HashCodes.fromInt(new HashSet(this.Columns).hashCode()),
				 HashCodes.fromInt(new HashSet(this.Indexes).hashCode()),
				 HashCodes.fromInt(new HashSet(this.Constraints).hashCode())
		)).asInt();
	}
	
	public int paramsHashCode(){
		return Hashing.combineOrdered(Arrays.asList(
				 Hashing.goodFastHash(32).hashString(Name+TableType.name(),Charsets.UTF_8),
				 Hashing.goodFastHash(32).hashString((Comment == null?"null":Comment),Charsets.UTF_8)
				)).asInt();
	}


	public IndexedList<Column> getColumns() {
		return Columns;
	}

	public IndexedList<Index> getIndexes() {
		return Indexes;
	}

	public IndexedList<Constraint> getConstraints() {
		return Constraints;
	}

	

	@Override
	public ModelObjectTypes getObjType() {
		return ModelObjectTypes.TABLE;
	}

	@Override
	public String getKeyForIndex() {
		return Name;
	}
	
	
}
