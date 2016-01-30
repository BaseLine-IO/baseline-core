package baseline.model.constraint;

import baseline.utils.collections.AllowedForIndexing;
import baseline.model.Constraint;
import baseline.model.types.ConstraintTypes;

@AllowedForIndexing
public class ForeignKey extends Constraint {
	public ForeignKey() {
		super();
		Type = ConstraintTypes.FOREIGN_KEY;
	}
	
	public ForeignKey(String name) {
		super(name);
		Type = ConstraintTypes.FOREIGN_KEY;
	}

}
