package baseline.model.constraint;

import javax.xml.bind.annotation.XmlRootElement;

import baseline.model.Constraint;


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
