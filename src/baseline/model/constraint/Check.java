package baseline.model.constraint;

import javax.xml.bind.annotation.XmlRootElement;

import baseline.model.Constraint;


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
