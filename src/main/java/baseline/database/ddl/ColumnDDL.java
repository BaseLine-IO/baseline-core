package baseline.database.ddl;

import baseline.model.ModelObject;
import baseline.model.table.Column;

public class ColumnDDL extends DDL {
	
	private Column column;
	
	public ColumnDDL(ModelObject _Object) {
		super(_Object);
		column = (Column) _Object;
	}
	
	
	@Override
	public void reset(ModelObject _Object) {
		column = (Column) _Object;
		super.reset(_Object);
	}
	
	@Override
	public String CREATE() {
		return Join("ALTER TABLE",Parent.getName(),"ADD",this.getColumnDesc(""), delimiter, this.COMMENT());
	}
	
	@Override
	public String ALTER(ModifyTypes mode) {
		switch (mode) {
		case DEFAULT: return Join("ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"",this.getDefault(),")",delimiter);
		case CONSTRAINT: return Join("ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"",this.getNotNULL(null),")",delimiter);
		case TYPE: return Join(
				"ALTER TABLE",Parent.getName(),"ADD",this.getColumnDesc("__new__"),delimiter,
				"UPDATE",Parent.getName(), "SET", column.getName()+"__new__", "=",column.getName(),delimiter,	
				"ALTER TABLE",Parent.getName(),"DROP COLUMN",column.getName(),delimiter,
				"RENAME COLUMN",Parent.getName()+"."+column.getName()+"__new__","TO", column.getName(),delimiter
			);	
			

		default: return null;

		}
	}
	
	@Override
	public String ALTER(String s) {

		if(s.equals("Comment")) return this.COMMENT();
		if(s.equals("Type")){
			 return Join(
						"ALTER TABLE",Parent.getName(),"ADD",this.getColumnDesc("__new__"),delimiter,
						"UPDATE",Parent.getName(), "SET", column.getName()+"__new__", "=",column.getName(),delimiter,	
						"ALTER TABLE",Parent.getName(),"DROP COLUMN",column.getName(),delimiter,
						"RENAME COLUMN",Parent.getName()+"."+column.getName()+"__new__","TO", column.getName(),delimiter
					);	
		}
		if(s.equals("NULLABLE")) return Join("ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"",this.getNotNULL("NOT NULL DISABLE"),")",delimiter);
		
		if(s.equals("DefaultValue")){ 
			if(column.getDefaultValue() == null && column.IS_NULLABLE() == false){
				return Join(
							"ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"","NOT NULL DISABLE",")",delimiter,
							"ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"",this.getDefault(),")",delimiter,
							"ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"","NOT NULL ENABLE",")",delimiter
						);
			}else{
				return Join("ALTER TABLE",Parent.getName(),"MODIFY",  "(","\""+column.getName()+"\"",this.getDefault(),")",delimiter);
			}
			
		}
		return null;
	
	}
	
	@Override
	public String COMMENT() {
		if(column.getComment() == null) return null;
		return Join("COMMENT ON COLUMN",Parent.getName()+"."+column.getName(), "IS","\'" +column.getComment()+"\'", delimiter);
	}

	public String getColumnDesc(String postfx) {
		return Join("\"" + column.getName() + postfx + "\"", this.getType(), NVL(column.getDefaultValue(), this.getDefault(), null), this.getNotNULL(null));
	}
	
	private String getType(){
		 String tDesc = null;
		if (column.getType().equals("NUMBER") && !isNull(column.getScale()))
	 		 tDesc = Join("(", NVL(column.getSize(),"*"), ",", column.getScale(), ")");
		else if (column.getType().equals("VARCHAR2") || column.getType().equals("CHAR"))
	 		 tDesc = Join("(",column.getSize(),column.getUnits(),")");
		else if (column.getType().equals("NVARCHAR2"))
	 		 tDesc = Join("(", column.getSize(), ")");

		return Join(column.getType(), tDesc);
	}
	
	private  String getDefault(){
		return  Join("DEFAULT",NVL(column.getDefaultValue(),column.getDefaultValue(),"NULL"));
	}
	
	private  String getNotNULL(String ifNullable){
		return (!column.IS_NULLABLE())? "NOT NULL ENABLE" : ifNullable;
	}
	
	
	

}
