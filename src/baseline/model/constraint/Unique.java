package baseline.model.constraint;

import baseline.model.Constraint;

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
