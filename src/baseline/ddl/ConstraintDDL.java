package baseline.ddl;

import baseline.model.Constraint;
import baseline.model.ModelObject;

public class ConstraintDDL extends DDL {
	
	private Constraint constraint;
	
	public ConstraintDDL(ModelObject _Object) {
		super(_Object);
		constraint = (Constraint) _Object;
	}
	
	@Override
	public String CREATE() {
		String refs = null;
		if(constraint.getRefTable() != null){
			refs = Join("references",constraint.getRefTable(),"(",
					join(constraint.getColumnRefs().iterator()),
				")");
		}
		
		return Join("ALTER TABLE",Parent.getName(),"ADD",
				ifTrue(constraint.getIS_USERNAME(),  Join("CONSTRAINT", constraint.getName())),
				constraint.getType().inName(),"(",
				NVL(constraint.getExpression(),join(constraint.getColumnNames().iterator())),
				")",refs,delimiter);

	}

}
