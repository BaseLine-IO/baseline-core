package baseline.model.constraint;

import baseline.model.Constraint;
import baseline.model.types.ConstraintTypes;
import baseline.utils.collections.AllowedForIndexing;

@AllowedForIndexing
public class Unique extends Constraint {
	public Unique() {
		super();
		Type = ConstraintTypes.UNIQUE;
	}
	
	public Unique(String name) {
		super(name);
		Type = ConstraintTypes.UNIQUE;
	}

}
