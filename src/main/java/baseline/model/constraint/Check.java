package baseline.model.constraint;

import baseline.model.Constraint;
import baseline.model.types.ConstraintTypes;
import baseline.utils.collections.AllowedForIndexing;

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
