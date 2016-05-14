package baseline.model;

import baseline.model.constraint.Check;
import baseline.model.constraint.ForeignKey;
import baseline.model.constraint.PrimaryKey;
import baseline.model.constraint.Unique;
import baseline.model.table.Column;
import baseline.model.types.ConstraintTypes;
import baseline.model.types.ModelObjectTypes;
import baseline.utils.collections.AllowedForIndexing;
import baseline.utils.collections.KeyForIndex;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@AllowedForIndexing
public class Constraint  extends ModelObject{
	
	protected transient ConstraintTypes Type;
	@XmlAttribute
	String Name;
	transient Boolean IS_USERNAME = true;
	transient String DBSysName;
	String Expression;
	@XmlAttribute
	String OnDelete;
	@XmlElement(name="Column")
	LinkedList<Column> Columns;
	@XmlAttribute
	String RefTable;
	@XmlAttribute
	String onColumn;

	public Constraint(String name) {
		Name = name;
		DBSysName = name;
		Columns = new LinkedList<Column>();
	}

	public Constraint() {
		Columns = new LinkedList<Column>();
	}

	public static Constraint build(ConstraintTypes type, String name) {
		switch (type) {
			case FOREIGN_KEY:
				return new ForeignKey(name);
			case PRIMARY_KEY:
				return new PrimaryKey(name);
			case CHECK:
				return new Check(name);
			case UNIQUE:
				return new Unique(name);
			default:
				return new Constraint(name);

		}
	}

	public String getOnColumn() {
		return onColumn;
	}

	public void setOnColumn(String onColumn) {
		this.onColumn = onColumn;
	}

	public  String getRefTable() {
		return RefTable;
	}
	
	public void setConstraint(Boolean is_USERNAME, String exp, String refTab){
		  //Type = type;
		  IS_USERNAME = is_USERNAME;
		  Expression = exp;
		  RefTable = refTab;
		  if(!IS_USERNAME) Name = null; 
	}
	
	public void addColumn(String col_name, String ref_col){
		Column col = new Column(col_name);
		col.setRef(ref_col);
		Columns.add(col);
	}

	public final ConstraintTypes getType() {
		return Type;
	}

	public final Boolean getIS_USERNAME() {
		return IS_USERNAME;
	}

	public final String getExpression() {
		return Expression;
	}

	public final LinkedList<Column> getColumns() {
		return Columns;
	}

	public final Collection<String> getColumnNames() {
		return	Collections2.transform(Columns, new Function<Column,String>() {
			@Override
			public String apply(Column arg) {
				return arg.getName();
			}

		});
	}
	
	public final Collection<String> getColumnRefs() {
		return	Collections2.transform(Columns, new Function<Column,String>() {
			@Override
			public String apply(Column arg) {
				return arg.getRef();
			}

		});
	}

	public String getOnDelete() {
		return OnDelete;
	}

	public void setOnDelete(String onDelete) {
		OnDelete = onDelete;
	}

	@Override
	public ModelObjectTypes getObjType() {
		return ModelObjectTypes.CONSTRAINT;
	}

	public final String getName() {
		if (Name == null) return DBSysName;
		return Name;
	}

	public int columnsHashCode(){
		List<HashCode> cods = new ArrayList<HashCode>();
		Iterator<Column> iterator = Columns.iterator();
		while (iterator.hasNext()) cods.add(HashCodes.fromInt(((Column) iterator.next()).getName().hashCode()));
		return Hashing.combineUnordered(cods).asInt();
	}
	
	public int refsHashCode(){
		List<HashCode> cods = new ArrayList<HashCode>();
		Iterator<Column> iterator = Columns.iterator();
		while (iterator.hasNext()) cods.add(HashCodes.fromInt(((Column) iterator.next()).getRef().hashCode()));
		return Hashing.combineUnordered(cods).asInt();
	}

	@KeyForIndex
	public String getKeyForIndex() {
		if(Name != null) return Name;
		if(DBSysName == null){
			DBSysName = String.valueOf(this.hashCode());
			IS_USERNAME = false;
		}
		return DBSysName;
	}

	public int hashCode() {
		return Hashing.combineOrdered(Arrays.asList(
				Hashing.crc32().hashString(Name + Type.name() + Expression + RefTable, Charsets.UTF_8)
				, HashCodes.fromInt((this.Columns == null) ? -1 : new HashSet(this.Columns).hashCode())
		)).asInt();
	}

}

