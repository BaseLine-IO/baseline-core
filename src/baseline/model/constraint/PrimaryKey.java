package baseline.model.constraint;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import baseline.model.Constraint;


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
