package baseline.ddl;

import java.util.Iterator;

import baseline.model.ModelObject;
import baseline.model.ModelObjectTypes;
import baseline.model.table.Column;

import com.google.common.base.Joiner;

public class DDL {
	
	protected String delimiter = ";\n";
	protected ModelObject Object;
	protected ModelObject Parent;
	
	public static DDL getDDL(ModelObject moj){
		switch (moj.getObjType()) {
			case TABLE:return new TableDDL(moj);
			case INDEX:return new IndexDDL(moj);	
			case CONSTRAINT:return new ConstraintDDL(moj);	
			case COLUMN:return new ColumnDDL(moj);	
			default: return new DDL(moj);	
		}	
	}
	
	public static DDL getDDL(ModelObject moj, String delim){
		DDL ddl = DDL.getDDL(moj);
		ddl.delimiter = delim;
		return ddl;
	}
	
	public void reset(ModelObject _Object){
		Object = _Object;
		Parent = Object.getParent();
	};
	
	public DDL(ModelObject _Object) {
		Object = _Object;
		Parent = Object.getParent();
	}
	
	public String CREATE(){
		return null;
	}
	
	public String ALTER(ModifyTypes mode){
		return null;
		
	}
	
	public String ALTER(String s){
		return null;
		
	}
	
	public String DROP(){
		if(Parent.getObjType().equals(ModelObjectTypes.TABLE)){
				return	Join("ALTER TABLE",Parent.getName(),"DROP" ,Object.getObjType(),Object.getName(),delimiter);
		}else{
				return	Join("DROP" ,Object.getObjType(),Object.getName(),delimiter);
		}
		
	}
	
	public String COMMENT(){
		return null;
		
	}
	
	public static Object ifTrue(Boolean exp,Object return_valie){
		if(exp) return return_valie;
		return null;
	}
	
	public static Object ifFalse(Boolean exp,Object return_valie){
		if(!exp) return return_valie;
		return null;
	}
	
	public static String Join(Object... var){
		return Joiner.on(" ").skipNulls().join(var);
	}
	
	
	public static String join(Iterator<?> var){
		return Joiner.on(",").skipNulls().join(var);
	}
	
	public static Boolean isNull(Object what){
		return what == null;
	}
	
	public static Object NVL(Object what,Object ifnull){
		return (what != null) ?  what :  ifnull;
	}
	
	public static Object NVL(Object what,Object ifnotnull,Object ifnull){
		return (what != null) ?  ifnotnull :  ifnull;
	}
	
	public static String join_raw(Iterator<?> var){
		return Joiner.on("").skipNulls().join(var);
	}
	
	public static String join_raw(Object... var){
		return Joiner.on("").skipNulls().join(var);
	}

}
