package baseline.model.constraint;

public enum ConstraintTypes {
	PRIMARY_KEY,
	FOREIGN_KEY,
	CHECK,
	UNIQUE;
	
	public String inName(){
		switch (this) {
			case PRIMARY_KEY: return "PRIMARY KEY";
			case FOREIGN_KEY: return "FOREIGN KEY";
			default : return this.name();
		}
	}
	
	
}
