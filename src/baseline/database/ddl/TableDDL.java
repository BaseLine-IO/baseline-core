package baseline.database.ddl;

import java.util.Iterator;
import java.util.LinkedList;

import baseline.model.Constraint;
import baseline.model.Index;
import baseline.model.ModelObject;
import baseline.model.Table;
import baseline.model.types.ConstraintTypes;
import baseline.model.table.Column;
import baseline.model.types.TableTypes;

import com.google.common.base.Joiner;

public class TableDDL extends DDL {
	
	public TableDDL(ModelObject _Object) {
		super(_Object);
		table = (Table) _Object;
	}
    
	private Table table; 
	
	@Override
	public String CREATE() {
		LinkedList<String> sql = new LinkedList<String>();
		LinkedList<String> columns_list = new LinkedList<String>();
		LinkedList<String> columns_comments = new LinkedList<String>();
		Iterator<Column> column_iterator = this.table.getColumns().iterator();
		while(column_iterator.hasNext()){
			ColumnDDL ddl = new ColumnDDL(column_iterator.next());
			columns_list.add(ddl.getColumnDesc(""));
			columns_comments.add(ddl.COMMENT());
		}
		
		String colums = Joiner.on(",\n\t").join(columns_list);
		
				
		sql.add(Join( "CREATE", 
				    ifTrue((table.getType() == TableTypes.TEMP_SESSION || table.getType() == TableTypes.TEMP_TRANSACTION), "GLOBAL TEMPORARY"),
					"TABLE",table.getName(),"(","\n\t"+colums, "\n)",
					ifTrue( table.getType() == TableTypes.TEMP_TRANSACTION, "ON COMMIT DELETE ROWS"),
					ifTrue( table.getType() == TableTypes.TEMP_SESSION, "ON COMMIT PRESERVE ROWS"),
					delimiter
		));
		
		Iterator<Index> indexes = table.getIndexes().iterator();
		Iterator<Constraint> constraints = table.getConstraints().iterator();
		
		while(indexes.hasNext()) sql.add(DDL.getDDL(indexes.next()).CREATE()); // All Indexes
		
		while(constraints.hasNext()){
			Constraint c = constraints.next();
			if(c.getType() != ConstraintTypes.FOREIGN_KEY) sql.add(DDL.getDDL(c).CREATE()); //All Constraint WithOut FKs
		}
		
		sql.add(join_raw(columns_comments.iterator()));// All Collumn Comments
		
		sql.add(this.COMMENT()); // TAble Comment
		
		sql.add("\n");
		
		return join_raw(sql.iterator());
		
	}
	
	@Override
	public String COMMENT() {
		if(table.getComment() == null) return null;
		return Join("COMMENT ON TABLE", table.getName(), "IS","\'" +table.getComment()+"\'",delimiter);
	}
	
	@Override
	public String ALTER(String s) {
		if(s.equals("Comment")){
			if(table.getComment() == null) return null;
			return Join("COMMENT ON TABLE", table.getName(), "IS","\'" +table.getComment()+"\'",delimiter);
		}
		return null;
	}
	
	

}
