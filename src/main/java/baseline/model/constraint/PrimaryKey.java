package baseline.model.constraint;

import baseline.model.Constraint;
import baseline.model.types.ConstraintTypes;
import baseline.utils.collections.AllowedForIndexing;

@AllowedForIndexing
public class PrimaryKey extends Constraint {
	public PrimaryKey() {
		super();
		Type = ConstraintTypes.PRIMARY_KEY;
	}
	
	public PrimaryKey(String name) {
		super(name);
		Type = ConstraintTypes.PRIMARY_KEY;
	}

}
