package baseline.model.constraint;

import baseline.utils.collections.AllowedForIndexing;
import baseline.model.Constraint;
import baseline.model.types.ConstraintTypes;

@AllowedForIndexing
public class Check extends Constraint {
	public Check() {
		super();
		Type = ConstraintTypes.CHECK;
	}
	
	public Check(String name) {
		super(name);
		Type = ConstraintTypes.CHECK;
	}


}
